package br.com.entequerido.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Cidade extends Generico{
	@NotBlank(message="Atributo obrigatório, por favor informe o nome da rua!")
	@Size(min=3, message="Tamanho inválido, por favor informe um nome de rua com mais de 2 caracteres!")
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
