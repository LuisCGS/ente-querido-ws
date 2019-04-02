package br.com.entequerido.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


import br.com.entequerido.exception.ApiError;
import br.com.entequerido.exception.ValidacaoException;
import br.com.entequerido.security.service.ServicoAutenticacaoPorToken;


public class JWTFiltroAutenticacao extends GenericFilterBean {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			Authentication authentication = ServicoAutenticacaoPorToken.obterAutenticacaoDaHeader((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (ValidacaoException e) {
			((HttpServletResponse) response).setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.flushBuffer();
			response.getWriter().append(new ApiError(HttpStatus.FORBIDDEN, e, ((HttpServletRequest) request).getServletPath()).toString());
		} finally {
			chain.doFilter(request, response);
		}	
	}
}
