package br.com.entequerido.model.enumeration;

public enum PerfilEnum {
	ADMINISTRADOR	("Administrador"),
	USUARIO			("Usuario");
	
	private String descricao;
	
	PerfilEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public PerfilEnum obterPorOrdinal(Integer ordinal) {
		for(PerfilEnum perfilEnum : PerfilEnum.values()) {
			if(this.ordinal() == ordinal) {
				return perfilEnum;
			}
		}
		
		return null;
	}
	
	public PerfilEnum obterPorOrdinal(String descricao) {
		for(PerfilEnum perfilEnum : PerfilEnum.values()) {
			if(this.descricao == descricao) {
				return perfilEnum;
			}
		}
		
		return null;
	}
}
