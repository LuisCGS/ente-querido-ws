package br.com.entequerido.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Quadra extends Generico {
	@NotBlank(message="Por favor, informe o nome da quadra")
	private String nome;
	
	@DBRef
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
	
	public Cemiterio getCemiterio() {
		return cemiterio;
	}

	public void setCemiterio(Cemiterio cemiterio) {
		this.cemiterio = cemiterio;
	}
}
