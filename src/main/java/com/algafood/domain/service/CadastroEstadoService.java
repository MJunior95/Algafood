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
	
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com código: %d";
	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private EstadoRepository repository;
	
	public Estado salvar(Estado estado) {
		return repository.save(estado);
	}
	
	public void excluir(Long estadoId) {
		try {
			repository.deleteById(estadoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, estadoId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncotradaException(
					String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
		}
	}
	
	public Estado buscarOuFalhar(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncotradaException(
						String.format(MSG_ESTADO_NAO_ENCONTRADO, id)));
	}

}
