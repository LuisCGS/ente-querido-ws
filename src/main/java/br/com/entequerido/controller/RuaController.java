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

import br.com.entequerido.model.Rua;
import br.com.entequerido.repository.QuadraRepository;
import br.com.entequerido.repository.RuaRepository;
import br.com.entequerido.repository.TumuloRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Util;
import br.com.entequerido.model.Quadra;

@RestController
@RequestMapping("/ws/rua")
public class RuaController {
	@Autowired
	private RuaRepository ruaRepository;
	
	@Autowired
	private QuadraRepository quadraRepository;
	
	@Autowired
	private TumuloRepository tumuloRepository;
	
	/**
	 * Metodos responsavel por salvar uma {@link Rua} sendo obrigatorio ja ter adicionado uma {@link Quadra} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 12/03/2019 - 04:01 </i>
	 * @param rua : {@link Rua}
	 * @return {@link String}
	 * @throws Exception
	 */
	@RequestMapping(value=Caminhos.SALVAR_RUA, method=RequestMethod.POST)
	public String salvarRua(@Valid @RequestBody Rua rua) throws Exception{
		try {
			String codigoQuadra = rua.getQuadra().getCodigo();
			
			if(Util.isNotBlank(codigoQuadra)) {
				Quadra quadra = quadraRepository.findById(codigoQuadra).get();
				
				if(Util.isNull(quadra)) {
					return Util.montarRetornoErro("mensagem.erro.rua.quadra.inexistente", Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA));
				}
				
				rua.setQuadra(quadra);
				return ruaRepository.save(rua).toString();
			} else {
				return Util.montarRetornoErro("mensagem.erro.rua.quadra.codigo", Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA)); 
			}
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA));
		}
	}
	
	/**
	 * Metodos responsavel por buscar as {@link Rua} podendo ser ordenado e/ou pagina conforme a parametrizacao passada
	 * 
	 * <b> Ordem pode ser vazio Ex.: &ordem OU &ordem="" </b>
	 * <b> Pagina e tamanho pode ser vazio. Ex.: &pagina&tamanho </b>
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 13/03/2019 - 11:23 </i>
	 * @param nomeRua : {@link String}
	 * @param ordem : {@link String}
	 * @param pagina : {@link Integer}
	 * @param tamanho : {@link Integer}
	 * @return {@link String}
	 */
	@RequestMapping(value=Caminhos.BUSCAR_RUA_POR_NOME_ORDENADO_E_OU_PAGINADO, method=RequestMethod.GET)
	public String buscarRuaPorNomeOrdenadoEOuPaginado(@RequestParam String nomeRua, @RequestParam String ordem, 
			@RequestParam Integer pagina, @RequestParam Integer tamanho) {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(verificarOrdemParametrizado(ordem)) {
					return Util.montarRetornoErro("mensagem.erro.ordem.incorreta", Caminhos.WS_RUA.concat(Caminhos.BUSCAR_RUA_POR_NOME_ORDENADO_E_OU_PAGINADO));
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return new Gson().toJson(ruaRepository.findByNomeLikeIgnoreCase(nomeRua, new Sort(ordemSort, "nome")));
			} else {
				return new Gson().toJson(ruaRepository.findByNomeLikeIgnoreCase(nomeRua, PageRequest.of(pagina, tamanho, ordemSort, "nome")));
			}
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_RUA.concat(Caminhos.BUSCAR_RUA_POR_NOME_ORDENADO_E_OU_PAGINADO));
		}
	}
	
	/**
	 * Metodos responsavel por buscar uma lista de {@link Rua} informando um nome de {@link Quadra}  
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 12/03/2019 - 04:04 </i>
	 * @param nomeQuadra : {@link String}
	 * @return {@link String}
	 */
	@RequestMapping(value=Caminhos.BUSCAR_RUA_QUADRA, method=RequestMethod.GET)
	public String buscarRuaPorQuadra(@RequestParam String nomeQuadra, @RequestParam String ordem, 
			@RequestParam Integer pagina, @RequestParam Integer tamanho) {
		try {
			Direction ordemSort = Direction.ASC;
			
			if(Util.isNotBlank(ordem)) {
				if(verificarOrdemParametrizado(ordem)) {
					return Util.montarRetornoErro("mensagem.erro.ordem.incorreta", Caminhos.WS_RUA.concat(Caminhos.BUSCAR_RUA_QUADRA));
				}
				
				ordemSort = Direction.fromString(ordem);
			} 
			
			if(Util.isNull(pagina) || Util.isNull(tamanho)) {
				return new Gson().toJson(ruaRepository.findRuaByQuadraNomeLikeIgnoreCase(nomeQuadra, new Sort(ordemSort, "nome")));
			} else {
				return new Gson().toJson(ruaRepository.findRuaByQuadraNomeLikeIgnoreCase(nomeQuadra, PageRequest.of(pagina, tamanho, ordemSort, "nome")));
			}
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_RUA.concat(Caminhos.BUSCAR_RUA_QUADRA));
		}
	}
	
	/**
	 * Metodos responsavel por obter a quantidade de ruas que existem para a quadra informada 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 13/03/2019 - 12:35 </i>
	 * @param nomeQuadra : {@link String}
	 * @return {@link String}
	 */
	@RequestMapping(value=Caminhos.BUSCAR_QUANTIDADE_RUA_POR_QUADRA, method=RequestMethod.GET)
	public String buscarQuantidadeRuaPorQuadra(@RequestParam String nomeQuadra) {
		try {
			if(Util.isNotBlank(nomeQuadra)) {
				if(quadraRepository.findByNomeIgnoreCase(nomeQuadra).isEmpty()) {
					return Util.montarRetornoErro("mensagem.erro.rua.quadra.inexistente", Caminhos.WS_RUA.concat(Caminhos.BUSCAR_QUANTIDADE_RUA_POR_QUADRA));
				}
			} else {
				return Util.montarRetornoErro("mensagem.erro.rua.quadra.inexistente", Caminhos.WS_RUA.concat(Caminhos.BUSCAR_QUANTIDADE_RUA_POR_QUADRA));
			}
			
			return Long.toString(ruaRepository.countByQuadraNomeIgnoreCase(nomeQuadra));
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_RUA.concat(Caminhos.BUSCAR_RUA_QUADRA));
		}
	}
	
	/**
	 * Metodos responsavel por excluir uma {@link Rua} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 13/03/2019 - 01:27 </i>
	 * @param rua : {@link String}
	 * @return {@link String}
	 */
	@RequestMapping(value=Caminhos.EXCLUIR_RUA, method=RequestMethod.DELETE)
	public String excluirRua(@RequestParam String codigo) {
		try {
			Rua rua = ruaRepository.findById(codigo).get();
			
			if(Util.isNull(rua)) {
				return Util.montarRetornoErro("mensagem.erro.rua.inexistente", Caminhos.WS_RUA.concat(Caminhos.EXCLUIR_RUA));
			}
			
			if(tumuloRepository.countByRuaCodigo(codigo) > 0) {
				return Util.montarRetornoErro("mensagem.erro.rua.tumulo.existente", Caminhos.WS_RUA.concat(Caminhos.EXCLUIR_RUA));
			}
			
			ruaRepository.delete(rua);
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_RUA.concat(Caminhos.EXCLUIR_RUA));
		}
			
		return Util.montarRetornoSucesso("mensagem.sucesso.rua.excluida", "rua");
	}
	
	/** Metodos responsavel por verificar a ordem informada pelo {@link RequestParam}
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 13/03/2019 - 12:29 </i>
	 * @param ordem : {@link String}
	 * @return {@link Boolean}
	 */
	private Boolean verificarOrdemParametrizado(String ordem) {
		return !ordem.equalsIgnoreCase(Direction.ASC.toString()) && !ordem.equalsIgnoreCase(Direction.DESC.toString());
	}
}
