package br.com.entequerido.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.entequerido.model.Cemiterio;
import br.com.entequerido.model.Cidade;
import br.com.entequerido.model.Quadra;
import br.com.entequerido.model.Rua;
import br.com.entequerido.model.Tumulo;
import br.com.entequerido.repository.CemiterioRepository;
import br.com.entequerido.repository.RuaRepository;
import br.com.entequerido.repository.TumuloRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Parametros;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/ws/tumulo")
public class TumuloController {
	@Autowired
	private TumuloRepository tumuloRepository;
	
	@Autowired
	private RuaRepository ruaRepository;
	
	@Autowired
	private CemiterioRepository cemiterioRepository;
	
	/**
	 * Metodos responsavel por salar/alterar a entidade {@link Tumulo} 
	 *
	 * @Autor: <b> Luis C. G. Sanches <luis.cgs@icloud.com> </b>
	 * @Data: <i> 17/03/2019 - 01:45 </i>
	 * @param tumulo : {@link Tumulo}
	 * @return {@link String}
	 * @throws Exception
	 */
	@RequestMapping(value=Caminhos.SALVAR_TUMULO, method=RequestMethod.POST)
	public String salvarTumulo(@Valid @RequestBody Tumulo tumulo) throws Exception{
		try {
			Cemiterio cemiterio = cemiterioRepository.findByCodigoOrNome(tumulo.getCemiterio().getCodigo(), null);
			
			if(Util.isNull(cemiterio)) {
				return Util.montarRetornoErro(Parametros.MENSAGEM_ERRO_VALIDACAO_M_ATRIBUTO_CLASSE, 
						Caminhos.TUMULO.concat(Caminhos.SALVAR_TUMULO), Parametros.CEMITERIO_CODIGO, Parametros.CEMITERIO);
			}
			
			Rua rua = ruaRepository.findByCodigoOrNome(tumulo.getRua().getCodigo(), null);
			
			if(Util.isNull(rua)) {
				return Util.montarRetornoErro(Parametros.MENSAGEM_ERRO_VALIDACAO_F_ATRIBUTO_CLASSE, 
						Caminhos.TUMULO.concat(Caminhos.SALVAR_TUMULO), Parametros.RUA_CODIGO, Parametros.RUA);
			}
			
			tumulo.setCemiterio(cemiterio);
			tumulo.setRua(rua);
			return tumuloRepository.save(tumulo).toString();
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.TUMULO.concat(Caminhos.SALVAR_TUMULO));
		}
	}
	
	
}
