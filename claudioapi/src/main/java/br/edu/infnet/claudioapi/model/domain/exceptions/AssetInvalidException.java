package br.edu.infnet.claudioapi.model.domain.exceptions;

public class AssetInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AssetInvalidException(String message) {
		super(message);
	}

}
