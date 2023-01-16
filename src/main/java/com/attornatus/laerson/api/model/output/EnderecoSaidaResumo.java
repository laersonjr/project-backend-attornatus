package com.attornatus.laerson.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoSaidaResumo {

    private Long id;

    private String logradouro;

    private String cep;

    private String numero;

    private String cidade;

    private Boolean principal;

}
