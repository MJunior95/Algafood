package com.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {
	
	@Id
	@EqualsAndHashCode.Include
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;
	
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	@JsonIgnore
	private Endereco endereco;
	
	@CreationTimestamp 
	@JsonIgnore
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;

	@UpdateTimestamp
	@JsonIgnore
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;
	
	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
}
