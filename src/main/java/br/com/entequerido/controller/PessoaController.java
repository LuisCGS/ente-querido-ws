package br.com.entequerido.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.entequerido.exception.GenericoException;
import br.com.entequerido.exception.ValidacaoException;
import br.com.entequerido.model.Pessoa;
import br.com.entequerido.model.Tumulo;
import br.com.entequerido.repository.PessoaRepository;
import br.com.entequerido.repository.TumuloRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
	@Autowired
	private TumuloRepository tumuloRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Pessoa pessoa) throws ValidacaoException, Exception{
		try {
			Tumulo tumulo = tumuloRepository.findByCodigoOrNome(pessoa.getTumulo().getCodigo(), null);
			
			if(Util.isNull(tumulo)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_VALIDACAO_M_ATRIBUTO_CLASSE, Parametros.TUMULO_CODIGO, 
						Parametros.TUMULO), Caminhos.PESSOA);
			}
			
			Pessoa pessoaConsulta = pessoaRepository.findByNomeIgnoreCaseAndTumuloCodigo(pessoa.getNome().trim(), tumulo.getCodigo());
			
			if(Util.isNotNull(pessoaConsulta)
					&& !pessoa.equals(pessoaConsulta)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_F_CLASSE_M_ATRIBUTO_EXISTENTE, 
						Parametros.PESSOA, Parametros.RUA_NOME), Caminhos.PESSOA);
			}
			
			pessoa.setTumulo(tumulo);
			return Util.montarRetornoResponseEntity(pessoaRepository.save(pessoa));
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.RUA);
		}
	}

	public ResponseEntity<?> buscarPorNomeOrdenadoEOuPaginado(String nome, String ordem, Integer pagina,
			Integer tamanho) throws ValidacaoException, GenericoException {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
					throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_ORDEM_INCORRETA), Caminhos.RUA);
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return Util.montarRetornoResponseEntity(pessoaRepository.findByNomeLikeIgnoreCase(nome, new Sort(ordemSort, Parametros.RUA_NOME)));
			} else {
				return Util.montarRetornoResponseEntity(pessoaRepository.findByNomeLikeIgnoreCase(nome, PageRequest.of(pagina, tamanho, ordemSort, Parametros.RUA_NOME)));
			}
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.RUA);
		}
	}

	public void excluir(String codigo) throws ValidacaoException, GenericoException {
		try {
			Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
			
			if(!pessoa.isPresent()) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_OBRIGATORIO_F_INEXISTENTE, Parametros.PESSOA), Caminhos.PESSOA);
			}
			
			pessoaRepository.delete(pessoa.get());
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.RUA);
		}
	}
	
}
