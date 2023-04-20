package com.algafood.algafoodapi;

import com.algafood.algafoodapi.util.DatabaseCleaner;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algafood.domain.service.CadastroCozinhaService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Before
	public void setUp(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		this.prepararDados();
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
	public void deveConterDuasCozinhasQuandoConsultarCozinhas(){
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get()//Se refere ao metodo GET do HTTP
				.then()
				.body("", Matchers.hasSize(2))
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

	private void prepararDados(){
		Cozinha cozinha1 =  new Cozinha();
		cozinha1.setNome("Indiana");

		cozinhaRepository.save(cozinha1);

		Cozinha cozinha2 =  new Cozinha();
		cozinha2.setNome("Tailandesa");
		cozinhaRepository.save(cozinha2);
	}
}
