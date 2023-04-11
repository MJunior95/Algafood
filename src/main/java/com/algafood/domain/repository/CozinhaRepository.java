package com.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.domain.model.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{
	
	List<Cozinha> findAllByNomeContaining(String nome);
	
	Optional<Cozinha> findByNome(String nome);

	boolean existsByNome(String nome);
	
	
}
