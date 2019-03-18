package br.com.entequerido.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.core.mapping.DBRef;

@EntityScan
public class Rua extends Generico {
	@Size(min=3, message="Por favor, informe um nome de rua com mais de 2 caracteres!")
	private String nome;
	
	@DBRef
	@NotNull(message="Por favor, informe os dados da quadra, ou cadastre uma. Ex.: {'codigo' : '123', 'nome' : 'Quadra A'}")
	private Quadra quadra;
	
	public Rua() {
		super();
	}

	public Rua(String codigo, String nome, Quadra quadra) {
		super(codigo);
		this.nome = nome;
		this.quadra = quadra;
	}
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Quadra getQuadra() {
		return quadra;
	}

	public void setQuadra(Quadra quadra) {
		this.quadra = quadra;
	}
	
}
