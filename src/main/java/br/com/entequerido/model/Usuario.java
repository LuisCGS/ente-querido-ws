package br.com.entequerido.model;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.entequerido.model.enumeration.PerfilEnum;

public class Usuario extends Generico {
	
	@Size(min=3, message="Por favor, informe um login com mais de 2 caracteres!")
	private String login;
	
	@Size(min=3, message="Por favor, informe uma senha com mais de 2 caracteres!")
	private String senha;
	
	@NotEmpty(message="Por favor, informe pelo menos um perfil.")
	private List<PerfilEnum> listaPerfil;
	
	public Usuario() {
		super();
	}
	 
	public Usuario(String login, String senha, PerfilEnum... perfis) {
		super();
		this.login = login;
		this.senha = senha;
		this.listaPerfil = Arrays.asList(perfis);
	}
	
	public String getLogin() {
		return login;
	}
	public void getLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void getSenha(String password) {
		this.senha = password;
	}

	public List<PerfilEnum> getListaPerfil() {
		return listaPerfil;
	}

	public void setListaPerfil(List<PerfilEnum> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}
}
