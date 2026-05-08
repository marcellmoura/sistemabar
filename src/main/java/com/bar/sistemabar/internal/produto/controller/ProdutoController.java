package com.bar.sistemabar.internal.produto.controller;

import com.bar.sistemabar.internal.produto.dto.ProdutoRequestRecord;
import com.bar.sistemabar.internal.produto.dto.ProdutoResponseRecord;
import com.bar.sistemabar.internal.produto.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseRecord> cadastrar(
            @Valid @RequestBody ProdutoRequestRecord request) {

        ProdutoResponseRecord response = produtoService.cadastrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseRecord>> listar() {

        return ResponseEntity.ok(produtoService.listar());
    }
}