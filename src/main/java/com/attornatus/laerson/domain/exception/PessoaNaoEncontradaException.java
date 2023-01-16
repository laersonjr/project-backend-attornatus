package com.attornatus.laerson.domain.exception;

public class PessoaNaoEncontradaException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public PessoaNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public PessoaNaoEncontradaException(Long pessoaId) {
        this(String.format("Não existe um cadastro de pessoa com código %d", pessoaId));
    }

}
