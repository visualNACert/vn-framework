package com.visualnacert.reto.configuration.filters;

import com.visualnacert.reto.reto.organization.model.Organization;
import com.visualnacert.reto.reto.organization.service.OrganizationService;
import com.visualnacert.reto.reto.user.model.User;
import com.visualnacert.reto.reto.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.visual.security.auth.visual.dto.VnLoggedUser;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ValidateUserFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final OrganizationService organizationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username;
        int idOrg;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            if(authentication.getPrincipal() instanceof VnLoggedUser user){
                username = user.getName();
                idOrg = user.getIdOrganizacion();
            }else{
                Jwt jwt = (Jwt) authentication.getPrincipal();
                username = jwt.getClaimAsString("preferred_username");
                idOrg = Integer.parseInt(jwt.getClaimAsMap("vn_extra_data").get("idOrganization").toString());
            };

            try {
                Organization org = organizationService.findByVisualOrganizationId(idOrg);
                User user = userService.findUser(username, org.getOrganizationId());
                    if(user != null) {
                    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList());
                    SecurityContextHolder.getContext().setAuthentication(authRequest);

                    doFilter(request, response, filterChain);
                }else{
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } catch (HttpClientErrorException ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
