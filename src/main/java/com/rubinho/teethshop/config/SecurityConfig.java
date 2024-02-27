package com.rubinho.teethshop.config;


import com.rubinho.teethshop.controllers.GlobalExceptionHandler;
import com.rubinho.teethshop.jwt.JwtAuthenticationEntryPoint;
import com.rubinho.teethshop.jwt.JwtAuthFilter;
import com.rubinho.teethshop.jwt.UserAuthProvider;
import com.rubinho.teethshop.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


//@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserAuthProvider userAuthProvider;

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          UserAuthProvider userAuthProvider,
                          @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userAuthProvider = userAuthProvider;
        this.resolver = resolver;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthFilter(userAuthProvider, resolver), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/v1/cart/**").authenticated()

                        .requestMatchers(HttpMethod.PUT, "/api/v1/order/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/order/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/order/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/order/**").hasAuthority(Role.ADMIN.name())

                        .requestMatchers("/api/v1/upload").hasAuthority(Role.ADMIN.name())

                        .requestMatchers(HttpMethod.POST, "/api/v1/products").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAuthority(Role.ADMIN.name())

                        .requestMatchers(HttpMethod.POST, "/api/v1/types").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/types/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/types/**").hasAuthority(Role.ADMIN.name())

                        .requestMatchers(HttpMethod.POST, "/api/v1/sections").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/sections/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/sections/**").hasAuthority(Role.ADMIN.name())

                        .requestMatchers(HttpMethod.POST, "/api/v1/producers").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/producers/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/producers/**").hasAuthority(Role.ADMIN.name())


                        .anyRequest().permitAll()
                )
                .build();

    }
}
