package com.fsegs.genie_logiciel_etude_cas_1.Configuration;

import com.fsegs.genie_logiciel_etude_cas_1.Middleware.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
		http.csrf(csrf -> csrf.disable()) //NOTE: csrf are used for fullstack html applications, for our use case which is a simple api, it is not needed
			.authorizeHttpRequests(
					auth -> auth.requestMatchers("/seance/**").permitAll()
								.requestMatchers("/enseignants/**").permitAll()
								.requestMatchers("/horaire/**").permitAll()
								.requestMatchers("/matiere/**").permitAll()
								.requestMatchers("/seance/**").permitAll()
								.requestMatchers("/horaire/**").permitAll()
								.anyRequest().authenticated()
								
			).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
