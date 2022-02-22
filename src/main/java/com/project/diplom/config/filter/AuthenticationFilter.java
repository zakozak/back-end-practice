package com.project.diplom.config.filter;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.diplom.model.dto.AuthCredentials;
import com.project.diplom.shared.CustomUserDetails;
import com.project.diplom.shared.View;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final int expirationTime;
    private final String secret;
    private final String headerName;
    private final String tokenPrefix;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            int expirationTime,
            String secret,
            String headerName,
            String tokenPrefix) {
        this.authenticationManager = authenticationManager;
        this.expirationTime = expirationTime;
        this.secret = secret;
        this.headerName = headerName;
        this.tokenPrefix = tokenPrefix;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            AuthCredentials credentials = new ObjectMapper().readValue(req.getInputStream(), AuthCredentials.class);
            try {
                return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                new CustomUserDetails(credentials.getName(), credentials.getPassword()),
                                credentials.getPassword(),
                                new ArrayList<>()));
            } catch (AuthenticationException e) {
                res.sendError(401, e.getMessage());
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
            throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String tokenPayload =
                new ObjectMapper()
                        .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                        .writerWithView(View.JsonWebToken.class)
                        .writeValueAsString(userDetails);

        String token =
                Jwts.builder()
                        .setSubject(tokenPayload)
                        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                        .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                        .compact();

        res.addHeader(headerName, tokenPrefix + token);
    }
}
