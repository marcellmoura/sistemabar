package com.bar.sistemabar.internal.saidaProduto.controller;

import com.bar.sistemabar.internal.saidaProduto.dto.SaidaProdutoRequestRecord;
import com.bar.sistemabar.internal.saidaProduto.dto.SaidaProdutoResponseRecord;
import com.bar.sistemabar.internal.saidaProduto.service.SaidaProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saidas-produto")
public class SaidaProdutoController {

    private final SaidaProdutoService saidaProdutoService;

    public SaidaProdutoController(SaidaProdutoService saidaProdutoService) {
        this.saidaProdutoService = saidaProdutoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaidaProdutoResponseRecord registrar(
            @RequestBody @Valid SaidaProdutoRequestRecord request
    ) {

        return saidaProdutoService.registrar(request);
    }

    @GetMapping
    public List<SaidaProdutoResponseRecord> listar() {

        return saidaProdutoService.listar();
    }
}