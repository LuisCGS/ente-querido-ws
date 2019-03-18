package br.com.entequerido.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class CertidaoObito extends Generico {
	@DBRef
	@NotNull(message="Por favor, informe uma pessoa.")
	private Pessoa pessoa;
	
	@NotNull(message="Por favor, informe o nome do ofício.")
	private String nomeOficio;
	
	@NotNull(message="Por favor, informe o oficial registrador.")
	private String oficialRegistrador;
	
	@NotNull(message="Por favor, informe o município e o UF do ofício")
	private String muncipioUfOficio;
	
	@NotNull(message="Por favor, informe o endereco do ofício")
	private String enderecoOficio;

	public CertidaoObito(String codigo) {
		super(codigo);
	}

	public CertidaoObito(String codigo, String nomeOficio, Pessoa pessoa, String oficialRegistrador,
			String muncipioUfOficio, String enderecoOficio) {
		super(codigo);
		this.nomeOficio = nomeOficio;
		this.pessoa = pessoa;
		this.oficialRegistrador = oficialRegistrador;
		this.muncipioUfOficio = muncipioUfOficio;
		this.enderecoOficio = enderecoOficio;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getNomeOficio() {
		return nomeOficio;
	}

	public void setNomeOficio(String nomeOficio) {
		this.nomeOficio = nomeOficio;
	}

	public String getOficialRegistrador() {
		return oficialRegistrador;
	}

	public void setOficialRegistrador(String oficialRegistrador) {
		this.oficialRegistrador = oficialRegistrador;
	}

	public String getMuncipioUfOficio() {
		return muncipioUfOficio;
	}

	public void setMuncipioUfOficio(String muncipioUfOficio) {
		this.muncipioUfOficio = muncipioUfOficio;
	}

	public String getEnderecoOficio() {
		return enderecoOficio;
	}

	public void setEnderecoOficio(String enderecoOficio) {
		this.enderecoOficio = enderecoOficio;
	}
}
