
package com.ebuka.ecommerce;

import com.ebuka.ecommerce.repository.UserRepository;
import com.ebuka.ecommerce.service.JwtAuthenticationFilter;
import com.ebuka.ecommerce.service.JwtService;
import tools.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            UserRepository userRepository,
            HttpSecurity http,
            JwtService jwtService,
            ObjectMapper objectMapper) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            .headers(headers ->
                headers.frameOptions(frame ->
                    frame.sameOrigin()
                )
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(401);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    objectMapper.writeValue(
                        response.getOutputStream(),
                        com.ebuka.ecommerce.dto.res.ApiRes.error("Token required")
                    );
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(403);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    objectMapper.writeValue(
                        response.getOutputStream(),
                        com.ebuka.ecommerce.dto.res.ApiRes.error("Access denied")
                    );
                })
            )
            

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(
                    org.springframework.http.HttpMethod.GET,
                    "/api/products",
                    "/api/products/**"
                ).permitAll()

                .requestMatchers("/api/products/**").hasRole("ADMIN")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/wallet/**").hasRole("CUSTOMER")
                .anyRequest().authenticated()
            )

            .addFilterBefore(
            new JwtAuthenticationFilter(
                    jwtService,
                    userRepository,
                    objectMapper
            ),
            UsernamePasswordAuthenticationFilter.class
    );

        return http.build();
    }
}
