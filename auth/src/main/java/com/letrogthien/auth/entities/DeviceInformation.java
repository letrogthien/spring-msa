package com.letrogthien.auth.entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;


@Entity
@Table(
        name = "device_manager"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceInformation {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    @Column(
            columnDefinition = "BINARY(16)"
    )
    private UUID id;
    @Column(
            name = "user_id",
            nullable = false,
            columnDefinition = "BINARY(16)"
    )
    private UUID userId;
    @Column(
            name = "device_name",
            nullable = false,
            length = 100
    )
    private String deviceName;
    @Column(
            name = "device_type",
            nullable = false,
            length = 50
    )
    private String deviceType;
    @Column(
            name = "created_at",
            nullable = false
    )
    private ZonedDateTime createdAt;
    @Column(
            name = "last_login_at"
    )
    private ZonedDateTime lastLoginAt;

    public boolean isTrustDevice(DeviceInformation input) {
        return this.userId.equals(input.getUserId()) && this.deviceName.equals(input.getDeviceName()) && this.deviceType.equals(input.getDeviceType());
    }
}
