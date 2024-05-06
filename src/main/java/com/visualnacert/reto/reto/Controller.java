package com.visualnacert.reto.reto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visualnacert.reto.common.SessionObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reto")
@RequiredArgsConstructor
public class Controller {
    private final SessionObject sessionObject;
    private final static ObjectMapper mapper = new ObjectMapper();

    @GetMapping()
    public String reto() throws JsonProcessingException {
        StringBuilder data = new StringBuilder(mapper.writeValueAsString(sessionObject.getUser()));
        data.append(" ");
        data.append(mapper.writeValueAsString(sessionObject.getOrganization()));

        return data.toString();
    }
}
