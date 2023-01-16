package com.attornatus.laerson.api.exceptionhandler;

import com.attornatus.laerson.domain.exception.EnderecoNaoEncontradoException;
import com.attornatus.laerson.domain.exception.EnderecoNaoPertencePessoaException;
import com.attornatus.laerson.domain.exception.PessoaNaoEncontradaException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ArrayList<Erro.Problema> erros = new ArrayList<Erro.Problema>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()){
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            erros.add(new Erro.Problema(nome, mensagem));
        }

        Erro erro = new Erro();
        erro.setStatus(status.value());
        erro.setTitulo(messageSource.getMessage("campos-invalidos", null, LocaleContextHolder.getLocale()));
        erro.setProblemas(erros);

        return handleExceptionInternal(ex, erro, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<Erro.Problema> problemas = extrairCamposGerandoErro(ex);
        Erro erro = new Erro(status.value(), messageSource.getMessage("campos-invalidos", null, LocaleContextHolder.getLocale()), problemas);
        return new ResponseEntity<Object>(erro, new HttpHeaders(), erro.getStatus());
    }

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    private ResponseEntity<Object> handlePessoaNaoEncontrada(PessoaNaoEncontradaException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Erro erro = retornarErro(status, ex);
        return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EnderecoNaoEncontradoException.class)
    private ResponseEntity<Object> handleEnderecoNaoEncontrado(EnderecoNaoEncontradoException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Erro erro = retornarErro(status, ex);
        return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EnderecoNaoPertencePessoaException.class)
    private ResponseEntity<Object> handleEnderecoNaoPertencePessoa(EnderecoNaoPertencePessoaException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Erro erro = retornarErro(status, ex);
        return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
    }

    private List<Erro.Problema> extrairCamposGerandoErro(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof JsonMappingException) {
            JsonMappingException cause = (JsonMappingException) ex.getCause();
            List<Erro.Problema> problemas = new ArrayList<>();
            for (JsonMappingException.Reference reference : cause.getPath()) {
                problemas.add(new Erro.Problema(reference.getFieldName(),messageSource.getMessage("data-incorreta", null, LocaleContextHolder.getLocale())));
            }
            return problemas;
        }
        return null;
    }

    private Erro retornarErro(HttpStatus status, RuntimeException ex) {
        Erro erro = new Erro();
        erro.setStatus(status.value());
        erro.setTitulo(ex.getMessage());
        return erro;
    }

}
