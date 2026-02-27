package Reaktor_redes_main.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class PublicEndpointSecurityConfig
{
	@Bean
	@Order(1)
	public SecurityFilterChain publicRegistrosRedesPostFilterChain(HttpSecurity http) throws Exception
	{
		http
			.securityMatcher(new AntPathRequestMatcher("/registros-redes", "POST"))
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authz -> authz.anyRequest().permitAll()) ;

		return http.build() ;
	}
}