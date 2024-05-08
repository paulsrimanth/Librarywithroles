package com.library.config;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.library.filter.JwtAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		super();
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		System.out.println("in security config");
		 CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("/**"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						(req)->req.requestMatchers("/roles/login/**","/book/fetchbooks","book/getbookdata/**","/book/category/**")
						.permitAll()
						.requestMatchers("/user/createuser").hasAnyAuthority("ADMIN","POWER")
						.requestMatchers("/user/power/createadmin").hasAnyAuthority("POWER")
						.requestMatchers("/roles/fetchAll").hasAnyAuthority("ADMIN")
						.requestMatchers("/roles/usersbyadmin").hasAnyAuthority("ADMIN")
						.requestMatchers("/roles/usersbypower").hasAnyAuthority("POWER")
						.requestMatchers("/roles/deleteuser/**").hasAuthority("ADMIN")
						.requestMatchers("/book/upload").hasAnyAuthority("ADMIN")
						.requestMatchers("book/assignbooks/**").hasAnyAuthority("USER")
						.requestMatchers("/book/releasebook/**").hasAnyAuthority("USER")
						.requestMatchers("book/getmybooks").hasAnyAuthority("USER")
						.requestMatchers("/user/allusers").hasAnyAuthority("POWER")
						.anyRequest()
						.authenticated()
						)
				.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build() ;
		
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
		
	}
	

}
