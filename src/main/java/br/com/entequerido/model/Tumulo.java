package br.com.entequerido.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Tumulo extends Generico {
	
	@NotNull(message="Por favor, informe os dados da rua, ou cadastre uma. Ex.: {'codigo' : '123', 'nome' : 'Rua A'}")
	private Rua rua;
	
	@DBRef
	@NotNull(message="Por favor, informe os dados do cemiterio, ou cadastre um. Ex.: {'codigo' : '123', 'nome' : 'Cemiterio A'}")
	private Cemiterio cemiterio;
	
	@DBRef
	private List<Pessoa> listaPessoa;
	
	public Tumulo() {
		super();
	}
	
	public Tumulo(String codigo, Rua rua, Cemiterio cemiterio, List<Pessoa> listaPessoa) {
		super(codigo);
		this.rua = rua;
		this.cemiterio = cemiterio;
		this.listaPessoa = listaPessoa;
	}

	public Rua getRua() {
		return rua;
	}

	public void setRua(Rua rua) {
		this.rua = rua;
	}

	public Cemiterio getCemiterio() {
		return cemiterio;
	}

	public void setCemiterio(Cemiterio cemiterio) {
		this.cemiterio = cemiterio;
	}

	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}

	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}
}
