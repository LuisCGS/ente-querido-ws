package br.com.entequerido.model;

import java.util.Date;

public class Pessoa extends Generico{
	private String nome;
	private Date dataNascimento;
	private Date dataFalecimento;
	private Tumulo tumulo;
	
	public Pessoa() {
		super();
	}
	
	public Pessoa(String codigo, String nome, Date dataNascimento, Date dataFalecimento, Tumulo tumulo) {
		super(codigo);
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.dataFalecimento = dataFalecimento;
		this.tumulo = tumulo;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataFalecimento() {
		return dataFalecimento;
	}

	public void setDataFalecimento(Date dataFalecimento) {
		this.dataFalecimento = dataFalecimento;
	}

	public Tumulo getTumulo() {
		return tumulo;
	}

	public void setTumulo(Tumulo tumulo) {
		this.tumulo = tumulo;
	}
}
