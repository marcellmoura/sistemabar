package com.bar.sistemabar.internal.categoria.controller;

import com.bar.sistemabar.internal.categoria.dto.CategoriaRequestRecord;
import com.bar.sistemabar.internal.categoria.dto.CategoriaResponseRecord;
import com.bar.sistemabar.internal.categoria.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseRecord> registrar(@Valid @RequestBody CategoriaRequestRecord request) {

        CategoriaResponseRecord categoria = categoriaService.registrar(request);

        return ResponseEntity.ok(categoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseRecord>> listar() {

        List<CategoriaResponseRecord> categorias = categoriaService.listar();

        return ResponseEntity.ok(categorias);
    }
}