package br.com.entequerido.security.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.entequerido.exception.ValidacaoException;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class ServicoAutenticacaoPorToken {
	private static final long TEMPOEXPIRA = 1800000;
	private static final String SEGREDO = "MeuSegredoEnteQueridoApp";
	private static final String TOKEN_PREFIXO = "Bearer";
	private static final String HEADER = "Authorization";
	
	public static void adicionarNoHeaderAutenticacaoPorToken (HttpServletResponse response, Authentication auth) {
		final String autoridades = auth.getAuthorities().stream()
				.map(GrantedAuthority :: getAuthority)
				.collect(Collectors.joining(","));
		
		String JWT = Jwts.builder()
				.setSubject(auth.getName())
				.setExpiration(new Date(System.currentTimeMillis() + TEMPOEXPIRA))
				.signWith(SignatureAlgorithm.HS512, SEGREDO)
				.claim(Parametros.CHAVE_AUTORIZACAO, autoridades)
				.compact();
		
		String token = TOKEN_PREFIXO + " " + JWT;
		response.addHeader(HEADER, token);
		try {
			response.getOutputStream().print(token);
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	public static Authentication obterPorToken(String token) throws ValidacaoException {
		try {
			Claims body = Jwts.parser()
					.setSigningKey(SEGREDO)
					.parseClaimsJws(token.replace(TOKEN_PREFIXO, ""))
					.getBody();
			
			final Collection<SimpleGrantedAuthority> authorities = Arrays.stream(body.get(Parametros.CHAVE_AUTORIZACAO).toString().split(","))
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
			
			return body.getSubject() == null ? null :  new UsernamePasswordAuthenticationToken(body.getSubject(), null, authorities);
		} catch (ExpiredJwtException e) {
			throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_TEMPO_EXPIRADO));
		}
	}
	
	public static Authentication obterAutenticacaoDaHeader (HttpServletRequest request) {
		String token = request.getHeader(HEADER);
		
		if(token != null) {
			return obterPorToken(token);
		}
		
		return null;
	}
}
