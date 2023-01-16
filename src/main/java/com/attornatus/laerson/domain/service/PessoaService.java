package com.attornatus.laerson.domain.service;

import com.attornatus.laerson.api.model.input.PessoaResumo;
import com.attornatus.laerson.domain.exception.PessoaNaoEncontradaException;
import com.attornatus.laerson.domain.model.Pessoa;
import com.attornatus.laerson.domain.repository.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Pessoa atualizarPessoa(Long id, PessoaResumo pessoa) {
        Pessoa pessoaLocalizada = buscarPessoaOuFalhar(id);
        BeanUtils.copyProperties(pessoa, pessoaLocalizada, "id");
        return pessoaRepository.save(pessoaLocalizada);
    }

    public Pessoa salvarPessoa(PessoaResumo pessoaResumo){
        return converterEntidade(pessoaResumo);
    }

    public void excluirPessoa(Long id) {
        pessoaRepository.deleteById(buscarPessoaOuFalhar(id).getId());
    }

    public Pessoa buscarPessoaOuFalhar(Long id){
        return pessoaRepository.findById(id).orElseThrow(() -> new PessoaNaoEncontradaException(id));
    }

    private Pessoa converterEntidade(PessoaResumo pessoaResumo){
        return modelMapper.map(pessoaResumo, Pessoa.class);
    }

}
