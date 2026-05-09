package com.bar.sistemabar.internal.entradaProduto.controller;

import com.bar.sistemabar.internal.entradaProduto.dto.EntradaProdutoRequestRecord;
import com.bar.sistemabar.internal.entradaProduto.dto.EntradaProdutoResponseRecord;
import com.bar.sistemabar.internal.entradaProduto.service.EntradaProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entradas-produto")
public class EntradaProdutoController {

    private final EntradaProdutoService entradaProdutoService;

    public EntradaProdutoController(EntradaProdutoService entradaProdutoService) {
        this.entradaProdutoService = entradaProdutoService;
    }

    @PostMapping
    public ResponseEntity<EntradaProdutoResponseRecord> cadastrar(
            @RequestBody @Valid EntradaProdutoRequestRecord request
    ) {

        EntradaProdutoResponseRecord response =
                entradaProdutoService.cadastrar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<EntradaProdutoResponseRecord>> listar() {

        return ResponseEntity.ok(
                entradaProdutoService.listar()
        );
    }
}