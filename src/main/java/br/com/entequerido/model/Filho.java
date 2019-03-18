package br.com.entequerido.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Filho extends Generico{
	@NotNull(message="Por favor, informe o nome do filho")
	private String nome;
	
	@NotNull(message="Por favor, informe a idade do filho")
	private Integer idade;
	
	@DBRef
	@NotNull(message="Por favor, informe o pai ou mãe que faleceu")
	private Pessoa pessoa;
	
	public Filho(String codigo) {
		super(codigo);
	}
	
	public Filho(String codigo, String nome, Integer idade, Pessoa pessoa) {
		super(codigo);
		this.nome = nome;
		this.idade = idade;
		this.pessoa = pessoa;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
