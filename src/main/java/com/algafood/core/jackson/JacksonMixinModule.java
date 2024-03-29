package com.algafood.core.jackson;

import com.algafood.api.model.mixin.CidadeMixin;
import com.algafood.api.model.mixin.CozinhaMixin;
import com.algafood.api.model.mixin.GrupoMixin;
import com.algafood.api.model.mixin.RestauranteMixin;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.model.Grupo;
import com.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule(){
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Grupo.class, GrupoMixin.class);
    }


}
