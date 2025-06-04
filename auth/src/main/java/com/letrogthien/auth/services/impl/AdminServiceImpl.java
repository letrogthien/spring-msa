package com.letrogthien.auth.services.impl;

import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.common.Status;
import com.letrogthien.auth.entities.Role;
import com.letrogthien.auth.entities.User;
import com.letrogthien.auth.exceptions.CustomException;
import com.letrogthien.auth.exceptions.ErrorCode;
import com.letrogthien.auth.repositories.RoleRepository;
import com.letrogthien.auth.repositories.UserRepository;
import com.letrogthien.auth.responses.ApiResponse;
import com.letrogthien.auth.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public ApiResponse<String> approveUser(UUID userId) {
       return null;
    }

    @Override
    public ApiResponse<String> rejectUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if (!user.getStatus().equals(Status.ACTIVE)){
            return ApiResponse.<String>builder()
                    .message("User is not active")
                    .build();
        }
        user.setStatus(Status.BLOCKED);
        userRepository.save(user);
        return ApiResponse.<String>builder()
                .message("User rejected successfully")
                .build();
    }

    @Override
    public ApiResponse<String> suspendUser(UUID userId) {
        return null;
    }

    @Override
    public ApiResponse<String> deleteUser(UUID userId) {
        return null;
    }

    @Override
    public ApiResponse<String> setRoleForUser(UUID userId, RoleName roleName) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(roleName))) {
            return ApiResponse.<String>builder()
                    .message("User already has this role")
                    .build();
        }
        Role role = roleRepository.findByName(roleName).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND)
        );
        user.getRoles().add(role);
        userRepository.save(user);
        return ApiResponse.<String>builder()
                .message("Role " + roleName + " assigned to user successfully")
                .build();
    }
}
