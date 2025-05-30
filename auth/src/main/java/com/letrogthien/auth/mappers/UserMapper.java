package com.letrogthien.auth.mappers;

import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.common.Status;
import com.letrogthien.auth.dtos.UserDto;
import com.letrogthien.auth.entities.KycDocument;
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

    @Named("mapKycDocumentsToStatus")
    default Status mapKycDocumentsToStatus(List<KycDocument> kycDocuments) {
        return kycDocuments.stream().max((doc1, doc2) ->
             doc2.getSubmittedAt().compareTo(doc1.getSubmittedAt())
        ).map(KycDocument::getStatus).orElse(Status.NONE);
    }
}
