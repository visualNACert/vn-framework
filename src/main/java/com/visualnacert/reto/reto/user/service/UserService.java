package com.visualnacert.reto.reto.user.service;

import com.visualnacert.reto.reto.user.model.User;
import com.visualnacert.reto.reto.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User findUser(String userName, UUID organizationId){
        return repository.findByUserNameAndOrganizationIdAndActiveTrue(userName, organizationId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
    }

}
