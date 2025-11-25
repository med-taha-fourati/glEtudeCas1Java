package com.fsegs.genie_logiciel_etude_cas_1.Configuration;

import com.fsegs.genie_logiciel_etude_cas_1.Middleware.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin("*")
public class SecurityConfiguration {
	private final JwtRequestFilter jwtRequestFilter;
	public SecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
		this.jwtRequestFilter = jwtRequestFilter;
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
		http.csrf(csrf -> csrf.disable()) //NOTE: csrf are used for fullstack html applications, for our use case which is a simple api, it is not needed
			.authorizeHttpRequests(
					auth -> auth
								.requestMatchers("/seance/**").permitAll() // remove every endpoint later for prod
								.requestMatchers("/enseignant/**").permitAll()
								.requestMatchers("/horaire/**").permitAll()
								.requestMatchers("/matiere/**").permitAll()
								.requestMatchers("/seance/**").permitAll()
								.requestMatchers("/grade/**").permitAll()
								.requestMatchers("/v3/api-docs/**",
										"/swagger-ui.html",
										"/swagger-ui/**",
										"/swagger-resources/**",
										"/webjars/**").permitAll()
								.anyRequest().authenticated()
								
			).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
