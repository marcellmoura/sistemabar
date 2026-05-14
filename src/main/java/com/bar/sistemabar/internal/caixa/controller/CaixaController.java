package com.bar.sistemabar.internal.caixa.controller;

import com.bar.sistemabar.internal.caixa.dto.CaixaRequestRecord;
import com.bar.sistemabar.internal.caixa.dto.CaixaResponseRecord;
import com.bar.sistemabar.internal.caixa.service.CaixaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caixas")
public class CaixaController {

    private final CaixaService caixaService;

    public CaixaController(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    @PostMapping
    public ResponseEntity<CaixaResponseRecord> registrarConferencia(
            @RequestBody @Valid CaixaRequestRecord request
    ) {

        CaixaResponseRecord response =
                caixaService.registrarConferencia(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CaixaResponseRecord>> listar() {

        return ResponseEntity.ok(caixaService.listar());
    }

    @GetMapping("/movimento/{movimentoDiaId}")
    public ResponseEntity<CaixaResponseRecord> buscarPorMovimento(
            @PathVariable Long movimentoDiaId
    ) {

        return ResponseEntity.ok(
                caixaService.buscarPorMovimento(movimentoDiaId)
        );
    }
}
