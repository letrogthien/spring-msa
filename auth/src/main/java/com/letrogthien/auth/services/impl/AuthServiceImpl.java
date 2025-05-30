package com.letrogthien.auth.services.impl;


import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import com.letrogthien.auth.common.ConstString;
import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.common.Status;
import com.letrogthien.auth.common.TokenType;
import com.letrogthien.auth.entities.DeviceInformation;
import com.letrogthien.auth.entities.PasswordHistory;
import com.letrogthien.auth.entities.Role;
import com.letrogthien.auth.entities.User;
import com.letrogthien.auth.entities.WhiteList;
import com.letrogthien.auth.exceptions.CustomException;
import com.letrogthien.auth.exceptions.ErrorCode;
import com.letrogthien.auth.jwt.JwtUtils;
import com.letrogthien.auth.kafka.EventProducer;
import com.letrogthien.auth.otp.OtpModel;
import com.letrogthien.auth.otp.OtpType;
import com.letrogthien.auth.redis.services.OtpModelCacheService;
import com.letrogthien.auth.redis.services.WhiteListCacheService;
import com.letrogthien.auth.repositories.DeviceInformationRepository;
import com.letrogthien.auth.repositories.PasswordHistoryRepository;
import com.letrogthien.auth.repositories.RoleRepository;
import com.letrogthien.auth.repositories.UserRepository;
import com.letrogthien.auth.requests.AccessTokenRequest;
import com.letrogthien.auth.requests.ChangePwdRequest;
import com.letrogthien.auth.requests.Disable2FaRequest;
import com.letrogthien.auth.requests.ForgotPwdRequest;
import com.letrogthien.auth.requests.LoginRequest;
import com.letrogthien.auth.requests.LogoutRequest;
import com.letrogthien.auth.requests.RegisterRequest;
import com.letrogthien.auth.requests.ResetPwdRequest;
import com.letrogthien.auth.requests.Verify2FaRequest;
import com.letrogthien.auth.responses.ApiResponse;
import com.letrogthien.auth.responses.LoginResponse;
import com.letrogthien.auth.securities.CustomPasswordEncoder;
import com.letrogthien.auth.services.AuthService;
import com.letrogthien.common.event.OtpEvent;
import com.letrogthien.common.event.RegistrationEvent;
import com.letrogthien.common.event.StrangeDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final WhiteListCacheService whiteListCacheService;
    private final OtpModelCacheService otpModelCacheService;
    private final EventProducer eventProducer;
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final DeviceInformationRepository deviceInformationRepository;

    @Override
    public ApiResponse<String> register(RegisterRequest registerRequest) {
        String responseData = "";
        if (this.existUsername(registerRequest.getUsername())) {
            responseData = "Username already exists";
        }

        if (this.existEmail(registerRequest.getEmail())) {
            responseData = responseData + ", Email already exists";
        }

        if (!responseData.isEmpty()) {
            return ApiResponse.<String>builder()
                    .message("Registration failed: ")
                    .data(responseData)
                    .build();
        }
        Role defaultRole = this.roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND)
        );
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(this.passwordEncoder.passwordEncoder().encode(registerRequest.getPassword()));
        user.setRoles(List.of(defaultRole));
        this.userRepository.save(user);

        RegistrationEvent registrationEvent = this.generateRegistrationEvent(user);
        this.eventProducer.registerUser(registrationEvent);

        return ApiResponse.<String>builder()
                .message("Registration successful, please check your email to activate your account")
                .data("Registration successful")
                .build();

    }

    private boolean existUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    private boolean existEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    private RegistrationEvent generateRegistrationEvent(User user) {
        String var10000 = ConstString.DOMAIN_NAME.getValue();
        String url = var10000 + "activate?token=" + this.jwtUtils.generateActivationToken(user);
        return RegistrationEvent.newBuilder().setEmail(user.getEmail()).setUrlActivation(url).build();
    }

    @Override
    @Transactional
    public ApiResponse<LoginResponse> login(LoginRequest loginRequest) {
        User user = this.userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if (user.getStatus() != Status.ACTIVE) {
            return ApiResponse.<LoginResponse>builder()
                    .message("Account is not active")
                    .build();
        }
        if (!this.passwordEncoder.passwordEncoder().matches(loginRequest.getPassword(), user.getPasswordHash())) {
            return ApiResponse.<LoginResponse>builder()
                    .message("Invalid username or password")
                    .build();
        }

        this.handlerDeviceInformation(user, loginRequest.getDeviceName(), loginRequest.getDeviceType());
        if (user.isTwoFactorEnabled()) {
            return this.handlerTwoFAuth(user);
        }
        user.updateLastLogin();
        this.userRepository.save(user);
        return this.generateLoginResponse(user);


    }

    private void handlerDeviceInformation(User user, String deviceName, String deviceTpe) {
        AtomicBoolean isTrust = new AtomicBoolean(false);
        List<DeviceInformation> listDevice = this.deviceInformationRepository.findByUserId(user.getId());
        if (!listDevice.isEmpty()) {
            for (DeviceInformation di : listDevice) {
                if (di.getDeviceName().equals(deviceName) && di.getDeviceType().equals(deviceTpe)) {
                    isTrust.set(true);
                    di.setLastLoginAt(ZonedDateTime.now());
                    this.deviceInformationRepository.save(di);
                    break;
                }
            }
        }

        if (isTrust.get()) {
            return;
        }
        DeviceInformation deviceInformation = new DeviceInformation();
        deviceInformation.setUserId(user.getId());
        deviceInformation.setDeviceName(deviceName);
        deviceInformation.setDeviceType(deviceTpe);
        deviceInformation.setLastLoginAt(ZonedDateTime.now());
        deviceInformation.setCreatedAt(ZonedDateTime.now());
        this.deviceInformationRepository.save(deviceInformation);
        this.eventProducer.strangeDevice(
                StrangeDevice.newBuilder()
                        .setEmail(user.getEmail())
                        .setDeviceName(deviceName)
                        .setDeviceType(deviceTpe)
                        .build()
        );
    }

    private ApiResponse<LoginResponse> generateLoginResponse(User user) {
        String token = this.jwtUtils.generateToken(user);
        String refreshToken = this.jwtUtils.generateRefreshToken(user);
        String jti = this.jwtUtils.extractClaim(refreshToken, "jti");
        this.whiteListCacheService.saveToCache(new WhiteList(jti, user.getId()));
        return ApiResponse.<LoginResponse>builder()
                .message("Login successful")
                .data(LoginResponse.builder()
                        .accessToken(token)
                        .refreshToken(refreshToken)
                        .build())
                .build();
    }


    private ApiResponse<LoginResponse> handlerTwoFAuth(User user) {
        String tmpToken = this.jwtUtils.generateTmpToken(user);
        this.saveAndSendOtp(user);
        return ApiResponse.<LoginResponse>builder()
                .message("Two-factor authentication required")
                .data(LoginResponse.builder()
                        .accessToken(tmpToken)
                        .build())
                .build();
    }

    private void saveAndSendOtp(User user) {
        OtpModel otpModel = new OtpModel();
        otpModel.generateOtp();
        otpModel.setUserId(user.getId());
        otpModel.setOtpType(OtpType.TWO_FACTOR_AUTHENTICATION);
        this.otpModelCacheService.saveOtpModel(otpModel);
        OtpEvent otpEvent = OtpEvent.newBuilder().setEmail(user.getEmail()).setOtp(otpModel.getOtp()).build();
        this.eventProducer.sendOtp(otpEvent);
    }

    @Override
    public ApiResponse<LoginResponse> verifyTwoFAuth(Verify2FaRequest verify2FaRequest) {
        String secret = verify2FaRequest.getSecret();
        if (!this.jwtUtils.isTokenValid(secret, TokenType.TMP_TOKEN)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        UUID userId = UUID.fromString(this.jwtUtils.extractClaim(secret, "id"));
        if (!this.otpModelCacheService.isPresentAndValidInCache(
                userId,
                verify2FaRequest.getOtp(),
                OtpType.TWO_FACTOR_AUTHENTICATION)
        ) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        return this.generateLoginResponse(user);


    }

    @Override
    public ApiResponse<String> logout(LogoutRequest logoutRequest) {
        String jtiRefreshToken = logoutRequest.getRefreshToken();
        UUID userId = this.jwtUtils.extractClaim(jtiRefreshToken, "id") != null ?
                UUID.fromString(this.jwtUtils.extractClaim(jtiRefreshToken, "id")) : null;
        if (this.whiteListCacheService.isPresentInCache(jtiRefreshToken, userId)) {
            this.whiteListCacheService.deleteFromCache(jtiRefreshToken, userId);
        }
        return ApiResponse.<String>builder()
                .message("Logout successful")
                .build();

    }

    @Override
    public ApiResponse<String> logoutAll(UUID userId) {
        this.whiteListCacheService.deleteAllByUserId(userId);
        return ApiResponse.<String>builder()
                .message("Logout all successful")
                .build();
    }

    @Override
    public ApiResponse<LoginResponse> accessToken(AccessTokenRequest accessTokenRequest) {
        String refreshToken = accessTokenRequest.getRefreshToken();
        if (!this.jwtUtils.isTokenValid(refreshToken, TokenType.REFRESH_TOKEN)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        UUID userId = UUID.fromString(this.jwtUtils.extractClaim(refreshToken, "id"));
        if (!this.whiteListCacheService.isPresentInCache(refreshToken, userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        String token = this.jwtUtils.generateToken(user);
        return ApiResponse.<LoginResponse>builder()
                .message("Access token generated successfully")
                .data(LoginResponse.builder()
                        .accessToken(token)
                        .refreshToken(refreshToken)
                        .build())
                .build();


    }

    @Override
    public ApiResponse<String> changePassword(ChangePwdRequest changePwdRequest, UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        this.validateChangePasswordRequest(changePwdRequest, user.getPasswordHash(), userId);
        String newPassword = changePwdRequest.getNewPassword();
        user.setPasswordHash(this.passwordEncoder.passwordEncoder().encode(newPassword));
        this.userRepository.save(user);
        return ApiResponse.<String>builder()
                .message("Password changed successfully")
                .build();
    }

    private void validateChangePasswordRequest(ChangePwdRequest changePwdRequest, String oldPassword, UUID userId) {

        String oldPasswordRq = changePwdRequest.getOldPassword();
        String newPassword = changePwdRequest.getNewPassword();
        String confirmPassword = changePwdRequest.getConfirmPassword();
        if (!this.passwordEncoder.passwordEncoder().matches(oldPasswordRq, oldPassword)) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new CustomException(ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }

        List<PasswordHistory> passwordHistories = this.passwordHistoryRepository.getTop3(userId);
        if (passwordHistories.stream().anyMatch(ph ->
                this.passwordEncoder.passwordEncoder().matches(newPassword, ph.getPasswordHash())
        )) {
            throw new CustomException(ErrorCode.PASSWORD_RECENTLY_USED);
        }

        int lastIndex = passwordHistories.isEmpty() ? 0 : passwordHistories.getFirst().getCurrentIndex();
        PasswordHistory newPasswordHistory = new PasswordHistory();
        newPasswordHistory.setUserId(userId);
        newPasswordHistory.setPasswordHash(this.passwordEncoder.passwordEncoder().encode(newPassword));
        newPasswordHistory.setCurrentIndex(lastIndex + 1);
        this.passwordHistoryRepository.save(newPasswordHistory);

    }

    @Override
    public ApiResponse<String> forgotPassword(ForgotPwdRequest forgotPwdRequest) {
        String email = forgotPwdRequest.getEmail();
        User user = this.userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if (user.getStatus() != Status.ACTIVE) {
            return ApiResponse.<String>builder()
                    .message("Account is not active")
                    .build();
        }
        String token = this.jwtUtils.generateResetPasswordToken(user);
        String var10000 = ConstString.DOMAIN_NAME.getValue();
        String url = var10000 + "reset-password?token=" + token;
        return ApiResponse.<String>builder()
                .message("Forgot password request successful, please check your email")
                .data(url)
                .build();
    }

    @Override
    public ApiResponse<String> enableTwoFAuth(UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if (user.isTwoFactorEnabled()) {
            return ApiResponse.<String>builder()
                    .message("Two-factor authentication is already enabled")
                    .build();
        }
        user.setTwoFactorEnabled(true);
        this.userRepository.save(user);
        return ApiResponse.<String>builder()
                .message("Two-factor authentication enabled successfully")
                .build();
    }


    @Override
    public ApiResponse<String> disableTwoFAuth(Disable2FaRequest disable2FAuthRequest, UUID userId) {

        boolean isValid = otpModelCacheService.isPresentAndValidInCache(
                userId,
                disable2FAuthRequest.getOtp(),
                OtpType.TWO_FACTOR_AUTHENTICATION);
        if (!isValid) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if (!user.isTwoFactorEnabled()) {
            return ApiResponse.<String>builder()
                    .message("Two-factor authentication is not enabled")
                    .build();
        }
        user.setTwoFactorEnabled(false);
        this.userRepository.save(user);
        return ApiResponse.<String>builder()
                .message("Two-factor authentication disabled successfully")
                .build();
    }

    @Override
    public ApiResponse<String> trustDevice(String deviceName, String deviceType, UUID userId) {

        return null;
    }

    @Override
    public ApiResponse<String> activateAccount(String token) {
        if (!this.jwtUtils.isTokenValid(token, TokenType.ACTIVATION_TOKEN)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        String email = this.jwtUtils.extractClaim(token, "email");
        User user = this.userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if (user.getStatus() == Status.ACTIVE) {
            return ApiResponse.<String>builder()
                    .message("Account is already active")
                    .build();
        }
        user.setStatus(Status.ACTIVE);
        this.userRepository.save(user);
        return ApiResponse.<String>builder()
                .message("Account activated successfully")
                .data("Account activated successfully")
                .build();
    }

    @Override
    public ApiResponse<String> resetPassword(ResetPwdRequest resetPwdRequest) {
        return null;
    }

}
