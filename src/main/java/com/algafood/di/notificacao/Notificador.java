package com.algafood.di.notificacao;

import com.algafood.di.modelo.Cliente;

public interface Notificador {

	void notificar(Cliente cliente, String mensagem);
}
