package com.attornatus.laerson.api.model.input;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResumo {

    private Long id;

    @NotBlank
    @Size(max = 124)
    private String logradouro;

    @Size(min = 8, max = 8)
    @Column(length = 8)
    private String cep;

    private String numero;

    @NotBlank
    @Size(min = 2, max = 64)
    private String cidade;

}
