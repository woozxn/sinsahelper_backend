package com.ujm.sinsahelper.common.response;

import lombok.Getter;

@Getter
public class Constants {

    @Getter
    public enum Key {
        CODE("code"),
        MESSAGE("message");

        private String key;

        Key(String key) {
            this.key = key;
        }
    }

    @Getter
    public enum Code {
        SUCCESS(0, "SUCCESS"),
        FAIL(-1, "FAIL");

        private Integer code;
        private String message;

        Code(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

    }

}
