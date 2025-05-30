package com.letrogthien.auth.kafka;

import lombok.Getter;
@Getter
public enum KafkaTopic {
    REGISTER("auth-register"),
    FORGOT_PASSWORD("auth-forgot-password"),
    CHANGE_PASSWORD("auth-change-password"),
    SEND_OTP("auth-send-otp"),
    STRANGE_DEVICE("auth-strange-device");

    private final String topicName;

    KafkaTopic(String topicName) {
        this.topicName = topicName;
    }

}
