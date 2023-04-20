package com.algafood.algafoodapi;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CadastroCozinhaIntegrationTests {

	@LocalServerPort
	private int Port;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void deveRetornarStatus200QuandoConsultarCozinhas(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		RestAssured.given()
					.basePath("/cozinhas")
					.port(8080)
					.accept(ContentType.JSON)
				.when()
					.get()
				.then()
					.statusCode(HttpStatus.OK.value());
	}
	@Test
	public void deveConterQuatroCozinhasQuandoConsultarCozinhas(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		RestAssured.given()
					.basePath("/cozinhas")
					.port(8080)
					.accept(ContentType.JSON)
				.when()
					.get()
				.then()
					.body("", Matchers.hasSize(4))
					.body("nome", Matchers.hasItems("Indiana", "Tailandesa"));
	}
}
