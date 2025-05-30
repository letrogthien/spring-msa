package com.letrogthien.auth.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.letrogthien.auth.common.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(
        name = "roles"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "name",
            unique = true,
            nullable = false,
            length = 50
    )
    private RoleName name;
    @Column(
            name = "description"
    )
    private String description;

    @ManyToMany(
            mappedBy = "roles",
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private List<User> users = new ArrayList<>();

}
