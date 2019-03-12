package br.com.entequerido.model;

import java.util.List;

public class Cemiterio extends Generico{
	private String nome;
	private List<Tumulo> listaTumulo;
	
	public Cemiterio() {
		super();
	}
	
	public Cemiterio(String codigo, String nome, List<Tumulo> listaTumulo) {
		super(codigo);
		this.nome = nome;
		this.listaTumulo = listaTumulo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Tumulo> getListaTumulo() {
		return listaTumulo;
	}

	public void setListaTumulo(List<Tumulo> listaTumulo) {
		this.listaTumulo = listaTumulo;
	}
}
