package com.attornatus.laerson.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Erro {

    private Integer status;
    private String titulo;
    private List<Problema> problemas;

    @AllArgsConstructor
    @Getter
    public static class Problema{

        private String campo;

        private String mensagem;

    }

}
