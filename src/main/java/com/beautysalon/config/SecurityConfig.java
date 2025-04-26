package com.beautysalon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable() // Отключаем CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // регистрация, логин — для всех
                        .requestMatchers(HttpMethod.GET, "/api/services/**").permitAll() // просмотр услуг — для всех
                        .requestMatchers(HttpMethod.GET, "/api/masters/by-service/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/masters/available").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/masters/available-slots").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/masters/with-slots").permitAll()


                        .requestMatchers(HttpMethod.GET, "/api/appointments/my").hasAnyRole("client", "admin")
                        .requestMatchers(HttpMethod.GET, "/api/appointments/admin/all").hasRole("admin")

                        .anyRequest().authenticated() // остальные запросы требуют токен
                );

        // Подключаем JWT-фильтр
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // Разрешаем запросы от React
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
