package com.practice.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestClient;

import com.practice.demo.authentication.JwtAuthenticationFilter;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@EnableWebSecurity
public class SwaggerConfig {

	 private  final AuthenticationProvider authenticationProvider;
	    private final JwtAuthenticationFilter jwtAuthenticationFilter;

	    public SwaggerConfig(
	        JwtAuthenticationFilter jwtAuthenticationFilter,
	        AuthenticationProvider authenticationProvider
	    ) {
	        this.authenticationProvider = authenticationProvider;
	        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    }

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.csrf()
	                .disable()
	                .authorizeHttpRequests()
	                .requestMatchers("/auth/**")
	                .permitAll()
	                .anyRequest()
	                .authenticated()
	                .and()
	                .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	                .authenticationProvider(authenticationProvider)
	                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }

		/*
		 * @Bean CorsConfigurationSource corsConfigurationSource() { CorsConfiguration
		 * configuration = new CorsConfiguration();
		 * 
		 * configuration.setAllowedOrigins(List.of("http://localhost:8005"));
		 * configuration.setAllowedMethods(List.of("GET","POST"));
		 * configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
		 * 
		 * UrlBasedCorsConfigurationSource source = new
		 * UrlBasedCorsConfigurationSource();
		 * 
		 * source.registerCorsConfiguration("/**",configuration);
		 * 
		 * return source; }
		 */
	
	@Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
	
	


	 @Bean
	 public OpenAPI customOpenapi() {
		 return new OpenAPI().info(new Info().title("Demo API")
				 .description("demo api for users")
				 );
	 }

}
