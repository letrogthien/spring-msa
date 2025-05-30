package com.letrogthien.auth.repositories;

import java.util.List;
import java.util.UUID;

import com.letrogthien.auth.entities.DeviceInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceInformationRepository extends JpaRepository<DeviceInformation, UUID> {
    List<DeviceInformation> findByUserId(UUID userId);
}
