package br.com.entequerido.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.DBRef;

import br.com.entequerido.model.enumeration.SepultamentoEnum;

public class Tumulo extends Generico {

	@Size(min=3, message="Por favor, informe um nome para o tumulo com mais de 2 caracteres, utilizar o nome da família! ")
	private String nome;
	
	@DBRef
	@NotNull(message="Por favor, informe os dados da rua, ou cadastre uma. Ex.: {'codigo' : '123', 'nome' : 'Rua A'}")
	private Rua rua;
	
	private List<SepultamentoEnum> listaSepultamento;
	
	private List<Pessoa> listaPessoa;
	
	public Tumulo() {
		super();
	}
	
	public Tumulo(String codigo, String nome, Rua rua, List<Pessoa> listaPessoa) {
		super(codigo);
		this.nome = nome;
		this.rua = rua;
		this.listaPessoa = listaPessoa;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
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
