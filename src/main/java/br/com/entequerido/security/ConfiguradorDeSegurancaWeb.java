package br.com.entequerido.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.entequerido.security.jwt.JWTFiltroAutenticacao;
import br.com.entequerido.security.jwt.JWTFiltroLogin;

@Configuration
@EnableWebSecurity
public class ConfiguradorDeSegurancaWeb extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilterBefore(new JWTFiltroLogin("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JWTFiltroAutenticacao(), UsernamePasswordAuthenticationFilter.class);

	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder e = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		/* FIXME: Fazer a chamada de todos os usuarios */
		auth.inMemoryAuthentication()
				.withUser("admin")
				.password(e.encode("password"))
				.roles("ADMIN");
	}
}
