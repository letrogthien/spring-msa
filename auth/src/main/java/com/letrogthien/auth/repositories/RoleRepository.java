package com.letrogthien.auth.repositories;

import java.util.Optional;
import java.util.UUID;
import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(RoleName name);
}
