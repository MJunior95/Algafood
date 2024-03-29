package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.CidadeRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private CidadeRepository repository;

	@Autowired

	private CadastroEstadoService serviceEstado;

	@Transactional
	public Cidade salvar(Cidade cidade) {

		Long estadoId = cidade.getEstado().getId();

		Estado estado = serviceEstado.buscarOuFalhar(estadoId);

		cidade.setEstado(estado);

		return repository.save(cidade);
	}

	@Transactional
	public void excluir(Long cidadeId) {
		try {
			repository.deleteById(cidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, cidadeId));
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
		}
	}
	

	public Cidade buscarOuFalhar(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new CidadeNaoEncontradaException(id));
	}


}
