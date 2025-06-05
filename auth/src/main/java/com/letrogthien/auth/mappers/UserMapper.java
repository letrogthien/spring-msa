package com.letrogthien.auth.mappers;

import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.dtos.UserDto;
import com.letrogthien.auth.entities.Role;
import com.letrogthien.auth.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {
    @Mappings({@Mapping(
            target = "roleNames",
            source = "roles",
            qualifiedByName = {"mapRolesToRoleNames"}
    ), @Mapping(
            target = "kycStatus",
            source = "kycDocuments",
            qualifiedByName = {"mapKycDocumentsToStatus"}
    )})
    UserDto toDTO(User user);

    List<UserDto> toDTOs(List<User> users);

    @Named("mapRolesToRoleNames")
    default List<RoleName> mapRolesToRoleNames(List<Role> roles) {
        return roles.stream().map(Role::getName).toList();
    }

}
