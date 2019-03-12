package br.com.entequerido.model;

import java.util.List;

public class Tumulo extends Generico {
	private Endereco endereco;
	private List<Pessoa> listaPessoa;
	
	public Tumulo() {
		super();
	}
	
	public Tumulo(String codigo,  Endereco endereco, List<Pessoa> listaPessoa) {
		super(codigo);
		this.endereco = endereco;
		this.listaPessoa = listaPessoa;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}

	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}
}
