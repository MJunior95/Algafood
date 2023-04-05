package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.algafood.domain.exception.EntidadeNaoEncotradaException;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository repository;

	public Cozinha salvar(Cozinha cozinha) {
		return repository.save(cozinha);
	}

	public void excluir(Long cozinhaId) {
		try {
			repository.deleteById(cozinhaId);

		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("Cozinha de código %d não pode ser removida, pois está em uso", cozinhaId));
		//	throw new EntidadeEmUsoException(
			//		String.format("Cozinha de código %d não pode ser removida, pois está em uso", cozinhaId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncotradaException(
					String.format("Não existe um cadastro de cozinha com código: %d", cozinhaId));
		}
	}

}
