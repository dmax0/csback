package cn.withwang.cs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(request -> request.getServletPath().startsWith("/auth")).permitAll();
//                .requestMatchers(request -> request.getServletPath().startsWith("/chat")).permitAll()
//                .anyRequest().authenticated();
        http.cors()
                .configurationSource(request -> {
                    var cors = new org.springframework.web.cors.CorsConfiguration();
                    cors.setAllowedOrigins(java.util.List.of("http://127.0.0.1:3000/", "http://localhost:3000/"));
                    cors.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE"));
                    cors.setAllowedHeaders(java.util.List.of("*"));
                    return cors;
                });
        return http.build();
    }
}
