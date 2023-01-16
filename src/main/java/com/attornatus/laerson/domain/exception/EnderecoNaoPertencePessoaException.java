package com.attornatus.laerson.domain.exception;

public class EnderecoNaoPertencePessoaException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EnderecoNaoPertencePessoaException(String mensagem){
        super(mensagem);
    }

}
