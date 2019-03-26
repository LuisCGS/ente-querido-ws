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
import br.com.entequerido.interfaces.ControllerInterface;
import br.com.entequerido.model.Cidade;
import br.com.entequerido.repository.CemiterioRepository;
import br.com.entequerido.repository.CidadeRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/cidade")
public class CidadeController implements ControllerInterface {
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
	 * @return {@link ResponseEntity}
	 * @throws ValidacaoException, Exception
	 */
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Cidade cidade) throws ValidacaoException, Exception{
		try {
			Cidade cidadeConsulta = cidadeRepository.findByNomeIgnoreCase(cidade.getNome().trim());
			
			if(Util.isNotNull(cidadeConsulta)
					&& !cidade.equals(cidadeConsulta)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_F_CLASSE_M_ATRIBUTO_EXISTENTE, Parametros.CIDADE, Parametros.CIDADE_NOME), Caminhos.CIDADE);
			}
			
			return Util.montarRetornoResponseEntity(cidadeRepository.save(cidade));
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.CIDADE);
		}
	}
	
	/**
	 * Metodos responsavel por buscar uma lista ou paginas de {@link Cidade} podendo ser ordenado e/ou pagina conforme a parametrizacao passada
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 04:28 </i>
	 * @param nome : {@link String}
	 * @param ordem : {@link String}
	 * @param pagina : {@link Integer}
	 * @param tamanho : {@link Integer}
	 * @return {@link ResponseEntity}
	 * @throws ValidacaoException
	 * @throws GenericoException 
	 */
	@Override
	public ResponseEntity<?> buscarPorNomeOrdenadoEOuPaginado(String nome, String ordem, 
			Integer pagina, Integer tamanho) throws ValidacaoException, GenericoException {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
					throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_ORDEM_INCORRETA), Caminhos.CIDADE);
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return Util.montarRetornoResponseEntity(cidadeRepository.findByNomeLikeIgnoreCase(nome, new Sort(ordemSort, Parametros.CIDADE_NOME))) ;
			} else {
				return Util.montarRetornoResponseEntity(cidadeRepository.findByNomeLikeIgnoreCase(nome, PageRequest.of(pagina, tamanho, ordemSort, Parametros.CIDADE_NOME)));
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
	 * @throws ValidacaoException, GenericoException 
	 */
	@Override
	public void excluir(String codigo) throws ValidacaoException, GenericoException {
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
