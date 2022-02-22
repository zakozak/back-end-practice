package com.project.diplom.config;

import com.project.diplom.config.filter.AuthenticationFilter;
import com.project.diplom.config.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final int expirationTime;
    private final String secret;
    private final String headerName;
    private final String tokenPrefix;

    public WebSecurityConfig(
            UserDetailsService userDetailsService,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            @Value("${jwt.secret.word}") String secret,
            @Value("${jwt.header.name}") String headerName,
            @Value("${jwt.token.pref}") String tokenPrefix,
            @Value("${jwt.expiration.ms}") int expirationTime) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.expirationTime = expirationTime;
        this.secret = secret;
        this.headerName = headerName;
        this.tokenPrefix = tokenPrefix;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(
                        new AuthenticationFilter(
                                authenticationManager(),
                                expirationTime,
                                secret,
                                headerName,
                                tokenPrefix))
                .addFilter(
                        new AuthorizationFilter(authenticationManager(), secret, headerName, tokenPrefix))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
}
