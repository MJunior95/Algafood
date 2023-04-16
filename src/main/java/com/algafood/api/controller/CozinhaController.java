package com.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository repository;

	@Autowired
	private CadastroCozinhaService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Cozinha> listar() {
		return repository.findAll();
	}

	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable("cozinhaId") Long id) {

		return service.buscarOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return service.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable("cozinhaId") Long id, @RequestBody @Valid Cozinha cozinha) {

		Cozinha cozinhaAtual = service.buscarOuFalhar(id);

		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

		return service.salvar(cozinhaAtual);

	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("cozinhaId") Long id) {
		service.excluir(id);

	}
}
