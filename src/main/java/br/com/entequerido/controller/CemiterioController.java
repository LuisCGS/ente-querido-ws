package br.com.entequerido.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.entequerido.exception.GenericoException;
import br.com.entequerido.exception.ValidacaoException;
import br.com.entequerido.model.Cemiterio;
import br.com.entequerido.model.Cidade;
import br.com.entequerido.repository.CemiterioRepository;
import br.com.entequerido.repository.CidadeRepository;
import br.com.entequerido.repository.QuadraRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/cemiterio")
public class CemiterioController {
	@Autowired
	private CemiterioRepository cemiterioRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private QuadraRepository quadraRepository;
	
	/**
	 * Metodos responsavel por salvar um {@link Cemiterio} e retornar a entidade com o codigo gerado 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 10:37 </i>
	 * @param cemiterio : {@link Cemiterio}
	 * @return {@link ResponseEntity}
	 * @throws Exception
	 */
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Cemiterio cemiterio) throws ValidacaoException, GenericoException {
		try {
			Cidade cidade = cidadeRepository.findByCodigoOrNome(cemiterio.getCidade().getCodigo(), null);
			
			if(Util.isNull(cidade)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_VALIDACAO_M_ATRIBUTO_CLASSE, Parametros.CIDADE_CODIGO, 
						Parametros.CIDADE), Caminhos.CEMITERIO);
			}
			
			Cemiterio cemiterioConsulta = cemiterioRepository.findByNomeIgnoreCaseAndCidadeCodigo(cemiterio.getNome(), cidade.getCodigo());
			
			if(Util.isNotNull(cemiterioConsulta)
					&& !cemiterio.equals(cemiterioConsulta)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_M_CLASSE_M_ATRIBUTO_EXISTENTE, Parametros.CEMITERIO, 
						Parametros.CEMITERIO_NOME), Caminhos.CEMITERIO);
			}
			
			return Util.montarRetornoResponseEntity(cemiterioRepository.save(cemiterio));
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.CEMITERIO);
		}
	}
	
	/**
	 * Metodos responsavel por buscar uma lista ou paginas de {@link Cemiterio} por nome podendo ser ordenado e/ou paginado 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 10:40 </i>
	 * @param nome : {@link String}
	 * @param ordem : {@link String}
	 * @param pagina : {@link Integer}
	 * @param tamanho : {@link Integer}
	 * @return {@link ResponseEntity}
	 * @throws GenericoException 
	 * @throws ValidacaoException
	 */
	public ResponseEntity<?> buscarPorNomeOrdenadoEOuPaginado(String nome, String ordem, 
			 Integer pagina, Integer tamanho) throws ValidacaoException, GenericoException {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
					throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_ORDEM_INCORRETA), Caminhos.CEMITERIO);
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return Util.montarRetornoResponseEntity(cemiterioRepository.findByNomeLikeIgnoreCase(nome, new Sort(ordemSort, Parametros.CEMITERIO_NOME)));
			} else {
				return Util.montarRetornoResponseEntity(cemiterioRepository.findByNomeLikeIgnoreCase(nome, PageRequest.of(pagina, tamanho, ordemSort, Parametros.CEMITERIO_NOME)));
			}
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.CEMITERIO);
		}
	}
	
	/**
	 * Metodos responsavel por buscar a quantidade de {@link Cemiterio} por {@link Cidade} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 16/03/2019 - 12:39 </i>
	 * @param codigo : {@link String}
	 * @return {@link ResponseEntity}
	 */
	@RequestMapping(value=Caminhos.BUSCAR_QUANTIDADE_CEMITERIO_POR_CODIGO_DE_CIDADE, method=RequestMethod.GET)
	public ResponseEntity<?> buscarQuantidadeCemiterioPorCodigoCidade(@RequestParam @NotBlank String codigo) throws ValidacaoException, GenericoException{
		try {
			if(Util.isNull(cidadeRepository.findByCodigoOrNome(codigo, null))) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_OBRIGATORIO_M_ATRIBUTO_F_CLASSE, Parametros.CIDADE_CODIGO, Parametros.CIDADE), Caminhos.CEMITERIO.concat(Caminhos.BUSCAR_QUANTIDADE_CEMITERIO_POR_CODIGO_DE_CIDADE));
			}
			
			return Util.montarRetornoResponseEntity(cemiterioRepository.countByCidadeCodigoOrNome(codigo, null));
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.CEMITERIO.concat(Caminhos.BUSCAR_QUANTIDADE_CEMITERIO_POR_CODIGO_DE_CIDADE));
		}
	}
	
	/**
	 * Metodos responsavel por buscar uma lista de {@link Cemiterio} de acordo com o codigo da {@link Cidade} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 22/03/2019 - 03:55 </i>
	 * @param codigo : {@link String}
	 * @return {@link ResponseEntity}
	 * @throws ValidacaoException
	 * @throws GenericoException
	 */
	@RequestMapping(value=Caminhos.BUSCAR_CEMITERIO_POR_CODIGO_DE_CIDADE, method=RequestMethod.GET)
	public ResponseEntity<?> buscarCemiterioPorCodigoCidade(@RequestParam @NotBlank String codigo) throws ValidacaoException, GenericoException{
		try {
			if(Util.isNull(cidadeRepository.findByCodigoOrNome(codigo, null))) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_OBRIGATORIO_M_ATRIBUTO_F_CLASSE, Parametros.CIDADE_CODIGO, Parametros.CIDADE), Caminhos.CEMITERIO.concat(Caminhos.BUSCAR_QUANTIDADE_CEMITERIO_POR_CODIGO_DE_CIDADE));
			}
			
			return Util.montarRetornoResponseEntity(cemiterioRepository.findByCidadeCodigo(codigo));
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.CEMITERIO.concat(Caminhos.BUSCAR_QUANTIDADE_CEMITERIO_POR_CODIGO_DE_CIDADE));
		}
	}
	
	/**
	 * Metodos responsavel por deletar um {@link Cemiterio} a partir do codigo parametrizado
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 22/03/2019 - 02:45 </i>
	 * @param codigo : {@link String}
	 * @throws ValidacaoException
	 * @throws GenericoException
	 */
	public void excluir(String codigo) throws ValidacaoException, GenericoException {
		try {
			Optional<Cemiterio> cemiterio = cemiterioRepository.findById(codigo);
			
			if(!cemiterio.isPresent()) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_OBRIGATORIO_F_INEXISTENTE, Parametros.CEMITERIO), Caminhos.CEMITERIO);
			}
			
			if(quadraRepository.countByCemiterioCodigo(codigo) > 0) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_M_EXISTE_VINCULO, Parametros.QUADRA, Parametros.CEMITERIO), Caminhos.CEMITERIO);
			}
			
			cemiterioRepository.delete(cemiterio.get());
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.CIDADE);
		}
	}
}
