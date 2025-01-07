package com.example.sustentaree.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class EstoqueInsuficienteException extends RuntimeException {
  public EstoqueInsuficienteException(String mensagem) {
    super(mensagem);
  }
}