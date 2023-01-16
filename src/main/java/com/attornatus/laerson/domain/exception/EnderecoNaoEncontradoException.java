package com.attornatus.laerson.domain.exception;

public class EnderecoNaoEncontradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EnderecoNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public EnderecoNaoEncontradoException(Long enderecoId) {
        this(String.format("Não existe um endereço cadastrado com código %d", enderecoId));
    }

}
