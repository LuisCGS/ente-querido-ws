package br.com.entequerido.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Quadra extends Generico {
	@NotBlank(message="Por favor, informe o nome da quadra")
	private String nome;
	
	@DBRef
	@NotNull(message="Por favor, informe os dados do cemiterio, ou cadastre uma. Ex.: {'codigo' : '123', 'nome' : 'Cemiterio A'}")
	private Cemiterio cemiterio;
	
	private List<Rua> listaRua;
	
	public Quadra() {
		super();
	}

	public Quadra(String codigo, String nome, Cemiterio cemiterio,
			List<Rua> listaRua) {
		super(codigo);
		this.nome = nome;
		this.cemiterio = cemiterio;
		this.setListaRua(listaRua);
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

	public List<Rua> getListaRua() {
		return listaRua;
	}

	public void setListaRua(List<Rua> listaRua) {
		this.listaRua = listaRua;
	}
}
