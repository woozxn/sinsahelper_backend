package com.ujm.sinsahelper.common.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenDTO {

    private String tokenType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public JwtTokenDTO(String tokenType, String accessToken, String refreshToken) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
