package com.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncotradaException;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.repository.CidadeRepository;
import com.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository repository;

	@Autowired
	private CadastroCidadeService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Cidade> listar() {
		return repository.listar();
	}

	@GetMapping("/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable("cidadeId") Long id) {
		Cidade cidade = repository.buscar(id);

		if (cidade == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cidade);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		return service.salvar(cidade);
	}

	@PutMapping("/{cidadeId}")
	public ResponseEntity<Cidade> atualizar(@PathVariable("cidadeId") Long id, @RequestBody Cidade cidade) {
		Cidade cidadeAtual = repository.buscar(id);

		if (cidadeAtual == null) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(cidade, cidadeAtual, "id");

		service.salvar(cidadeAtual);

		return ResponseEntity.ok(cidadeAtual);

	}

	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<Cidade> remover(@PathVariable("cidadeId") Long id) {
		try {
			service.excluir(id);

			return ResponseEntity.noContent().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntidadeNaoEncotradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
