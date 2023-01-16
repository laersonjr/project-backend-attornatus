package com.attornatus.laerson.domain.repository;

import com.attornatus.laerson.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

        @Query(nativeQuery = true,
            value = "SELECT COALESCE(COUNT(a.pessoa_id), 0) FROM ENDERECO a WHERE a.pessoa_id = ?1")
    Integer verificarQuantidadeDeEnderecoPessoa(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE ENDERECO a SET a.principal = false where a.pessoa_id = ?1")
    void atualizaEnderecoParaInativo(@Param("id") Long id);

    List<Endereco> findByPessoaId(Long id);
}