package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncotradaException;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository repository;
	
	public Estado salvar(Estado estado) {
		return repository.salvar(estado);
	}
	
	public void excluir(Long estadoId) {
		try {
			repository.remover(estadoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removida, pois está em uso", estadoId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncotradaException(
					String.format("Não existe um cadastro de estado com código: %d", estadoId));
		}
	}

}
