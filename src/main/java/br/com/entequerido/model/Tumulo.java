package br.com.entequerido.model;

import java.util.List;

import javax.validation.constraints.NotNull;

public class Tumulo extends Generico {
	
	@NotNull(message="Por favor, informe os dados da rua, ou cadastre uma. Ex.: {'codigo' : '123', 'nome' : 'Rua A'}")
	private Rua rua;
	
	private List<Pessoa> listaPessoa;
	
	public Tumulo() {
		super();
	}
	
	public Tumulo(String codigo, Rua rua, List<Pessoa> listaPessoa) {
		super(codigo);
		this.rua = rua;
		this.listaPessoa = listaPessoa;
	}

	public Rua getRua() {
		return rua;
	}

	public void setRua(Rua rua) {
		this.rua = rua;
	}

	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}

	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}
}
