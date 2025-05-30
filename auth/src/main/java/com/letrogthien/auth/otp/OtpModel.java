package com.letrogthien.auth.otp;

import com.letrogthien.auth.common.Status;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpModel {
    private final Random random = new Random();
    private String otp;
    private OtpType otpType;
    private ZonedDateTime createdAt;
    private ZonedDateTime expiredAt;
    private UUID userId;
    private String email;
    private Status status;

    public void generateOtp() {
        int intOtp = this.random.nextInt(900000) + 100000;
        this.otp = String.valueOf(intOtp);
        this.createdAt = ZonedDateTime.now();
        this.expiredAt = this.createdAt.plusMinutes(5L);
        this.status = Status.ACTIVE;
    }

}
