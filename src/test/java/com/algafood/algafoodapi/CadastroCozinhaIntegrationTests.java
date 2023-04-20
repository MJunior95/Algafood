package com.algafood.algafoodapi;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CadastroCozinhaIntegrationTests {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void deveCadastroCozinhaComSucesso() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void deveFalharAoCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
	}
	
	@Test(expected = EntidadeEmUsoException.class)
	public void deveFalharAoExcluirCozinhaEmUso() {
		Long idCozinhaEmUso = 1l;
		cadastroCozinha.excluir(idCozinhaEmUso);
	}
	
	@Test(expected = EntidadeNaoEncontradaException.class)
	public void deveFalharAoExcluirCozinhaInexistente() {
		Long idCozinhaInexistente = 1000l;
		cadastroCozinha.excluir(idCozinhaInexistente);
	}

}
