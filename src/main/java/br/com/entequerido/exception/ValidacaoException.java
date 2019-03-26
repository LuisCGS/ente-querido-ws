package br.com.entequerido.exception;

public class ValidacaoException extends RuntimeException {
	private static final long serialVersionUID = 3846798883917799640L;
	
	private String path;
	
	public ValidacaoException(String message) {
		super(message);
	}
	
	public ValidacaoException(String message, String path) {
		super(message);
		this.path = path;
	}
	
	public ValidacaoException(String message, Throwable causa, String path) {
		super(message, causa);
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
