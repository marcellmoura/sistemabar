package com.bar.sistemabar.internal.fiado.controller;

import com.bar.sistemabar.internal.fiado.dto.PessoaFiadoRequestRecord;
import com.bar.sistemabar.internal.fiado.dto.PessoaFiadoResponseRecord;
import com.bar.sistemabar.internal.fiado.service.PessoaFiadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas-fiado")
public class PessoaFiadoController {

    private final PessoaFiadoService pessoaFiadoService;

    public PessoaFiadoController(PessoaFiadoService pessoaFiadoService) {
        this.pessoaFiadoService = pessoaFiadoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaFiadoResponseRecord cadastrar(
            @RequestBody @Valid PessoaFiadoRequestRecord request
    ) {

        return pessoaFiadoService.cadastrar(request);
    }

    @GetMapping
    public List<PessoaFiadoResponseRecord> listar() {

        return pessoaFiadoService.listar();
    }

    @GetMapping("/{id}")
    public PessoaFiadoResponseRecord buscarPorId(@PathVariable Long id) {

        return pessoaFiadoService.buscarPorId(id);
    }

    @PatchMapping("/{id}/ativar")
    public PessoaFiadoResponseRecord ativar(@PathVariable Long id) {

        return pessoaFiadoService.ativar(id);
    }

    @PatchMapping("/{id}/inativar")
    public PessoaFiadoResponseRecord inativar(@PathVariable Long id) {

        return pessoaFiadoService.inativar(id);
    }
}
