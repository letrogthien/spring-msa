package com.letrogthien.auth.controllers;


import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import com.letrogthien.auth.anotation.JwtClaims;
import com.letrogthien.auth.requests.AccessTokenRequest;
import com.letrogthien.auth.requests.ChangePwdRequest;
import com.letrogthien.auth.requests.Disable2FaRequest;
import com.letrogthien.auth.requests.ForgotPwdRequest;
import com.letrogthien.auth.requests.LoginRequest;
import com.letrogthien.auth.requests.LogoutRequest;
import com.letrogthien.auth.requests.RegisterRequest;
import com.letrogthien.auth.requests.ResetPwdRequest;
import com.letrogthien.auth.requests.TrustDeviceRequest;
import com.letrogthien.auth.requests.Verify2FaRequest;
import com.letrogthien.auth.responses.ApiResponse;
import com.letrogthien.auth.responses.LoginResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/v1/auth"})
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping({"/register"})
    public ApiResponse<String> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return this.authService.register(registerRequest);
    }

    @PostMapping({"/login"})
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return this.authService.login(loginRequest, response);
    }

    @PostMapping({"/logout"})
    public ApiResponse<String> logout(@RequestBody LogoutRequest logoutRequest) {
        return this.authService.logout(logoutRequest);
    }

    @PostMapping({"/logout-all"})
    public ApiResponse<String> logoutAll(@JwtClaims("id") UUID userId) {
        return this.authService.logoutAll(userId);
    }

    @PostMapping({"/access-token"})
    public ApiResponse<LoginResponse> accessToken(@RequestBody AccessTokenRequest accessTokenRequest) {
        return this.authService.accessToken(accessTokenRequest);
    }

    @PostMapping({"/change-password"})
    public ApiResponse<String> changePassword(@RequestBody @Valid ChangePwdRequest changePwdRequest, @JwtClaims("id") UUID userId) {
        return this.authService.changePassword(changePwdRequest, userId);
    }

    @PostMapping({"/forgot-password"})
    public ApiResponse<String> forgotPassword(@RequestBody @Valid ForgotPwdRequest forgotPwdRequest) {
        return this.authService.forgotPassword(forgotPwdRequest);
    }

    @PostMapping({"/reset-password"})
    public ApiResponse<String> resetPassword(@RequestBody @Valid ResetPwdRequest resetPwdRequest, @RequestParam String token) {
        return this.authService.resetPassword(resetPwdRequest, token);
    }

    @PostMapping({"/enable-2fa"})
    public ApiResponse<String> enableTwoFAuth(@JwtClaims("id") UUID userId) {
        return this.authService.enableTwoFAuth(userId);
    }

    @PostMapping({"/disable-2fa"})
    public ApiResponse<String> disableTwoFAuth(@RequestBody Disable2FaRequest disable2FAuthRequest, @JwtClaims("id") UUID userId) {
        return this.authService.disableTwoFAuth(disable2FAuthRequest, userId);
    }

    @PostMapping({"/verify-2fa"})
    public ApiResponse<LoginResponse> verifyTwoFAuth(@RequestBody Verify2FaRequest verify2FaRequest, HttpServletResponse response) {
        return this.authService.verifyTwoFAuth(verify2FaRequest , response);
    }

    @PostMapping({"/trust-device"})
    public ApiResponse<String> trustDevice(@RequestBody TrustDeviceRequest trustDeviceRequest, @JwtClaims("id") UUID userId) {
        return this.authService.trustDevice(trustDeviceRequest.getDeviceName(), trustDeviceRequest.getDeviceType(), userId);
    }

    @PostMapping({"/activate"})
    public ApiResponse<String> activateAccount(@RequestParam String token) {
        return this.authService.activateAccount(token);
    }

    @PostMapping({"/role/assign"})
    public ApiResponse<String> assignRoleToUser(@JwtClaims("id") UUID userId, @RequestParam RoleName roleName) {
        return this.authService.assignRoleToUser(userId, roleName);
    }

    @GetMapping({"/test"})
    public ApiResponse<String> test() {
        return ApiResponse.<String>builder()
                .message("Test successful")
                .build();
    }
    @GetMapping({"/test-authenticated"})
    public ApiResponse<String> testAuthenticated(@JwtClaims("id") UUID userId) {
        return ApiResponse.<String>builder()
                .message("Authenticated user ID: " + userId)
                .build();
    }
}
