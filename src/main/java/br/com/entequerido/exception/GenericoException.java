package br.com.entequerido.exception;

public class GenericoException extends Exception {
	private static final long serialVersionUID = 1691504694876064234L;
	
	private String path;

	public GenericoException(String message, Throwable cause, String path) {
		super(message, cause);
		
		this.path = path;
	}

	public GenericoException(String message, Throwable causa) {
		super(message, causa);
	}
	
	public GenericoException(String message, String path) {
		super(message);
		
		this.path = path;
	}

	public GenericoException(String message) {
		super(message);
	}

	public GenericoException(Throwable causa) {
		super(causa);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
