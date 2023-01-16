package com.attornatus.laerson;

import com.attornatus.laerson.domain.exception.EnderecoNaoEncontradoException;
import com.attornatus.laerson.domain.exception.EnderecoNaoPertencePessoaException;
import com.attornatus.laerson.domain.exception.PessoaNaoEncontradaException;
import com.attornatus.laerson.domain.model.Endereco;
import com.attornatus.laerson.domain.model.Pessoa;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String PESSOA_URL = "/api/pessoas";

    @Test
    void quandoCriarUmaPessoa_entaoRetorno201() throws Exception {
        Pessoa pessoa = new Pessoa("Laerson", LocalDate.of(1989,1,20));
        mockMvc.perform(post(PESSOA_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoa)))
                        .andExpect(status().isCreated());
    }

    @Test
    void quandoPassarDadosInvalidos_entaoRetornaUmErro() throws Exception{
        Pessoa pessoa = new Pessoa("", LocalDate.of(1989,1,20));
        mockMvc.perform(post(PESSOA_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoa)))
                        .andExpect(status().isBadRequest())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void quandoPassarUmIdPessoaNaoCadastrado_entaoRetorna404() throws Exception{
        int idPessoa = 100;
        mockMvc.perform(get(PESSOA_URL + "/" + idPessoa))
                        .andExpect(status().isNotFound())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof PessoaNaoEncontradaException));
    }

    @Test
    void quandoPassarUmIdPessoaCadastrada_entaoRetorna200() throws Exception{
        int idPessoa = 1;
        mockMvc.perform(get(PESSOA_URL + "/" + idPessoa))
                        .andExpect(status().isOk());
    }

    @Test
    void quandoPassarUmNomePessoaCadastrada_entaoRetorna200() throws Exception{
        String nome = "Laer";
        mockMvc.perform(get(PESSOA_URL + "?" + nome))
                        .andExpect(status().isOk());
    }

    @Test
    void quandoExcluirUmaPessoaCadastrada_entaoRetorna204() throws Exception{
        int id = 5;
        mockMvc.perform(delete(PESSOA_URL + "/" + id))
                        .andExpect(status().isNoContent());
    }

    @Test
    void quandoExcluirUmaPessoaNaoCadastrada_entaoRetorna404() throws Exception{
        int id = 100;
        mockMvc.perform(delete(PESSOA_URL + "/" + id))
                        .andExpect(status().isNotFound())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof PessoaNaoEncontradaException));
    }

    @Test
    void quandoAtualizarUmaPessoaCadastrada_entaoRetorna200() throws Exception{
        Long id = 10L;
        String nome = "Laerson Castro";
        LocalDate dataDeNascimento = LocalDate.of(1989,1,20);

        Pessoa pessoa = new Pessoa(nome, dataDeNascimento);
        pessoa.setId(id);

        mockMvc.perform(put(PESSOA_URL+ "/" + pessoa.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoa)))
                        .andExpect(status().isOk());
    }

    @Test
    void quandoCadastrarUmEnderecoParaPessoa_entaoRetorna201() throws Exception{
        Pessoa pessoa = new Pessoa("Laerson", LocalDate.of(1989,1,20));
        pessoa.setId(1L);

        Endereco endereco = new Endereco("Rua Qualquer", "58400000", "100", "Cidade Qq", true, pessoa );
        mockMvc.perform(post(PESSOA_URL + "/" + pessoa.getId() + "/endereco")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endereco)))
                        .andExpect(status().isCreated());
    }

    @Test
    void quandoBuscarEnderecosParaPessoaCadastradas_entaoRetorna200() throws Exception{
        Long pessoaId = 1L;

        mockMvc.perform(get(PESSOA_URL + "/" + pessoaId + "/endereco"))
                        .andExpect(status().isOk());
    }

    @Test
    void quandoBuscarEnderecosParaPessoaNaoCadastrada_entaoRetorna404() throws Exception{
        Long pessoaId = 100L;

        mockMvc.perform(get(PESSOA_URL + "/" + pessoaId + "/endereco"))
                        .andExpect(status().isNotFound())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof PessoaNaoEncontradaException));
    }

    @Test
    void quandoAtualizoEnderecoPrincipalParaPessoaCadastrada_entaoRetorna200() throws Exception{
        Long pessoaId = 1L;
        Long enderecoId = 1L;
        mockMvc.perform(put(PESSOA_URL + "/" + pessoaId + "/endereco/" + enderecoId))
                        .andExpect(status().isOk());
    }

    @Test
    void quandoAtualizoEnderecoPrincipalParaEnderecoNaoCadastrado_entaoRetorna404() throws Exception{
        Long pessoaId = 1L;
        Long enderecoId = 100L;
        mockMvc.perform(put(PESSOA_URL + "/" + pessoaId + "/endereco/" + enderecoId))
                        .andExpect(status().isNotFound())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof EnderecoNaoEncontradoException));
    }

    @Test
    void quandoAtualizoEnderecoPrincipalParaEnderecoNaoPertencePessoa_entaoRetorna400() throws Exception{
        Long pessoaId = 1L;
        Long enderecoId = 10L;
        mockMvc.perform(put(PESSOA_URL + "/" + pessoaId + "/endereco/" + enderecoId))
                        .andExpect(status().isBadRequest())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof EnderecoNaoPertencePessoaException));
    }

}
