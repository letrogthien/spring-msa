package com.letrogthien.auth.services;

import com.letrogthien.auth.anotation.CusAuditable;
import com.letrogthien.auth.requests.*;
import com.letrogthien.auth.responses.ApiResponse;
import com.letrogthien.auth.responses.LoginResponse;

import java.util.UUID;

public interface AuthService {
    ApiResponse<String> register(RegisterRequest registerRequest);

    ApiResponse<LoginResponse> login(LoginRequest loginRequest);

    ApiResponse<String> logout(LogoutRequest logoutRequest);

    ApiResponse<String> logoutAll(UUID userId);

    ApiResponse<LoginResponse> accessToken(AccessTokenRequest accessTokenRequest);

    @CusAuditable(action = "Change Password", description = "User changes their password")
    ApiResponse<String> changePassword(ChangePwdRequest changePwdRequest, UUID userId);

    ApiResponse<String> forgotPassword(ForgotPwdRequest forgotPwdRequest);

    ApiResponse<String> enableTwoFAuth(UUID userId);

    @CusAuditable(action = "Disable 2FA", description = "User disables two-factor authentication")
    ApiResponse<String> disableTwoFAuth(Disable2FaRequest disable2FAuthRequest, UUID userId);

    ApiResponse<LoginResponse> verifyTwoFAuth(Verify2FaRequest verify2FaRequest);

    @CusAuditable(action = "Trust Device", description = "User trusts a device for future logins")
    ApiResponse<String> trustDevice(String deviceName, String deviceType, UUID userId);

    ApiResponse<String> activateAccount(String token);

    ApiResponse<String> resetPassword(ResetPwdRequest resetPwdRequest, String token);
}