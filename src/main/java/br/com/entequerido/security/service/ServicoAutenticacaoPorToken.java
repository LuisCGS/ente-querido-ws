package br.com.entequerido.security.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class ServicoAutenticacaoPorToken {
	private static final long TEMPOEXPIRA = 864000000;
	private static final String SEGREDO = "MeuSegredoEnteQueridoApp";
	private static final String TOKEN_PREFIXO = "Bearer";
	private static final String HEADER = "Authorization";
	
	public static void adicionarNoHeaderAutenticacaoPorToken (HttpServletResponse response, String login) {
		String JWT = Jwts.builder()
				.setSubject(login)
				.setExpiration(new Date(System.currentTimeMillis() + TEMPOEXPIRA))
				.signWith(SignatureAlgorithm.HS512, SEGREDO)
				.compact();
		
		String token = TOKEN_PREFIXO + " " + JWT;
		response.addHeader(HEADER, token);
		try {
			response.getOutputStream().print(token);
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	public static Authentication obterPorToken(String token) {
		String usuario = Jwts.parser()
				.setSigningKey(SEGREDO)
				.parseClaimsJws(token.replace(TOKEN_PREFIXO, ""))
				.getBody()
				.getSubject();
		
		return usuario == null ? null :  new UsernamePasswordAuthenticationToken(usuario, null, null);
	}
	
	public static Authentication obterAutenticacaoDaHeader (HttpServletRequest request) {
		String token = request.getHeader(HEADER);
		
		if(token != null) {
			return obterPorToken(token);
		}
		
		return null;
	}
}
