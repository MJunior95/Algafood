package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncotradaException;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Não existe um cadastro de cozinha com código: %d";
	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha de código %d não pode ser removida, pois está em uso";
	@Autowired
	private CozinhaRepository repository;

	public Cozinha salvar(Cozinha cozinha) {
		return repository.save(cozinha);
	}

	public void excluir(Long cozinhaId) {
		try {
			repository.deleteById(cozinhaId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncotradaException(
					String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}
	
	public Cozinha buscarOuFalhar(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncotradaException(
						String.format(MSG_COZINHA_NAO_ENCONTRADA, id)));
	}

}
