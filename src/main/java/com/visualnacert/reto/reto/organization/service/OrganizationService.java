package com.visualnacert.reto.reto.organization.service;

import com.visualnacert.reto.reto.organization.model.Organization;
import com.visualnacert.reto.reto.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository repository;

    public Organization findByVisualOrganizationId(int visualOrganizationId){
        return repository.findByVisualOrganizationId(visualOrganizationId).orElseThrow();
    }

    public Organization findById(UUID organizationId){
        return repository.findById(organizationId).orElseThrow();
    }
}
