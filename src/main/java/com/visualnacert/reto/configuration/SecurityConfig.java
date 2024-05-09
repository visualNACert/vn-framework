package com.visualnacert.reto.configuration;

import com.visual.framework.security.auth.keycloak.KcMatcher;
import com.visual.framework.security.auth.visual.VisualMatcher;
import com.visual.framework.security.filters.VisualCustomFilter;
import com.visualnacert.reto.common.SessionObject;
import com.visualnacert.reto.configuration.filters.ValidateOrganizationFilter;
import com.visualnacert.reto.configuration.filters.ValidateUserFilter;
import com.visualnacert.reto.reto.organization.service.OrganizationService;
import com.visualnacert.reto.reto.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final OrganizationService organizationService;
    private final SessionObject sessionObject;

    @Bean
    @Order(1)
    SecurityFilterChain jwtSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .securityMatcher(visualMatcher())
                .addFilterBefore(visualCustomFilter(), BearerTokenAuthenticationFilter.class);

        return commonSecurityConfig(httpSecurity).build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain kcSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .securityMatcher(kcMatcher())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        return commonSecurityConfig(httpSecurity).build();
    }

    private HttpSecurity commonSecurityConfig(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(httpAuth -> httpAuth.anyRequest().authenticated())
                .addFilterAfter(validateUserFilter(), BearerTokenAuthenticationFilter.class)
                .addFilterAfter(validateOrganizationFilter(), ValidateUserFilter.class);
    }

    @Bean
    public VisualCustomFilter visualCustomFilter() {
        return new VisualCustomFilter();
    }

    @Bean
    public ValidateUserFilter validateUserFilter() {
        return new ValidateUserFilter(userService, organizationService, sessionObject);
    }

    @Bean
    public ValidateOrganizationFilter validateOrganizationFilter() {
        return new ValidateOrganizationFilter(organizationService, sessionObject);
    }

    @Bean
    public VisualMatcher visualMatcher() {
        return new VisualMatcher();
    }

    @Bean
    public KcMatcher kcMatcher() {
        return new KcMatcher();
    }
}
