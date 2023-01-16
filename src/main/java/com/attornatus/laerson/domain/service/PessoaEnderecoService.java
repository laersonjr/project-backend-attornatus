package com.attornatus.laerson.domain.service;

import com.attornatus.laerson.api.model.input.EnderecoResumo;
import com.attornatus.laerson.api.model.output.EnderecoSaidaResumo;
import com.attornatus.laerson.domain.exception.EnderecoNaoEncontradoException;
import com.attornatus.laerson.domain.exception.EnderecoNaoPertencePessoaException;
import com.attornatus.laerson.domain.model.Endereco;
import com.attornatus.laerson.domain.model.Pessoa;
import com.attornatus.laerson.domain.repository.EnderecoRepository;
import com.attornatus.laerson.domain.repository.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaEnderecoService {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    public EnderecoSaidaResumo cadastrarEnderecoParaPessoa(Long id, EnderecoResumo enderecoResumo) {
        Pessoa pessoa = pessoaService.buscarPessoaOuFalhar(id);
        Endereco endereco = converterParaEntidade(enderecoResumo);
        endereco.setPessoa(pessoa);
        endereco.setPrincipal(verificarExistenciaEnderecoPessoa(pessoa.getId()));
        enderecoRepository.save(endereco);
        EnderecoSaidaResumo enderecoSaidaResumo = converterParaResumo(endereco);
        return  enderecoSaidaResumo;
    }

    public List<EnderecoSaidaResumo> listarEnderecos(Long id) {
        Pessoa pessoa = pessoaService.buscarPessoaOuFalhar(id);
        List<Endereco> listaEndereco = enderecoRepository.findByPessoaId(id);
        return converterParaListaResumo(listaEndereco);
    }

    public EnderecoSaidaResumo atualizarEnderecoPrincipalPessoa(Long id, Long enderecoId) {
        Pessoa pessoa = pessoaService.buscarPessoaOuFalhar(id);
        Endereco endereco = buscarEnderecoOuFalhar(enderecoId);
        validarEnderecoPertencePessoa(pessoa, endereco);
        enderecoRepository.atualizaEnderecoParaInativo(id);
        endereco.setPrincipal(true);
        enderecoRepository.save(endereco);
        EnderecoSaidaResumo enderecoSaidaResumo = converterParaResumo(endereco);
        return enderecoSaidaResumo;

    }

    private void validarEnderecoPertencePessoa(Pessoa pessoa, Endereco endereco) {
        if(pessoa.getId() != endereco.getPessoa().getId()){
            throw new EnderecoNaoPertencePessoaException("O endereço informado não pertence a pessoa!");
        }
    }

    private Endereco converterParaEntidade(EnderecoResumo enderecoResumo){
        return modelMapper.map(enderecoResumo, Endereco.class);
    }

    private EnderecoSaidaResumo converterParaResumo(Endereco endereco){
        return modelMapper.map(endereco, EnderecoSaidaResumo.class);
    }

    private List<EnderecoSaidaResumo> converterParaListaResumo(List<Endereco> enderecos){
        return enderecos.stream().map(this::converterParaResumo).collect(Collectors.toList());
    }

    private Boolean verificarExistenciaEnderecoPessoa(Long id){
        Integer quantidadeEndereco = enderecoRepository.verificarQuantidadeDeEnderecoPessoa(id);
        return quantidadeEndereco > 0 ? false : true;
    }

    private Endereco buscarEnderecoOuFalhar(Long id){
        return enderecoRepository.findById(id).orElseThrow(() -> new EnderecoNaoEncontradoException(id));
    }


}
