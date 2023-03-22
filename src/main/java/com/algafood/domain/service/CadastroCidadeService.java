package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncotradaException;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository repository;
	
	public Cidade salvar(Cidade cidade) {
		return repository.salvar(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			repository.remover(cidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncotradaException(
					String.format("Não existe um cadastro de cidade com código: %d", cidadeId));
		}
	}

}
