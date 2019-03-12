package br.com.entequerido.model;

import java.util.List;

public class Tumulo extends Generico {
	private Rua endereco;
	private List<Pessoa> listaPessoa;
	
	public Tumulo() {
		super();
	}
	
	public Tumulo(String codigo,  Rua endereco, List<Pessoa> listaPessoa) {
		super(codigo);
		this.endereco = endereco;
		this.listaPessoa = listaPessoa;
	}

	public Rua getEndereco() {
		return endereco;
	}

	public void setEndereco(Rua endereco) {
		this.endereco = endereco;
	}

	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}

	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}
}
