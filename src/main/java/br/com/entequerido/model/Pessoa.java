package br.com.entequerido.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.DBRef;

import br.com.entequerido.model.enumeration.CorEnum;
import br.com.entequerido.model.enumeration.EstadoCivilEnum;
import br.com.entequerido.model.enumeration.SexoEnum;

public class Pessoa extends Generico{
	@NotNull(message="Por favor, informe a matrícula")
	private String matricula;
	
	@Size(min=3, message="Por favor, informe um nome de rua com mais de 2 caracteres!")
	private String nome;
	
	@NotNull(message="Por favor, informe a data de nascimento.")
	private Date dataNascimento;
	
	@NotNull(message="Por favor, informe a data de falecimento.")
	private Date dataFalecimento;
	
	@NotNull(message="Por favor, informe o sexo.")
	private SexoEnum sexoEnum;
	
	@NotNull(message="Por favor, informe a cor.")
	private CorEnum corEnum;
	
	@NotNull(message="Por favor, informe o estado civil.")
	private EstadoCivilEnum estadoCivilEnum;
	
	@NotNull(message="Por favor, informe a profissão.")
	private String profissao;
	
	@NotNull(message="Por favor, informe o concílio ou a residência")
	private String residencia;
	
	@NotNull(message="Por favor, informe o documento de identificacao")
	private String documentoIdentificacao;
	
	@NotNull(message="Por favor, informe o campo eleitor")
	private Boolean eleitor;
	
	@NotNull(message="Por favor, informe a filiação, profissão e residência")
	private String filiacaoProfissaoResidencia;
	
	@NotNull(message="Por favor, informe o local de falecimento")
	private String localFalecimento;
	
	@NotNull(message="Por favor, informe a causa da morte")
	private String causaMorte;
	
	@NotNull(message="Por favor, informe o sepultamento")
	private String sepultamento;
	
	@NotNull(message="Por favor, informe o tumulo em que a pessoa se encontra")
	@DBRef
	private Tumulo tumulo;
	
	private String cartorioCasamento;
	private String nomeConjuge;
	private String declarante;
	private String observacoes;
	
	@DBRef
	private List<Filho> listaFilho;
	
	public Pessoa() {
		super();
	}
	
	public Pessoa(String codigo, String matricula, String nome, Date dataNascimento, Date dataFalecimento,
			SexoEnum sexoEnum,  CorEnum corEnum, EstadoCivilEnum estadoCivilEnum, String profissao, String residencia, 
			String documentoIdentificacao, Boolean eleitor, String filiacaoProfissaoResidencia, String localFalecimento, String causaMorte, 
			String sepultamento, Tumulo tumulo, String cartorioCasamento, String nomeConjuge, String declarante, String observacoes, List<Filho> listaFilho) {
		super(codigo);
		this.matricula = matricula;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.dataFalecimento = dataFalecimento;
		this.sexoEnum = sexoEnum;
		this.corEnum = corEnum;
		this.estadoCivilEnum = estadoCivilEnum;
		this.profissao = profissao;
		this.residencia = residencia;
		this.documentoIdentificacao = documentoIdentificacao;
		this.eleitor = eleitor;
		this.filiacaoProfissaoResidencia = filiacaoProfissaoResidencia;
		this.localFalecimento = localFalecimento;
		this.causaMorte = causaMorte;
		this.sepultamento = sepultamento;
		this.tumulo = tumulo;
		this.cartorioCasamento = cartorioCasamento;
		this.nomeConjuge = nomeConjuge;
		this.declarante = declarante;
		this.observacoes = observacoes;
		this.listaFilho = listaFilho;
	}
	
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
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
	public SexoEnum getSexoEnum() {
		return sexoEnum;
	}
	public void setSexoEnum(SexoEnum sexoEnum) {
		this.sexoEnum = sexoEnum;
	}
	public CorEnum getCorEnum() {
		return corEnum;
	}
	public void setCorEnum(CorEnum corEnum) {
		this.corEnum = corEnum;
	}
	public EstadoCivilEnum getEstadoCivilEnum() {
		return estadoCivilEnum;
	}
	public void setEstadoCivilEnum(EstadoCivilEnum estadoCivilEnum) {
		this.estadoCivilEnum = estadoCivilEnum;
	}
	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	public String getResidencia() {
		return residencia;
	}
	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}
	public String getDocumentoIdentificacao() {
		return documentoIdentificacao;
	}
	public void setDocumentoIdentificacao(String documentoIdentificacao) {
		this.documentoIdentificacao = documentoIdentificacao;
	}
	public Boolean getEleitor() {
		return eleitor;
	}
	public void setEleitor(Boolean eleitor) {
		this.eleitor = eleitor;
	}
	public String getFiliacaoProfissaoResidencia() {
		return filiacaoProfissaoResidencia;
	}
	public void setFiliacaoProfissaoResidencia(String filiacaoProfissaoResidencia) {
		this.filiacaoProfissaoResidencia = filiacaoProfissaoResidencia;
	}
	public String getLocalFalecimento() {
		return localFalecimento;
	}
	public void setLocalFalecimento(String localFalecimento) {
		this.localFalecimento = localFalecimento;
	}
	public String getCausaMorte() {
		return causaMorte;
	}
	public void setCausaMorte(String causaMorte) {
		this.causaMorte = causaMorte;
	}
	public String getSepultamento() {
		return sepultamento;
	}
	public void setSepultamento(String sepultamento) {
		this.sepultamento = sepultamento;
	}
	public Tumulo getTumulo() {
		return tumulo;
	}
	public void setTumulo(Tumulo tumulo) {
		this.tumulo = tumulo;
	}
	public String getCartorioCasamento() {
		return cartorioCasamento;
	}
	public void setCartorioCasamento(String cartorioCasamento) {
		this.cartorioCasamento = cartorioCasamento;
	}
	public String getNomeConjuge() {
		return nomeConjuge;
	}
	public void setNomeConjuge(String nomeConjuge) {
		this.nomeConjuge = nomeConjuge;
	}
	public String getDeclarante() {
		return declarante;
	}
	public void setDeclarante(String declarante) {
		this.declarante = declarante;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public List<Filho> getListaFilho() {
		return listaFilho;
	}
	public void setListaFilho(List<Filho> listaFilho) {
		this.listaFilho = listaFilho;
	}
}
