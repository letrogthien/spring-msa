package com.letrogthien.auth.services;

import com.letrogthien.auth.anotation.CusAuditable;
import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.responses.ApiResponse;

import java.util.UUID;

public interface AdminService {


    ApiResponse<String> approveUser(UUID userId);


    ApiResponse<String> rejectUser(UUID userId);


    ApiResponse<String> suspendUser(UUID userId);


    ApiResponse<String> deleteUser(UUID userId);


    ApiResponse<String> setRoleForUser(UUID userId, RoleName roleName);


}
