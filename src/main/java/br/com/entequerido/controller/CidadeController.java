package br.com.entequerido.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.entequerido.exception.GenericoException;
import br.com.entequerido.exception.ValidacaoException;
import br.com.entequerido.model.Cidade;
import br.com.entequerido.repository.CemiterioRepository;
import br.com.entequerido.repository.CidadeRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/cidade")
public class CidadeController {
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CemiterioRepository cemiterioRepository;
	
	/**
	 * Metodos responsavel por salvar uma entidade {@link Cidade} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 04:28 </i>
	 * @param cidade : {@link Cidade}
	 * @return {@link String}
	 * @throws ValidacaoException, Exception
	 */
	@PostMapping
	public ResponseEntity<?> salvarCidade(@Valid @RequestBody Cidade cidade) throws ValidacaoException, Exception{
		try {
			Cidade cidadeConsulta = cidadeRepository.findByCodigoOrNome(null, cidade.getNome().trim());
			
			if(Util.isNotNull(cidadeConsulta)
					&& !cidade.equals(cidadeConsulta)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_F_CLASSE_M_ATRIBUTO_EXISTENTE, Parametros.CIDADE, Parametros.CIDADE_NOME), Caminhos.CIDADE);
			}
			
			return new ResponseEntity<>(cidadeRepository.save(cidade), HttpStatus.OK);
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.CIDADE);
		}
	}
	
	/**
	 * Metodos responsavel por buscar as {@link Cidade} podendo ser ordenado e/ou pagina conforme a parametrizacao passada
	 * @param <T>
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 04:28 </i>
	 * @param nomeCidade : {@link String}
	 * @param ordem : {@link String}
	 * @param pagina : {@link Integer}
	 * @param tamanho : {@link Integer}
	 * @return {@link String}
	 * @throws ValidacaoException, GenericoException 
	 */
	@GetMapping
	public ResponseEntity<?> buscarCidadePorNomeOrdenadoEOuPaginado(@RequestParam String nomeCidade, @RequestParam String ordem, 
			@RequestParam Integer pagina, @RequestParam Integer tamanho) throws ValidacaoException, GenericoException {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
					throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_ORDEM_INCORRETA), Caminhos.CIDADE);
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return new ResponseEntity<>(cidadeRepository.findByNomeLikeIgnoreCase(nomeCidade, new Sort(ordemSort, Parametros.CIDADE_NOME)), HttpStatus.OK) ;
			} else {
				return new ResponseEntity<>(cidadeRepository.findByNomeLikeIgnoreCase(nomeCidade, PageRequest.of(pagina, tamanho, ordemSort, Parametros.CIDADE_NOME)), HttpStatus.OK);
			}
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.CIDADE);
		}
	}
	
	/**
	 * Metodos responsavel por deletar uma {@link Cidade} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 16/03/2019 - 11:53 </i>
	 * @param codigo : {@link String}
	 * @return {@link String}
	 * @throws ValidacaoException, GenericoException 
	 */
	@DeleteMapping
	public void excluirCidade(@RequestParam String codigo) throws ValidacaoException, GenericoException {
		try {
			Optional<Cidade> cidade = cidadeRepository.findById(codigo);
			
			if(!cidade.isPresent()) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_OBRIGATORIO_F_INEXISTENTE, Parametros.CIDADE), Caminhos.CIDADE);
			}
			
			if(cemiterioRepository.countByCidadeCodigoOrNome(codigo, null) > 0) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_M_EXISTE_VINCULO, Parametros.CEMITERIO, Parametros.CIDADE), Caminhos.CIDADE);
			}
			
			cidadeRepository.delete(cidade.get());
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.CIDADE);
		}
	}
}
