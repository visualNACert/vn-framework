package com.visualnacert.reto.reto.organization.repository;

import com.visualnacert.reto.reto.organization.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    Optional<Organization> findByVisualOrganizationId(int visualOrganizationId);
}
