package br.com.entequerido.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.entequerido.model.Usuario;
import br.com.entequerido.model.enumeration.PerfilEnum;
import br.com.entequerido.repository.UsuarioRepository;
import br.com.entequerido.security.jwt.JWTFiltroAutenticacao;
import br.com.entequerido.security.jwt.JWTFiltroLogin;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ConfiguradorDeSegurancaWeb extends WebSecurityConfigurerAdapter {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
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
		for(Usuario usuario : usuarioRepository.findAll()) {
			auth.inMemoryAuthentication()
				.withUser(usuario.getLogin())
				.password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(usuario.getSenha()))
				.roles(usuario.getListaPerfil().stream().map(PerfilEnum::name).toArray(String[]::new));
		}
	}
}
