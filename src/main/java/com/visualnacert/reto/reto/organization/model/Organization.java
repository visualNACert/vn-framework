package com.visualnacert.reto.reto.organization.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "organization_id", columnDefinition = "uuid", nullable = false)
    private UUID organizationId;

    @Column(name = "organization", nullable = false, length = 255, unique = true)
    private String organizationName;

    @Column(name = "visualOrganizationId", nullable = false)
    private int visualOrganizationId;
}
