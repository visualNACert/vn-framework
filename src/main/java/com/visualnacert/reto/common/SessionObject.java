package com.visualnacert.reto.common;

import com.visualnacert.reto.reto.organization.model.Organization;
import com.visualnacert.reto.reto.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionObject {
    private User user;
    private Organization organization;
}
