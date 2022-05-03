package com.ujm.sinsahelper.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "oauth.provider")
public class OauthProperties {

    private Kakao kakao;
    private Google google;

    @Getter
    @Setter
    public static class Kakao {
        private String loginUri;
        private String userInfoUri;
        private String clientId;
        private String redirectUri;
        private String grantType;
        private String clientSecret;
        private Rule rule;
    }

    @Getter
    @Setter
    public static class Google {
        private String loginUri;
        private String soNongMin;
        private Rule rule;
    }

    @Getter
    @Setter
    public static class Rule {
        private String prefix;
        private String postfix;

        public String makeFullText(String text) {
            return prefix + text + "@" + postfix;
        }

    }

}
