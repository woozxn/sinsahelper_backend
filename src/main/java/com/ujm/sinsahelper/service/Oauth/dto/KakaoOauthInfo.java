package com.ujm.sinsahelper.service.Oauth.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoOauthInfo extends OauthInfo{

    @SerializedName("id")
    private Long kakaoId;

    @SerializedName("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @Setter
    @ToString
    public static class KakaoAccount{
        private Boolean nameNeedsAgreement;
        private String name;
        private Boolean emailNeedsAgreement;
        private String email;
        private Boolean genderNeedsAgreement;
        private String gender;
    }

}
