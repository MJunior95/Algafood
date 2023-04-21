package com.algafood.api.model.mixin;

import com.algafood.domain.model.Permissao;
import com.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class GrupoMixin {

    @JsonIgnore
    private List<Permissao> permissoes = new ArrayList<>();
}
