package br.com.entequerido.model.enumeration;

public enum SepultamentoEnum {
	GERAL		("Geral", "Sepultura em terra"),
	PERPETUO	("Perpétuo", "Sepultura em jazigo famíliar"),
	RESERVADO	("Reservado", "Sepultura familiar reservada para futuros entes"),
	TEMPORARIO	("Temporário", "Sepultura temporária");
	
	private String tipo;
	private String descricao;
	
	SepultamentoEnum (String tipo, String descricao) {
		this.tipo = tipo;
		this.descricao = descricao;
	}
	
	public SepultamentoEnum obterPorOrdinal(Integer ordinal) {
		for(SepultamentoEnum placaEnum : SepultamentoEnum.values()) {
			if(this.ordinal() == ordinal) {
				return placaEnum;
			}
		}
		
		return null;
	}
	
	public SepultamentoEnum obterPorOrdinal(String descricao) {
		for(SepultamentoEnum corEnum : SepultamentoEnum.values()) {
			if(this.descricao == descricao) {
				return corEnum;
			}
		}
		
		return null;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDescricao() {
		return descricao;
	}
}
