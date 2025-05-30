package com.letrogthien.auth.common;

import lombok.Getter;

@Getter
public enum ConstString {
    DOMAIN_NAME("http://localhost:8082/api/v1/auth/");

    private final String value;
    private ConstString(String value) {
        this.value = value;
    }

}