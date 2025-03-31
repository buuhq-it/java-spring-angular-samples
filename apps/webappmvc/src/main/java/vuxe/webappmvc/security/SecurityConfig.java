package vuxe.webappmvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   PersistentTokenRepository persistentTokenRepository) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/user/**").hasRole("USER")
//                        .requestMatchers("/shared/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/auth/**", "/resources/**", "/css/**", "/js/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .tokenRepository(persistentTokenRepository)
                        .tokenValiditySeconds(7 * 24 * 60 * 60)
                        .key("vuxe-remember-me-secret")
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "GET")) // Allow GET
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
