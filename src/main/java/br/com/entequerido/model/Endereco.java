package br.com.entequerido.model;

public class Endereco extends Generico {
	private String rua;
	private Quadra quadra;
	
	public Endereco() {
		super();
	}

	public Endereco(String codigo, String rua, Quadra quadra) {
		super(codigo);
		this.rua = rua;
		this.quadra = quadra;
	}
	

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public Quadra getQuadra() {
		return quadra;
	}

	public void setQuadra(Quadra quadra) {
		this.quadra = quadra;
	}
}
