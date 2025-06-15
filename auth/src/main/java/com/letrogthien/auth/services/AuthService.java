package com.letrogthien.auth.services;


import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.requests.*;
import com.letrogthien.auth.responses.ApiResponse;
import com.letrogthien.auth.responses.LoginResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface AuthService {
    ApiResponse<String> register(RegisterRequest registerRequest);

    ApiResponse<LoginResponse> login(LoginRequest loginRequest, HttpServletResponse response);

    ApiResponse<String> logout(LogoutRequest logoutRequest);

    ApiResponse<String> logoutAll(UUID userId);

    ApiResponse<LoginResponse> accessToken(AccessTokenRequest accessTokenRequest);


    ApiResponse<String> changePassword(ChangePwdRequest changePwdRequest, UUID userId);

    ApiResponse<String> forgotPassword(ForgotPwdRequest forgotPwdRequest);

    ApiResponse<String> enableTwoFAuth(UUID userId);

    ApiResponse<String> disableTwoFAuth(Disable2FaRequest disable2FAuthRequest, UUID userId);

    ApiResponse<LoginResponse> verifyTwoFAuth(Verify2FaRequest verify2FaRequest, HttpServletResponse response);


    ApiResponse<String> trustDevice(String deviceName, String deviceType, UUID userId);

    ApiResponse<String> activateAccount(String token);

    ApiResponse<String> resetPassword(ResetPwdRequest resetPwdRequest, String token);

    ApiResponse<String> assignRoleToUser(UUID userId, RoleName roleName);
}