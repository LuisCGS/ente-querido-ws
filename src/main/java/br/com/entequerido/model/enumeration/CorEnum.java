package br.com.entequerido.model.enumeration;

public enum CorEnum {
	BRANCO	("Branco"),
	PRETO	("Preto"),
	PARDO	("Pardo"),
	AMARELO	("Amarelo");
	
	private String descricao;
	
	CorEnum (String descricao){
		this.descricao = descricao;
	}
	
	public CorEnum obterPorOrdinal(Integer ordinal) {
		for(CorEnum corEnum : CorEnum.values()) {
			if(this.ordinal() == ordinal) {
				return corEnum;
			}
		}
		
		return null;
	}
	
	public CorEnum obterPorOrdinal(String descricao) {
		for(CorEnum corEnum : CorEnum.values()) {
			if(this.descricao == descricao) {
				return corEnum;
			}
		}
		
		return null;
	}
}
