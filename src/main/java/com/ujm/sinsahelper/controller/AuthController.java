package com.ujm.sinsahelper.controller;

import com.ujm.sinsahelper.common.util.JwtTokenDTO;
import com.ujm.sinsahelper.service.Oauth.GoogleOauthService;
import com.ujm.sinsahelper.service.Oauth.KakaoOauthService;
import com.ujm.sinsahelper.service.Oauth.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final KakaoOauthService kakaoOauthService;
    private final GoogleOauthService googleOauthService;
    public static final String KAKAO = "kakao";
    public static final String GOOGLE = "google";

    // /auth/kakao/authorize
    // /auth/google/authorize

    @GetMapping("/{provider}/authorize")
    public String authorize(@PathVariable("provider") String provider,
                            HttpServletResponse response) {

        getProvider(provider).redirectToLoginUri(response);

        return "Redirect to " + provider + " Login page";
    }

    @GetMapping("/{provider}/login")
    public JwtTokenDTO login(@PathVariable("provider") String provider,
                             @RequestParam(value = "code") String authorizeCode) {
        return getProvider(provider).login(authorizeCode);
    }

    public OauthService getProvider(String provider) {
        switch (provider) {
            case KAKAO:
                return kakaoOauthService;
            case GOOGLE:
                return googleOauthService;
        }

        // 기본 oauth 로그인 값
        return kakaoOauthService;
    }

}
