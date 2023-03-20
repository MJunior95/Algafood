package com.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;


@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository repository;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Cozinha> listar(){
		return repository.listar();
	}
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {
		Cozinha cozinha = repository.buscar(id);
		
		if(cozinha == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cozinha);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return repository.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable("cozinhaId") Long id,
			 @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = repository.buscar(id);
		
		if(cozinhaAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(cozinha,cozinhaAtual, "id");
		
		repository.salvar(cozinhaAtual);
		
		return ResponseEntity.ok(cozinhaAtual);
	
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover (@PathVariable("cozinhaId") Long id){
		try {
			Cozinha cozinha = repository.buscar(id);
			if(cozinha == null) {
				return ResponseEntity.notFound().build();
			}
			repository.remover(cozinha);
			return ResponseEntity.noContent().build();
			
		} catch (DataIntegrityViolationException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
