package com.attornatus.laerson;

import com.attornatus.laerson.domain.model.Pessoa;
import com.attornatus.laerson.domain.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;


@SpringBootTest
public class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    private Pessoa pessoa;
    private Long pessoaId;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa("Laerson", LocalDate.of(1989, 1, 20));
        pessoa = pessoaRepository.save(pessoa);
        pessoaId = pessoa.getId();
    }

    @Test
    void quandoSalvar_entaoRetornePessoaSalva() {
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        assertThat(pessoaSalva.getId()).isNotNull();
        assertThat(pessoaSalva.getNome()).isEqualTo("Laerson");
        assertThat(pessoaSalva.getDataDeNascimento()).isEqualTo(LocalDate.of(1989, 1, 20));
    }

    @Test
    void quandoAtualizar_entaoRetornaPessoaAtualizada(){
        pessoa.setNome("Laerson Castro");
        Pessoa pessoaAtualizada = pessoaRepository.save(pessoa);
        assertThat(pessoaAtualizada.getId()).isNotNull();
        assertThat(pessoaAtualizada.getNome()).isEqualTo("Laerson Castro");
        assertThat(pessoaAtualizada.getDataDeNascimento()).isEqualTo(LocalDate.of(1989, 1, 20));
    }

    @Test
    void quandoBuscarPeloId_entaoRetornaPessoaEncontrada(){
        Optional<Pessoa> pessoaLocalizada = pessoaRepository.findById(pessoaId);
        assertThat(pessoaLocalizada.isPresent()).isTrue();
        assertThat(pessoaLocalizada.get().getNome()).isEqualTo("Laerson");
        assertThat(pessoaLocalizada.get().getDataDeNascimento()).isEqualTo(LocalDate.of(1989, 1, 20));
    }

    @Test
    void quandoExcluir_entaoPessoaNaoDeveSerEncontrada(){
        pessoaRepository.deleteById(pessoaId);
        Optional<Pessoa> pessoaLocalizada = pessoaRepository.findById(pessoaId);
        assertThat(pessoaLocalizada.isPresent()).isFalse();
    }

}
