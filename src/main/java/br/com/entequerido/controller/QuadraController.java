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

import br.com.entequerido.Repository.QuadraRepository;
import br.com.entequerido.model.Quadra;

@RestController
@RequestMapping("/ws/quadra")
public class QuadraController {
	@Autowired
	private QuadraRepository quadraRepository;
	
	@RequestMapping(value="/salvarQuadra", method=RequestMethod.POST)
	public String salvarCidade(@Valid @RequestBody Quadra quadra) {
		return new Gson().toJson(quadraRepository.save(quadra));
	}
	
	//FIXME: Usar um de exemplo, mas n terá busca por quadra
	@RequestMapping(value="/buscarQuadra", method=RequestMethod.GET)
	public String buscarQuadra(@RequestParam(value="idCidade") Integer idCidade) {
		List<Quadra> listaCemiterio = new ArrayList<Quadra>();
		
//		listaCemiterio.addAll(quadraBusiness.findAll());
		
		return new Gson().toJson(listaCemiterio);
	}
}
