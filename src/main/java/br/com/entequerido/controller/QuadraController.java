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
import br.com.entequerido.model.Cemiterio;
import br.com.entequerido.model.Quadra;
import br.com.entequerido.repository.CemiterioRepository;
import br.com.entequerido.repository.QuadraRepository;
import br.com.entequerido.repository.RuaRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/quadra")
public class QuadraController {
	@Autowired
	private QuadraRepository quadraRepository;
	
	@Autowired
	private CemiterioRepository cemiterioRepository;
	
	@Autowired
	private RuaRepository ruaRepository;
	
	/**
	 * Metodos responsavel por salvar uma {@link Quadra} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 25/03/2019 - 10:13 </i>
	 * @param quadra : {@link Quadra}
	 * @return {@link ResponseEntity}
	 * @throws ValidacaoException
	 * @throws GenericoException
	 */
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Quadra quadra) throws ValidacaoException, GenericoException {
		try {
			Cemiterio cemiterio = cemiterioRepository.findByCodigoOrNome(quadra.getCemiterio().getCodigo(), null);
			
			if(Util.isNull(cemiterio)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_VALIDACAO_M_ATRIBUTO_CLASSE, Parametros.CEMITERIO_CODIGO, 
						Parametros.CEMITERIO), Caminhos.QUADRA);
			}
			
			Quadra quadraConsulta = quadraRepository.findByNomeIgnoreCaseAndCemiterioCodigo(quadra.getNome(), quadra.getCemiterio().getCodigo());
			
			if(Util.isNotNull(quadraConsulta)
					&& !quadra.equals(quadraConsulta)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_F_CLASSE_M_ATRIBUTO_EXISTENTE, Parametros.QUADRA, 
						Parametros.QUADRA_NOME), Caminhos.QUADRA);
			}
			
			return Util.montarRetornoResponseEntity(quadraRepository.save(quadra));
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.QUADRA);
		}
	}
	
	/**
	 * Metodos responsavel por buscar uma lista ou paginas de {@link Quadra} por nome podendo ser ordenado e/ou paginado 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 10:40 </i>
	 * @param nome : {@link String}
	 * @param ordem : {@link String}
	 * @param pagina : {@link Integer}
	 * @param tamanho : {@link Integer}
	 * @return {@link ResponseEntity}
	 * @throws GenericoException 
	 */
	public ResponseEntity<?> buscarPorNomeOrdenadoEOuPaginado(String nome, String ordem, Integer pagina,
			Integer tamanho) throws ValidacaoException, GenericoException {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
					throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_ORDEM_INCORRETA), Caminhos.QUADRA);
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return Util.montarRetornoResponseEntity(quadraRepository.findByNomeLikeIgnoreCase(nome, new Sort(ordemSort, Parametros.QUADRA_NOME)));
			} else {
				return Util.montarRetornoResponseEntity(quadraRepository.findByNomeLikeIgnoreCase(nome, PageRequest.of(pagina, tamanho, ordemSort, Parametros.QUADRA_NOME)));
			}
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.QUADRA);
		}
	}
	
	/**
	 * Metodo responsavel por excluir uma {@link Quadra} a partir do codigo 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 25/03/2019 - 10:19 </i>
	 * @param codigo : {@link String}
	 * @throws ValidacaoException
	 * @throws GenericoException
	 */
	public void excluir(String codigo) throws ValidacaoException, GenericoException {
		try {
			Optional<Quadra> quadra = quadraRepository.findById(codigo);
			
			if(!quadra.isPresent()) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_OBRIGATORIO_F_INEXISTENTE, Parametros.QUADRA), Caminhos.QUADRA);
			}
			
			if(ruaRepository.countByQuadraCodigoOrNome(codigo, null) > 0) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_M_EXISTE_VINCULO, Parametros.RUA, Parametros.QUADRA), Caminhos.QUADRA);
			}
			
			quadraRepository.delete(quadra.get());
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.QUADRA);
		}
	}
}
