package org.example.spring_mini_project.configuration;

import lombok.AllArgsConstructor;
import org.example.spring_mini_project.exception.CustomAccessDeniedHandler;
import org.example.spring_mini_project.security.JwtAuthEntrypoint;
import org.example.spring_mini_project.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthEntrypoint jwtAuthEntrypoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/v1/article/create-article", "/api/v1/article/update-article/{articleId}", "/api/v1/article/delete-article/{articleId}",
                                "api/v1/category/**"
                                )
                        .hasRole("AUTHOR") // Only AUTHOR can access these endpoints
                        .requestMatchers("/api/v1/comment/**").hasAnyRole("AUTHOR", "READER") // AUTHOR and READER can access these endpoints
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthEntrypoint) // For handling unauthenticated access
                        .accessDeniedHandler(customAccessDeniedHandler) // For handling access denied due to roles
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

