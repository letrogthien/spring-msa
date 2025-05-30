package com.letrogthien.auth.mappers;

import com.letrogthien.auth.dtos.LoginHistoryDto;
import com.letrogthien.auth.entities.LoginHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface LoginHistoryMapper {
    @Mapping(
            source = "user.id",
            target = "userId"
    )
    LoginHistoryDto toDTO(LoginHistory loginHistory);

    List<LoginHistoryDto> toDTOs(List<LoginHistory> loginHistories);
}
