package com.visualnacert.reto.common;

import com.visualnacert.reto.reto.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Setter
@Getter
public class SessionObject {
    private User user;
}
