package com.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{

}
