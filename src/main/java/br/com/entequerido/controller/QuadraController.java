package br.com.entequerido.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.entequerido.model.Quadra;
import br.com.entequerido.repository.QuadraRepository;
import br.com.entequerido.util.Caminhos;

@RestController
@RequestMapping("/ws/quadra")
public class QuadraController {
	@Autowired
	private QuadraRepository quadraRepository;
	
	@RequestMapping(value=Caminhos.SALVAR_QUADRA, method=RequestMethod.POST)
	public String salvarQuadra(@Valid @RequestBody Quadra quadra) {
		return new Gson().toJson(quadraRepository.save(quadra));
	}
	
	@RequestMapping(value=Caminhos.BUSCAR_RUA_QUADRA, method=RequestMethod.GET)
	public String buscarQuadra(@RequestParam(value="nome") Integer nome) {
		List<Quadra> listaQuadra = new ArrayList<Quadra>();
		listaQuadra.addAll(quadraRepository.findAll());
		
		return new Gson().toJson(listaQuadra);
	}
}
