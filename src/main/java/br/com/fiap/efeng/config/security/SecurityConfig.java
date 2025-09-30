package br.com.fiap.efeng.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private VerificarToken verificarToken;

  @Bean
  public SecurityFilterChain filtrarCadeiaDeSeguranca(
      HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorize -> authorize

                // Swagger
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                // Auth
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                // Usuarios
                .requestMatchers(HttpMethod.POST, "/api/usuarios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/usuarios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/usuarios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios").hasRole("ADMIN")

                // Limites de Consumo
                .requestMatchers(HttpMethod.POST, "/api/limites").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/limites/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/limites/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/limites/**").hasAnyRole("ADMIN", "USER")

                // Dispositivos
                .requestMatchers(HttpMethod.POST, "/api/dispositivos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/dispositivos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/dispositivos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/dispositivos/**").hasAnyRole("ADMIN", "USER")

                // Sensores
                .requestMatchers(HttpMethod.POST, "/api/sensores").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/sensores/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/sensores/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/sensores/**").hasAnyRole("ADMIN", "USER")

                // Consumo Energia
                .requestMatchers(HttpMethod.POST, "/api/consumos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/consumos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/consumos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/consumos/**").hasAnyRole("ADMIN", "USER")

                // Alertas
                .requestMatchers(HttpMethod.POST, "/api/alertas").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/alertas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/alertas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/alertas/**").hasAnyRole("ADMIN", "USER")

                .anyRequest()
                .authenticated())
        .addFilterBefore(
            verificarToken,
            UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManage(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}