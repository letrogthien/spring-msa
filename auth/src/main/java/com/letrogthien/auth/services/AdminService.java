package com.letrogthien.auth.services;

import com.letrogthien.auth.anotation.CusAuditable;
import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.responses.ApiResponse;

import java.util.UUID;

public interface AdminService {

    @CusAuditable(
            action = "APPROVE_USER",
            description = "Approving a user account"
    )
    ApiResponse<String> approveUser(UUID userId);

    @CusAuditable(
            action = "REJECT_USER",
            description = "Rejecting a user account"
    )
    ApiResponse<String> rejectUser(UUID userId);

    @CusAuditable(
            action = "SUSPEND_USER",
            description = "Suspending a user account"
    )
    ApiResponse<String> suspendUser(UUID userId);

    @CusAuditable(
            action = "DELETE_USER",
            description = "Deleting a user account"
    )
    ApiResponse<String> deleteUser(UUID userId);

    @CusAuditable(
            action = "SET_ROLE",
            description = "Assigning a role to a user"
    )
    ApiResponse<String> setRoleForUser(UUID userId, RoleName roleName);


}
