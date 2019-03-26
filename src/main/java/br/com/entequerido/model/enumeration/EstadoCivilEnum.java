package br.com.entequerido.model.enumeration;

public enum EstadoCivilEnum {
	CASADO		("Casado"),
	SOLTEIRO	("Solteiro"),
	VIUVO		("Viúvo"),
	SEPARADO	("Separado"),
	DIVORCIADO	("Divorciado");
	
	private String descricao;
	
	EstadoCivilEnum (String descricao) {
		this.descricao = descricao;
	}
	
	public EstadoCivilEnum obterPorOrdinal(Integer ordinal) {
		for(EstadoCivilEnum estadoCivilEnum : EstadoCivilEnum.values()) {
			if(this.ordinal() == ordinal) {
				return estadoCivilEnum;
			}
		}
		
		return null;
	}
	
	public EstadoCivilEnum obterPorOrdinal(String descricao) {
		for(EstadoCivilEnum estadoCivilEnum : EstadoCivilEnum.values()) {
			if(this.descricao == descricao) {
				return estadoCivilEnum;
			}
		}
		
		return null;
	}
}
