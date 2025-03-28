package com.practice.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@EnableWebSecurity
public class SwaggerConfig {
	
	@Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.csrf().disable() // Disable CSRF for simplicity; enable it as needed
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/auth/register").permitAll() // Allow Swagger and OpenAPI
	                .anyRequest().authenticated() // Secure all other endpoints
	            )
	            .formLogin().and()
	            .httpBasic(); // Enable basic authentication (optional)

	        return http.build();
	    }
	 @Bean
	 public OpenAPI customOpenapi() {
		 return new OpenAPI().info(new Info().title("Demo API")
				 .description("demo api for users")
				 );
	 }

}
