package com.visualnacert.reto.reto.user.repository;

import com.visualnacert.reto.reto.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserNameAndOrganizationIdAndActiveTrue(String userName, UUID organizationId);
}
