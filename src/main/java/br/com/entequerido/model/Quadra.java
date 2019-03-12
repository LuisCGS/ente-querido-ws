package br.com.entequerido.model;

import com.google.gson.Gson;

public class Quadra extends Generico{
	private String nome;
	
	public Quadra() {
		super();
	}

	public Quadra(String codigo, String nome) {
		super(codigo);
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
