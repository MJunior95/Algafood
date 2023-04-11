package com.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;
	
	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	
	public RestauranteNaoEncontradoException(Long id) {
		super(String.format("Não existe um cadastro de estado com código: %d", id));
	}
	
	


}
