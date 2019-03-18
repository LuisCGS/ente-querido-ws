package br.com.entequerido.enumeration;

public enum SexoEnum {
	MASCULINO	("Masculino"),
	FEMININO	("Feminino");
	
	private String descricao;
	
	SexoEnum (String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}	
	
	public SexoEnum obterPorOrdinal(Integer ordinal) {
		for(SexoEnum sexoEnum : SexoEnum.values()) {
			if(this.ordinal() == ordinal) {
				return sexoEnum;
			}
		}
		
		return null;
	}
	
	public SexoEnum obterPorOrdinal(String descricao) {
		for(SexoEnum sexoEnum : SexoEnum.values()) {
			if(this.descricao == descricao) {
				return sexoEnum;
			}
		}
		
		return null;
	}
}
