package com.algafood.domain.repository;

import java.util.List;

import com.algafood.domain.model.Cozinha;

public interface CozinhaRepository2 {

	List<Cozinha> listar ();
	
	Cozinha buscar(Long id);
	
	Cozinha salvar(Cozinha cozinha);
	
	void remover(Cozinha cozinha);
}
