package br.com.entequerido.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.entequerido.model.Rua;
import br.com.entequerido.model.Tumulo;
import br.com.entequerido.repository.RuaRepository;
import br.com.entequerido.repository.TumuloRepository;
import br.com.entequerido.util.Caminhos;
import br.com.entequerido.util.Util;

@RestController
@RequestMapping("/ws/tumulo")
public class TumuloController {
	@Autowired
	private TumuloRepository tumuloRepository;
	
	@Autowired
	private RuaRepository ruaRepository;
	
	@RequestMapping(value=Caminhos.SALVAR_TUMULO, method=RequestMethod.POST)
	public String salvarRua(@Valid @RequestBody Tumulo tumulo) throws Exception{
		try {
			String codigoRua = tumulo.getRua().getCodigo();
			
			if(Util.isNotBlank(codigoRua)) {
				Rua rua = ruaRepository.findById(codigoRua).get();
				
				if(Util.isNull(rua)) {
					return Util.montarRetornoErro("mensagem.erro.rua.quadra.inexistente", Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA));
				}
				
				tumulo.setRua(rua);
				
				return tumuloRepository.save(tumulo).toString();
			} else {
				return Util.montarRetornoErro("mensagem.erro.rua.quadra.codigo", Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA)); 
			}
		} catch (Exception e) {
			return Util.montarRetornoErroException(e.getMessage(), Caminhos.WS_RUA.concat(Caminhos.SALVAR_RUA));
		}
	}
}
