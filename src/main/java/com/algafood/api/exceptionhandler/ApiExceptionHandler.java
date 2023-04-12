package com.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradoException(
			EntidadeNaoEncontradaException e, WebRequest request){
		return handleExceptionInternal(
				e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND,request);
				
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(
			EntidadeEmUsoException e, WebRequest request){
		return handleExceptionInternal(
				e, e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT,request);
		
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(
			NegocioException e, WebRequest request){
		return handleExceptionInternal(
				e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST,request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		body = Problema.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(body == null ? status.getReasonPhrase() : body.toString())
				.build();
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
}
