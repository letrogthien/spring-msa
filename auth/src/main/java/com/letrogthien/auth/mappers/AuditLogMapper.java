package com.letrogthien.auth.mappers;

import com.letrogthien.auth.dtos.AuditLogDto;
import com.letrogthien.auth.entities.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {
    @Mapping(
            source = "user.id",
            target = "userId"
    )
    AuditLogDto toDTO(AuditLog auditLog);

    List<AuditLogDto> toDTOs(List<AuditLog> auditLogs);
}
