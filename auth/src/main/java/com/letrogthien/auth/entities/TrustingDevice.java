package com.letrogthien.auth.entities;

import java.util.UUID;
import com.letrogthien.auth.common.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TrustingDevice {
    private UUID userId;
    private UUID deviceInformationId;
    private Status status;
}
