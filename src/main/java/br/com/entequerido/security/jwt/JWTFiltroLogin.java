package br.com.entequerido.security.jwt;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.entequerido.exception.ApiError;
import br.com.entequerido.model.Usuario;
import br.com.entequerido.security.service.ServicoAutenticacaoPorToken;

public class JWTFiltroLogin extends AbstractAuthenticationProcessingFilter{

	public JWTFiltroLogin(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
		
		try {
			Authentication autenticacao = getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							usuario.getLogin(), 
							usuario.getSenha(), 
							Collections.<GrantedAuthority>emptyList()
					)
			);
			
			return autenticacao;
		} catch (BadCredentialsException e) {
			((HttpServletResponse) response).setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.flushBuffer();
			response.getWriter().append(new ApiError(HttpStatus.FORBIDDEN, e, ((HttpServletRequest) request).getServletPath()).toString());
		}
		
		return null;
	}
	
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			FilterChain chain, Authentication auth) throws IOException, ServletException {
		ServicoAutenticacaoPorToken.adicionarNoHeaderAutenticacaoPorToken(response, auth);
	}
}
