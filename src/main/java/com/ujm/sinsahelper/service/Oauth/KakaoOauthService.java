package com.ujm.sinsahelper.service.Oauth;

import com.google.gson.Gson;
import com.ujm.sinsahelper.common.exception.LoginException;
import com.ujm.sinsahelper.common.properties.OauthProperties;
import com.ujm.sinsahelper.common.util.JwtTokenDTO;
import com.ujm.sinsahelper.common.util.JwtUtil;
import com.ujm.sinsahelper.domain.AuthRole;
import com.ujm.sinsahelper.domain.Member;
import com.ujm.sinsahelper.repository.AuthRepository;
import com.ujm.sinsahelper.service.Oauth.dto.KakaoOauthInfo;
import com.ujm.sinsahelper.service.Oauth.dto.KakaoOauthTokenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class KakaoOauthService implements OauthService {

    public static final String RESPONSE_TYPE = "response_type";
    public static final String CLIENT_SECRET = "client_secret";
    private final RestTemplate restTemplate;
    private final OauthProperties oauthProperties;
    private final Gson gson;
    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private OauthProperties.Kakao kakao;

    public static final String AUTHORIZATION = "Authorization";
    public static final String GRANT_TYPE = "grant_type";
    public static final String CLIENT_ID = "client_id";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String CODE = "code";

    @PostConstruct
    public void init() {
        this.kakao = oauthProperties.getKakao();
    }


    @Override
    public void redirectToLoginUri(HttpServletResponse response) {
        try {
            response.sendRedirect(makeLoginUri());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String makeLoginUri() {
        return kakao.getLoginUri()
                + "authorize?"
                + RESPONSE_TYPE + "=" + CODE + "&"
                + CLIENT_ID + "=" + kakao.getClientId() + "&"
                + REDIRECT_URI + "=" + kakao.getRedirectUri();
    }

    @Override
    @Transactional(readOnly = false)
    public JwtTokenDTO login(String code) {

        ResponseEntity<KakaoOauthTokenDTO> response = getProviderToken(code);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException();
        }

        ResponseEntity<String> exchange = getUserInfo(response.getBody());

        if (exchange.getStatusCode().equals(HttpStatus.OK)) {
            KakaoOauthInfo kakaoOauthInfo = gson.fromJson(exchange.getBody(), KakaoOauthInfo.class);

            // 계정 추출
            String userEmail = kakao.getRule()
                    .makeFullText(kakaoOauthInfo.getKakaoId().toString());

            // 해당 이메일이 DB상에 존재하는지 확인
            Member findMember = authRepository.findMemberByEmail(userEmail)
                    .orElse(null);

            // 해당 이메일이 DB상에 존재한다면
            if (!ObjectUtils.isEmpty(findMember)) {
                // 토큰 발급 후 리턴
                return jwtUtil.generateToken(findMember.getEmail());
            }

            // 해당 이메일이 존재하지 않다면 회원가입
            Member createMember = Member.builder()
                    .email(userEmail)
                    .password(passwordEncoder.encode(userEmail))
                    .userEmail(kakaoOauthInfo.getKakaoAccount().getEmail())
                    .userName(kakaoOauthInfo.getKakaoAccount().getName())
                    .gender(kakaoOauthInfo.getKakaoAccount().getGender())
                    .authRole(AuthRole.ROLE_USER)
                    .build();

            authRepository.save(createMember);

            // 토큰 발급 후 리턴
            return jwtUtil.generateToken(createMember.getEmail());
        }

        throw new LoginException();
    }

    private ResponseEntity<String> getUserInfo(KakaoOauthTokenDTO kakaoToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION, "Bearer " + kakaoToken.getAccessToken());

        ResponseEntity<String> exchange = restTemplate.exchange(
                kakao.getUserInfoUri(),
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                String.class);
        return exchange;
    }

    private ResponseEntity<KakaoOauthTokenDTO> getProviderToken(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(GRANT_TYPE, kakao.getGrantType());
        params.add(CLIENT_ID, kakao.getClientId());
        params.add(REDIRECT_URI, kakao.getRedirectUri());
        params.add(CODE, code);
        params.add(CLIENT_SECRET,kakao.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<KakaoOauthTokenDTO> response = restTemplate.postForEntity(kakao.getLoginUri() + "token", httpEntity, KakaoOauthTokenDTO.class);
        return response;
    }
}
