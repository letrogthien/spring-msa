package com.letrogthien.auth.dtos;

import com.letrogthien.auth.common.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class KycDocumentDto {
    private UUID id;
    private UUID userId;
    private String documentType;
    private String documentNumber;
    private String frontImageUrl;
    private String backImageUrl;
    private Status status;
    private ZonedDateTime submittedAt;
    private ZonedDateTime reviewedAt;
    private UUID reviewerId;
    private int version;
}
