package br.com.entequerido.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.entequerido.exception.GenericoException;
import br.com.entequerido.exception.ValidacaoException;
import br.com.entequerido.model.Cemiterio;
import br.com.entequerido.model.Cidade;
import br.com.entequerido.repository.CemiterioRepository;
import br.com.entequerido.repository.CidadeRepository;
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
	
	/**
	 * Metodos responsavel por salvar um {@link Cemiterio} e retornar a entidade com o codigo gerado 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 10:37 </i>
	 * @param cemiterio : {@link Cemiterio}
	 * @return {@link String}
	 * @throws Exception
	 */
	@PostMapping
	public String salvarCemiterio(@Valid @RequestBody Cemiterio cemiterio) throws ValidacaoException, GenericoException {
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
			
			cemiterio.setCidade(cidade);
			return cemiterioRepository.save(cemiterio).toString();
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericoException(e.getMessage(), Caminhos.CEMITERIO);
		}
	}
	
	/**
	 * Metodos responsavel por buscar um {@link Cemiterio} por nome podendo ser ordenado e/ou paginado 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 10:40 </i>
	 * @param nomeCemiterio : {@link String}
	 * @param ordem : {@link String}
	 * @param pagina : {@link Integer}
	 * @param tamanho : {@link Integer}
	 * @return {@link String}
	 * @throws GenericoException 
	 */
	@GetMapping
	public ResponseEntity<?> buscarCemiterioPorNomeOrdenadoEOuPaginado(@RequestParam String nomeCemiterio, @RequestParam String ordem, 
			@RequestParam Integer pagina, @RequestParam Integer tamanho) throws ValidacaoException, GenericoException {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
					throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_ORDEM_INCORRETA), Caminhos.CEMITERIO);
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return new ResponseEntity<>(cemiterioRepository.findByNomeLikeIgnoreCase(nomeCemiterio, new Sort(ordemSort, Parametros.CEMITERIO_NOME)), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(cemiterioRepository.findByNomeLikeIgnoreCase(nomeCemiterio, PageRequest.of(pagina, tamanho, ordemSort, Parametros.CEMITERIO_NOME)), HttpStatus.OK);
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
	 * @param codigoCidade : {@link String}
	 * @return {@link String}
	 */
	@RequestMapping(value=Caminhos.BUSCAR_QUANTIDADE_CEMITERIO_POR_CODIGO_DE_CIDADE, method=RequestMethod.GET)
	public String buscarQuantidadeCemiterioPorCodigoCidade(@RequestParam @NotBlank String codigoCidade) throws ValidacaoException, GenericoException{
		try {
			if(Util.isNull(cidadeRepository.findByCodigoOrNome(codigoCidade, null))) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_OBRIGATORIO_M_ATRIBUTO_F_CLASSE), Caminhos.CEMITERIO);
				return Util.montarRetornoErro(Parametros.MENSAGEM_ERRO_OBRIGATORIO_M_ATRIBUTO_F_CLASSE, Caminhos.CEMITERIO.concat(Caminhos.BUSCAR_QUANTIDADE_CEMITERIO_POR_CODIGO_DE_CIDADE), Parametros.CIDADE_CODIGO, Parametros.CIDADE);
			}
			
			return Long.toString(cemiterioRepository.countByCidadeCodigoOrNome(codigoCidade, null));
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.CEMITERIO.concat(Caminhos.BUSCAR_QUANTIDADE_CEMITERIO_POR_CODIGO_DE_CIDADE));
		}
	}
	
	
}
