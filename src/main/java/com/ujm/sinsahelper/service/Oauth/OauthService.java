package com.ujm.sinsahelper.service.Oauth;

import com.ujm.sinsahelper.common.util.JwtTokenDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public interface OauthService {

    void redirectToLoginUri(HttpServletResponse response);
    JwtTokenDTO login(String authorizeCode);
    String makeLoginUri();
}
