package com.attornatus.laerson;

import com.attornatus.laerson.domain.model.Endereco;
import com.attornatus.laerson.domain.model.Pessoa;
import com.attornatus.laerson.domain.repository.EnderecoRepository;
import com.attornatus.laerson.domain.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    /*
    * @Autowired
    * private TestEntityManager testEntityManager;
    * */

    @Test
    void quandoSalvarEndereco_retorneEnderecoSalvo(){
        Pessoa pessoa = new Pessoa("Laerson Castro", LocalDate.of(1989,01,20));
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        Endereco endereco = new Endereco("Rua Qualquer", "58400000", "100", "Cidade Qq", true, pessoa);

        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        assertThat(enderecoSalvo.getId()).isNotNull();
        assertThat(enderecoSalvo.getLogradouro()).isEqualTo(endereco.getLogradouro());
        assertThat(enderecoSalvo.getCep()).isEqualTo(endereco.getCep());
        assertThat(enderecoSalvo.getNumero()).isEqualTo(endereco.getNumero());
        assertThat(enderecoSalvo.getCidade()).isEqualTo(endereco.getCidade());
        assertThat(enderecoSalvo.getPrincipal()).isEqualTo(endereco.getPrincipal());
        assertThat(enderecoSalvo.getPessoa().getId()).isEqualTo(pessoaSalva.getId());
    }

    @Test
    void quandoBuscarEnderecoDePessoa_entaoRetornarUmaListaDeEndereco(){
        Pessoa pessoa = new Pessoa("Laerson Castro", LocalDate.of(1989,01,20));
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        Endereco endereco1 = new Endereco("Rua Qualquer", "58400000", "100", "Cidade Qq", true, pessoa);
        Endereco endereco2 = new Endereco("Rua Qualquer2", "58400002", "200", "Cidade Qq 2", true, pessoa);

        enderecoRepository.save(endereco1);
        enderecoRepository.save(endereco2);

        List<Endereco> listaDeEndereco = enderecoRepository.findByPessoaId(pessoa.getId());

        assertThat(listaDeEndereco).hasSize(2);

        assertThat(listaDeEndereco.get(0)).isEqualToComparingFieldByField(endereco1);
        assertThat(listaDeEndereco.get(1)).isEqualToComparingFieldByField(endereco2);

    }

}
