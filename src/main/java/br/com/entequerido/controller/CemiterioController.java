package br.com.entequerido.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.entequerido.model.Cemiterio;
import br.com.entequerido.model.Cidade;
import br.com.entequerido.model.Quadra;
import br.com.entequerido.repository.CemiterioRepository;
import br.com.entequerido.repository.CidadeRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/ws/cemiterio")
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
	@RequestMapping(value=Caminhos.SALVAR_CEMITERIO, method=RequestMethod.POST)
	public String salvarCemiterio(@Valid @RequestBody Cemiterio cemiterio) throws Exception{
		try {
			String codigoCidade = cemiterio.getCidade().getCodigo();
			
			if(Util.isNotBlank(codigoCidade)) {
				Cidade cidade = cidadeRepository.findById(codigoCidade).get();
				
				if(Util.isNull(cidade)) {
					return Util.montarRetornoErro("mensagem.erro.rua.quadra.inexistente", Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA));
				}
				
				cemiterio.setCidade(cidade);
				return cemiterioRepository.save(cemiterio).toString();
			} else {
				return Util.montarRetornoErro("mensagem.erro.rua.quadra.codigo", Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA)); 
			}
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA));
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
	 */
	@RequestMapping(value=Caminhos.BUSCAR_CEMITERIO_POR_NOME_ORDENADO_E_OU_PAGINADO, method=RequestMethod.GET)
	public String buscarCemiterioPorNomeOrdenadoEOuPaginado(@RequestParam String nomeCemiterio, @RequestParam String ordem, 
			@RequestParam Integer pagina, @RequestParam Integer tamanho) {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
					return Util.montarRetornoErro("mensagem.erro.ordem.incorreta", Caminhos.WS_CEMITERIO.concat(Caminhos.BUSCAR_CEMITERIO_POR_NOME_ORDENADO_E_OU_PAGINADO));
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return new Gson().toJson(cemiterioRepository.findByNomeLikeIgnoreCase(nomeCemiterio, new Sort(ordemSort, "nome")));
			} else {
				return new Gson().toJson(cemiterioRepository.findByNomeLikeIgnoreCase(nomeCemiterio, PageRequest.of(pagina, tamanho, ordemSort, "nome")));
			}
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_CEMITERIO.concat(Caminhos.BUSCAR_CEMITERIO_POR_NOME_ORDENADO_E_OU_PAGINADO));
		}
	}
	
	@RequestMapping(value=Caminhos.BUSCAR_QUANTIDADE_CEMITERIO_POR_NOME_DE_CIDADE, method=RequestMethod.GET)
	public String buscarQuantidadeCemiterioPorCidade(@RequestParam @NotBlank String codigoCidade) {
		try {
			if(Util.isNull(cidadeRepository.findByCodigoOrNome(codigoCidade, null))) {
				return Util.montarRetornoErro(Parametros.men, Caminhos.WS_RUA.concat(Caminhos.BUSCAR_QUANTIDADE_RUA_POR_CODIGO_DE_QUADRA), Parametros.QUADRA);
			}
			
			return Long.toString(cemiterioRepository.countByCidadeCodigoOrNomeIgnoreCase(codigoCidade, null));
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_CEMITERIO.concat(Caminhos.BUSCAR_CEMITERIO_POR_NOME_ORDENADO_E_OU_PAGINADO));
		}
	}
}
