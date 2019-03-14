package br.com.entequerido.controller;

import javax.validation.Valid;

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

import br.com.entequerido.model.Cidade;
import br.com.entequerido.repository.CidadeRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/ws/cidade")
public class CidadeController {
	@Autowired
	private CidadeRepository cidadeRepository;
	
	/**
	 * Metodos responsavel por salvar uma entidade {@link Cidade} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 04:28 </i>
	 * @param cidade : {@link Cidade}
	 * @return {@link String}
	 * @throws Exception
	 */
	@RequestMapping(value=Caminhos.SALVAR_CIDADE, method=RequestMethod.POST)
	public String salvarCidade(@Valid @RequestBody Cidade cidade) throws Exception{
		try {
			Cidade cidadeConsulta = cidadeRepository.findByNomeOrCodigo(cidade.getNome(), null);
			
			if(Util.isNotNull(cidadeConsulta)
					&& !cidade.equals(cidadeConsulta)) {
				return Util.montarRetornoErro(Parametros.MENSAGEM_ERRO_F_EXISTENTE, Caminhos.WS_CIDADE.concat(Caminhos.SALVAR_CIDADE), Parametros.CIDADE);
			}
			
			return cidadeRepository.save(cidade).toString();
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_CIDADE.concat(Caminhos.SALVAR_CIDADE));
		}
	}
	
	/**
	 * Metodos responsavel por buscar as {@link Cidade} podendo ser ordenado e/ou pagina conforme a parametrizacao passada
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 14/03/2019 - 04:28 </i>
	 * @param nomeCidade : {@link String}
	 * @param ordem : {@link String}
	 * @param pagina : {@link Integer}
	 * @param tamanho : {@link Integer}
	 * @return {@link String}
	 */
	@RequestMapping(value=Caminhos.BUSCAR_CIDADE_POR_NOME_ORDENADO_E_OU_PAGINADO, method=RequestMethod.GET)
	public String buscarCidadePorNomeOrdenadoEOuPaginado(@RequestParam String nomeCidade, @RequestParam String ordem, 
			@RequestParam Integer pagina, @RequestParam Integer tamanho) {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(Util.verificarOrdemParametrizado(ordem)) {
					return Util.montarRetornoErro(Parametros.MENSAGEM_ERRO_ORDEM_INCORRETA, Caminhos.WS_CIDADE.concat(Caminhos.BUSCAR_CIDADE_POR_NOME_ORDENADO_E_OU_PAGINADO));
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return new Gson().toJson(cidadeRepository.findByNomeLikeIgnoreCase(nomeCidade, new Sort(ordemSort, Parametros.CIDADE_NOME)));
			} else {
				return new Gson().toJson(cidadeRepository.findByNomeLikeIgnoreCase(nomeCidade, PageRequest.of(pagina, tamanho, ordemSort, Parametros.CIDADE_NOME)));
			}
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_CIDADE.concat(Caminhos.BUSCAR_CIDADE_POR_NOME_ORDENADO_E_OU_PAGINADO));
		}
	}
	
}
