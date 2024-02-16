package com.rubinho.teethshop.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserAuthProvider userAuthProvider;

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;

    public JwtAuthFilter(UserAuthProvider userAuthProvider, HandlerExceptionResolver resolver) {
        this.userAuthProvider = userAuthProvider;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null) {
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.split(" ")[1];
                try {
                    SecurityContextHolder.getContext().setAuthentication(
                            userAuthProvider.validateToken(token)
                    );
                } catch (RuntimeException e) {
                    System.out.println("UHAHAHAHA");
                    SecurityContextHolder.clearContext();
                    resolver.resolveException(request, response, null, e);


                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
