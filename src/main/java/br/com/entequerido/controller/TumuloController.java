package br.com.entequerido.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping(value=Caminhos.SALVAR_TUMULO, method=RequestMethod.POST)
	public String salvarRua(@Valid @RequestBody Tumulo tumulo) throws Exception{
		try {
			Rua rua = ruaRepository.findByNomeOrCodigo(null, tumulo.getRua().getCodigo());
			
			if(Util.isNull(rua)) {
				return Util.montarRetornoErro(Parametros.MENSAGEM_ERRO_VALIDACAO_M_ATRIBUTO_CLASSE, Caminhos.WS_TUMULO.concat(Caminhos.SALVAR_TUMULO), Parametros.RUA_CODIGO, Parametros.RUA);
			}
			
			Rua ruaConsulta = ruaRepository.findByNomeOrCodigo(rua.getNome(), null);
			
			if(Util.isNotNull(ruaConsulta)
					&& !rua.equals(ruaConsulta)) {
				return Util.montarRetornoErro(Parametros.MENSAGEM_ERRO_M_EXISTENTE, Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA), Parametros.RUA);
			}
			
			rua.setQuadra(quadra);
			return ruaRepository.save(rua).toString();
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA));
		}
	}
}
