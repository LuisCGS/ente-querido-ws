package br.com.entequerido.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.entequerido.exception.GenericoException;
import br.com.entequerido.exception.ValidacaoException;

public interface ControllerInterface {
	@GetMapping
	ResponseEntity<?> buscarPorNomeOrdenadoEOuPaginado(@RequestParam String nome, @RequestParam String ordem, 
			@RequestParam Integer pagina, @RequestParam Integer tamanho) throws ValidacaoException, GenericoException;
	
	@DeleteMapping
	void excluir(@RequestParam String codigo) throws ValidacaoException, GenericoException;
}
