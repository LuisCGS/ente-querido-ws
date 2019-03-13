package br.com.entequerido.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.entequerido.model.Rua;
import br.com.entequerido.repository.QuadraRepository;
import br.com.entequerido.repository.RuaRepository;
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
			if(Util.isNotBlank(rua.getQuadra().getCodigo())) {
				Quadra quadra = quadraRepository.findById(rua.getQuadra().getCodigo()).get();
				
				if(Util.isNull(quadra)) {
					return Util.montarRetornoErro("mensagem.erro.quadra.inexistente", Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA));
				}
				
				rua.setQuadra(quadra);
				return new Gson().toJson(ruaRepository.save(rua));
			} else {
				return Util.montarRetornoErro("mensagem.erro.quadra.codigo", Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA)); 
			}
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA));
		}
	}
	
	/**
	 * Metodos responsavel por uma {@link Rua} por nome 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 12/03/2019 - 04:03 </i>
	 * @param nome : {@link String}
	 * @return {@link String}
	 */
	@RequestMapping(value=Caminhos.BUSCAR_RUA_NOME, method=RequestMethod.GET)
	public String buscarRuaPorNome(@RequestParam String rua) {
		return new Gson().toJson(ruaRepository.findByRuaLikeIgnoreCase(rua));
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
	public String buscarRuaPorQuadra(@RequestParam String nomeQuadra) {
		return new Gson().toJson(ruaRepository.findByQuadraLikeIgnoreCase(new Quadra(null, nomeQuadra)));
	}
}
