package com.ujm.sinsahelper.service.Oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoOauthTokenDTO extends OauthTokenDTO {

    @JsonProperty(value = "expires_in")
    private Integer expiresIn;

    @JsonProperty(value = "refresh_token_expires_in")
    private Integer refreshTokenExpiresIn;
}
