package com.letrogthien.auth.requests;

import lombok.Data;

@Data
public class TrustDeviceRequest {
    private String deviceName;
    private String deviceType;
}
