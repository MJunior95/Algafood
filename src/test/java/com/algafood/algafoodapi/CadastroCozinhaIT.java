package com.algafood.algafoodapi;

import com.algafood.algafoodapi.util.DatabaseCleaner;
import com.algafood.algafoodapi.util.ResourceUtils;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

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
	private static final int COZINHA_ID_INEXISTENTE = 200;

	private Cozinha cozinhaTailandesa;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;


	@Before
	public void setUp(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		this.prepararDados();

		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
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
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get()
				.then()
				.body("", hasSize(quantidadeCozinhasCadastradas));
	}

	@Test
	public void deveRetornarStatus201QQuandoCadastrarCozinha(){
		RestAssured.given()
					.body(jsonCorretoCozinhaChinesa)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
				.when()
					.post()
				.then()
					.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarRespostaEStatusCorretosQuandoConsultarCozinhaExistente(){
		RestAssured.given()
					.pathParam("cozinhaId", cozinhaTailandesa.getId())
					.accept(ContentType.JSON)
				.when()
					.get("/{cozinhaId}")//Se refere ao metodo GET do HTTP
				.then()
					.statusCode(HttpStatus.OK.value())
					.body("nome", equalTo(cozinhaTailandesa.getNome()));

	}
	@Test
	public void deveRetornarStatus404QuandoConsultarCozinhaInexistente(){
		RestAssured.given()
					.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
					.accept(ContentType.JSON)
				.when()
					.get("/{cozinhaId}")//Se refere ao metodo GET do HTTP
				.then()
					.statusCode(HttpStatus.NOT_FOUND.value());

	}

	private void prepararDados(){
		Cozinha brasileira = new Cozinha();
		brasileira.setNome("Brasileira");
		cozinhaRepository.save(brasileira);

		cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailandesa);

		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}
}
