package com.project.diplom.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.diplom.shared.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final String secret;
    private final String headerName;
    private final String tokenPrefix;

    public AuthorizationFilter(AuthenticationManager authManager, String secret, String headerName, String tokenPrefix) {
        super(authManager);
        this.secret = secret;
        this.headerName = headerName;
        this.tokenPrefix = tokenPrefix;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(headerName);

        if (header == null || !header.startsWith(tokenPrefix)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws IOException {
        String token = request.getHeader(headerName);
        if (token != null) {
            String userString = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token.replace(tokenPrefix, ""))
                    .getBody()
                    .getSubject();

            if (userString != null) {
                CustomUserDetails customUserDetails = new ObjectMapper().readValue(userString, CustomUserDetails.class);
                return new UsernamePasswordAuthenticationToken(customUserDetails, null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + customUserDetails.getUserRole().toString())));
            }
            return null;
        }
        return null;
    }
}
