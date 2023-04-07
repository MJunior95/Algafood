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
	
	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com código: %d";
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private CidadeRepository repository;
	
	public Cidade salvar(Cidade cidade) {
		return repository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			repository.deleteById(cidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, cidadeId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncotradaException(
					String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));
		}
	}
	

	public Cidade buscarOuFalhar(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncotradaException(
						String.format(MSG_CIDADE_NAO_ENCONTRADA, id)));
	}


}
