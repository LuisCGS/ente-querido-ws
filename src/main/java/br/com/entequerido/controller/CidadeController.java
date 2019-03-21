package br.com.entequerido.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.entequerido.exception.ValidacaoException;
import br.com.entequerido.model.Cidade;
import br.com.entequerido.repository.CemiterioRepository;
import br.com.entequerido.repository.CidadeRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/ws/cidade")
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
	 * @throws Exception
	 */
	@PostMapping
	public ResponseEntity<?> salvarCidade(@Valid @RequestBody Cidade cidade) throws ValidacaoException, Exception{
		try {
			Cidade cidadeConsulta = cidadeRepository.findByCodigoOrNome(null, cidade.getNome().trim());
			
			if(Util.isNotNull(cidadeConsulta)
					&& !cidade.equals(cidadeConsulta)) {
				throw new ValidacaoException(Util.montarMensagemParametrizado(Parametros.MENSAGEM_ERRO_F_CLASSE_M_ATRIBUTO_EXISTENTE, Parametros.CIDADE, Parametros.CIDADE_NOME), Caminhos.WS_CIDADE);
//				return Util.montarRetornoErro(Parametros.MENSAGEM_ERRO_F_CLASSE_M_ATRIBUTO_EXISTENTE, Caminhos.WS_CIDADE.concat(Caminhos.SALVAR_CIDADE), 
//						Parametros.CIDADE, Parametros.CIDADE_NOME);
			}
			
			return new ResponseEntity<>(cidadeRepository.save(cidade), HttpStatus.OK);
		} catch (ValidacaoException e) {
			throw e;
		} catch (Exception e) {
			throw e;
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
	 */
	@GetMapping
	public ResponseEntity<?> buscarCidadePorNomeOrdenadoEOuPaginado(@RequestParam String nomeCidade, @RequestParam String ordem, 
			@RequestParam Integer pagina, @RequestParam Integer tamanho) {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
//					return Util.montarRetornoErro(Parametros.MENSAGEM_ERRO_ORDEM_INCORRETA, Caminhos.WS_CIDADE.concat(Caminhos.BUSCAR_CIDADE_POR_NOME_ORDENADO_E_OU_PAGINADO));
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return new ResponseEntity<>(cidadeRepository.findByNomeLikeIgnoreCase(nomeCidade, new Sort(ordemSort, Parametros.CIDADE_NOME)), HttpStatus.OK) ;
			} else {
				return new ResponseEntity<>(cidadeRepository.findByNomeLikeIgnoreCase(nomeCidade, PageRequest.of(pagina, tamanho, ordemSort, Parametros.CIDADE_NOME)), HttpStatus.OK);
			}
		} catch (Exception e) {
			throw e;
//			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_CIDADE.concat(Caminhos.BUSCAR_CIDADE_POR_NOME_ORDENADO_E_OU_PAGINADO));
		}
	}
	
	/**
	 * Metodos responsavel por deletar uma {@link Cidade} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 16/03/2019 - 11:53 </i>
	 * @param codigo : {@link String}
	 * @return {@link String}
	 */
	@DeleteMapping
	public String excluirCidade(@RequestParam String codigo) {
		try {
			Cidade cidade = cidadeRepository.findById(codigo).get();
			
			if(Util.isNull(cidade)) {
				return Util.montarRetornoErro(Parametros.MENSAGEM_ERRO_OBRIGATORIO_F_INEXISTENTE, Caminhos.WS_CIDADE.concat(Caminhos.EXCLUIR_CIDADE), Parametros.CIDADE);
			}
			
			if(cemiterioRepository.countByCidadeCodigoOrNome(codigo, null) > 0) {
				return Util.montarRetornoErro(Parametros.MENSAGEM_ERRO_M_EXISTE_VINCULO, Caminhos.WS_CIDADE.concat(Caminhos.EXCLUIR_CIDADE), Parametros.CEMITERIO, Parametros.CIDADE);
			}
			
			cidadeRepository.delete(cidade);
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_CIDADE.concat(Caminhos.EXCLUIR_CIDADE));
		}
			
		return Util.montarRetornoSucesso(Parametros.MENSAGEM_SUCESSO_EXCLUIDA, Parametros.CIDADE);
	}
}
