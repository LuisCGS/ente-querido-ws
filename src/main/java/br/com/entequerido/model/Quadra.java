package br.com.entequerido.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.entequerido.util.Util;

public class Quadra extends Generico {
	@NotBlank(message="Por favor, informe o nome da quadra")
	private String nome;
	
	@NotNull(message="Por favor, informe os dados do cemiterio, ou cadastre uma. Ex.: {'codigo' : '123', 'nome' : 'Cemiterio A'}")
	private Cemiterio cemiterio;
	
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
		return Util.gson.toJson(this);
	}
}
