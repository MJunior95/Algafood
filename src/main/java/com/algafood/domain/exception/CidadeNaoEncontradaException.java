package com.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;
	
	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	
	public CidadeNaoEncontradaException(Long id) {
		super(String.format("Não existe um cadastro de cidade com código: %d", id));
	}
	
	


}
