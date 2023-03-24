package com.algafood.api.controller;

import java.util.List;
import java.util.Optional;

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
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.EstadoRepository;
import com.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository repository;

	@Autowired
	private CadastroEstadoService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Estado> listar() {
		return repository.findAll();
	}

	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable("estadoId") Long id) {
		Optional<Estado> estado = repository.findById(id);

		if (estado.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(estado.get());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return service.salvar(estado);
	}

	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable("estadoId") Long id, @RequestBody Estado estado) {
		Optional<Estado> estadoAtual = repository.findById(id);

		if (estadoAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(estado, estadoAtual.get(), "id");

		Estado estadoSalvo = service.salvar(estadoAtual.get());

		return ResponseEntity.ok(estadoSalvo);

	}

	@DeleteMapping("/{estadoId}")
	public ResponseEntity<Estado> remover(@PathVariable("estadoId") Long id) {
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
