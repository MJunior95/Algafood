package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.EstadoRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {
	
	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private EstadoRepository repository;

	@Transactional
	public Estado salvar(Estado estado) {
		return repository.save(estado);
	}

	@Transactional
	public void excluir(Long estadoId) {
		try {
			repository.deleteById(estadoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, estadoId));
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(estadoId);
		}
	}
	
	public Estado buscarOuFalhar(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}

}
