package com.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;
	
	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	
	public CozinhaNaoEncontradaException(Long id) {
		super(String.format("Não existe um cadastro de restaurante com código: %d", id));
	}
	
	


}
