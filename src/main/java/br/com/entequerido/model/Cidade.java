package br.com.entequerido.model;

import java.util.List;

public class Cidade extends Generico{
	private String nome;
	private List<Cemiterio> listaCemiterio;
	
	public Cidade() {
		super();
	}
	
	public Cidade(String codigo, String nome, List<Cemiterio> listaCemiterio) {
		super(codigo);
		this.nome = nome;
		this.listaCemiterio = listaCemiterio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Cemiterio> getListaCemiterio() {
		return listaCemiterio;
	}

	public void setListaCemiterio(List<Cemiterio> listaCemiterio) {
		this.listaCemiterio = listaCemiterio;
	}
}
