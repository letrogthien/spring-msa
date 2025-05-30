package com.letrogthien.auth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;
import com.letrogthien.auth.common.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(
        name = "kyc_documents"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KycDocument {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
    @Column(
            name = "document_type",
            nullable = false,
            length = 50
    )
    private String documentType;
    @Column(
            name = "document_number",
            nullable = false,
            length = 50
    )
    private String documentNumber;
    @Column(
            name = "front_image_url",
            nullable = false,
            length = 255
    )
    private String frontImageUrl;
    @Column(
            name = "back_image_url",
            length = 255
    )
    private String backImageUrl;
    @Column(
            name = "status",
            nullable = false,
            length = 20
    )
    private Status status;
    @Column(
            name = "submitted_at",
            nullable = false
    )
    private ZonedDateTime submittedAt = ZonedDateTime.now();
    @Column(
            name = "reviewed_at"
    )
    private ZonedDateTime reviewedAt;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "reviewer_id"
    )
    private User reviewer;
    @Column(
            name = "version",
            nullable = false
    )
    private int version = 1;

    @PrePersist
    private void onPrePersist() {
        this.submittedAt = ZonedDateTime.now();
    }

    @PreUpdate
    private void onPreUpdate() {
        ++this.version;
    }
}
