package com.letrogthien.auth.dtos;
import com.letrogthien.auth.common.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private UUID id;
    private RoleName name;
    private String description;
}
