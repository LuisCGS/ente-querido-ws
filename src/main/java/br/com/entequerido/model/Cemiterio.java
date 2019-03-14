package br.com.entequerido.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Cemiterio extends Generico{
	@NotBlank(message="Por favor, informe o nome da rua contendo pelo menos 3 caracteres!")
	@Size(min=3, message="Por favor, informe um nome de rua com mais de 2 caracteres!")
	private String nome;
	
	@NotNull(message="Por favor, informe os dados da cidade, ou cadastre uma. Ex.: {'codigo' : '123', 'nome' : 'Cidade A'}")
	private Cidade cidade;
	private List<Tumulo> listaTumulo;
	
	public Cemiterio() {
		super();
	}
	
	public Cemiterio(String codigo, Cidade cidade, String nome, List<Tumulo> listaTumulo) {
		super(codigo);
		this.nome = nome;
		this.cidade = cidade;
		this.listaTumulo = listaTumulo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Tumulo> getListaTumulo() {
		return listaTumulo;
	}

	public void setListaTumulo(List<Tumulo> listaTumulo) {
		this.listaTumulo = listaTumulo;
	}
}
