package com.attornatus.laerson.api.model.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PessoaResumo {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String nome;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;

}
