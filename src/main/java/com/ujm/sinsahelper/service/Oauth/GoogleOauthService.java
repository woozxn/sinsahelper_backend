package com.ujm.sinsahelper.service.Oauth;

import com.ujm.sinsahelper.common.util.JwtTokenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GoogleOauthService implements OauthService {

    @Override
    public void redirectToLoginUri(HttpServletResponse response) {

    }

    @Override
    public String makeLoginUri() {
        return null;
    }

    @Override
    public JwtTokenDTO login(String authorizeCode) {

        return null;
    }
}
