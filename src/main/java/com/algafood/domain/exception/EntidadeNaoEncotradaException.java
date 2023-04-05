package com.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)//, reason = "Entidade não encontrada")
public class EntidadeNaoEncotradaException extends ResponseStatusException {
	
	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncotradaException(HttpStatus status, String mensagem) {
		super(status, mensagem);
	}

	public EntidadeNaoEncotradaException(String mensagem) {
		this(HttpStatus.NOT_FOUND, mensagem);
	}

}
