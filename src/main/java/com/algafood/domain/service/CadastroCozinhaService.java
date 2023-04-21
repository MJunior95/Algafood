package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Não existe um cadastro de cozinha com código: %d";
	
	@Autowired
	private CozinhaRepository repository;

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return repository.save(cozinha);
	}

	@Transactional
	public void excluir(Long cozinhaId) {
		try {
			repository.deleteById(cozinhaId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, cozinhaId));
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
		}
	}
	
	public Cozinha buscarOuFalhar(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(id));
	}

}
