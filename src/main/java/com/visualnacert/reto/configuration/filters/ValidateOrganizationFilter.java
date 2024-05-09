package com.visualnacert.reto.configuration.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visual.framework.rest.dto.ErrorResponse;
import com.visualnacert.reto.common.SessionObject;
import com.visualnacert.reto.reto.organization.model.Organization;
import com.visualnacert.reto.reto.organization.service.OrganizationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ValidateOrganizationFilter extends OncePerRequestFilter {
    private final OrganizationService organizationService;
    private final SessionObject sessionObject;

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static String ORG_PATTERN = "/organizations/([0-9a-fA-F-]+)/";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        String idOrganization = extractIdOrganization(url);

        if(idOrganization != null) {
            try{
                Organization org = organizationService.findById(UUID.fromString(idOrganization));
                sessionObject.setOrganization(org);
            } catch (Exception e) {
                ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "Organization not found");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setHeader("Content-Type", "application/json");
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }
        }

        doFilter(request, response, filterChain);
    }

    public static String extractIdOrganization(String url) {
        Pattern pattern = Pattern.compile(ORG_PATTERN);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}
