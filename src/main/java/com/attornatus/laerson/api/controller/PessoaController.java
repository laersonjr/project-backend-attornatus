package com.attornatus.laerson.api.controller;

import com.attornatus.laerson.api.model.input.EnderecoResumo;
import com.attornatus.laerson.api.model.input.PessoaResumo;
import com.attornatus.laerson.api.model.output.EnderecoSaidaResumo;
import com.attornatus.laerson.domain.event.RecursoCriadoEvent;
import com.attornatus.laerson.domain.model.Endereco;
import com.attornatus.laerson.domain.model.Pessoa;
import com.attornatus.laerson.domain.repository.PessoaRepository;
import com.attornatus.laerson.domain.service.PessoaEnderecoService;
import com.attornatus.laerson.domain.service.PessoaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaEnderecoService pessoaEnderecoService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping
    public ResponseEntity<Pessoa> cadastrarPessoa(@Valid @RequestBody PessoaResumo pessoaResumo, HttpServletResponse response){
        Pessoa pessoaSalva = pessoaRepository.save(pessoaService.salvarPessoa(pessoaResumo));
        eventPublisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @PostMapping("/{idPessoa}/endereco")
    public ResponseEntity<EnderecoSaidaResumo> cadastrarEnderecoPessoa(@PathVariable Long idPessoa, @Valid @RequestBody EnderecoResumo enderecoResumo, HttpServletResponse response){
        EnderecoSaidaResumo enderecoSaidaResumo = pessoaEnderecoService.cadastrarEnderecoParaPessoa(idPessoa, enderecoResumo);
        eventPublisher.publishEvent(new RecursoCriadoEvent(this, response, enderecoSaidaResumo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoSaidaResumo);
    }

    @GetMapping
    public Page<Pessoa> listarPessoas(@RequestParam(required = false, defaultValue = "") String nome, Pageable pageable){
        return pessoaRepository.findByNomeContaining(nome, pageable);
    }

    @GetMapping("/{idPessoa}")
    public ResponseEntity<Pessoa> buscarPessoaId(@PathVariable Long idPessoa){
        return ResponseEntity.ok(pessoaService.buscarPessoaOuFalhar(idPessoa));
    }

    @GetMapping("/{idPessoa}/endereco")
    public List<EnderecoSaidaResumo> listarEnderecoDePessoa(@PathVariable Long idPessoa){
       return pessoaEnderecoService.listarEnderecos(idPessoa);
    }

    @PutMapping("/{idPessoa}")
    public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long idPessoa, @Valid @RequestBody PessoaResumo pessoa){
        Pessoa pessoaSalva = pessoaService.atualizarPessoa(idPessoa, pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }

    @PutMapping("/{idPessoa}/endereco/{idEndereco}")
    public ResponseEntity<EnderecoSaidaResumo> atualizarEnderecoPrincipal(@PathVariable Long idPessoa, @PathVariable Long idEndereco){
        EnderecoSaidaResumo enderecoSaidaResumo = pessoaEnderecoService.atualizarEnderecoPrincipalPessoa(idPessoa, idEndereco);
        return ResponseEntity.ok(enderecoSaidaResumo);
    }

    @DeleteMapping("/{idPessoa}")
    public ResponseEntity<Pessoa> excluirPessoa(@PathVariable Long idPessoa){
        pessoaService.excluirPessoa(idPessoa);
        return ResponseEntity.noContent().build();
    }

}
