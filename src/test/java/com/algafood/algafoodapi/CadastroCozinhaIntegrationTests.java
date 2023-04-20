package com.algafood.algafoodapi;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
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
	private int port;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Before
	public void setUp(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	}

	@Test
	public void deveRetornarStatus200QuandoConsultarCozinhas(){
		RestAssured.given()
					.accept(ContentType.JSON)
				.when()
					.get()//Se refere ao metodo GET do HTTP
				.then()
					.statusCode(HttpStatus.OK.value());
	}
	@Test
	public void deveConterQuatroCozinhasQuandoConsultarCozinhas(){
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get()//Se refere ao metodo GET do HTTP
				.then()
				.body("", Matchers.hasSize(4))
				.body("nome", Matchers.hasItems("Indiana", "Tailandesa"));
	}

	@Test
	public void deveRetornarStatus201QQuandoCadastrarCozinha(){
		RestAssured.given()
				.body("{\"nome\": \"Chinesa\" }")
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.when().post()
				.then()
				.statusCode(HttpStatus.CREATED.value());
	}
}
