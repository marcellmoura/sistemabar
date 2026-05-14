package com.bar.sistemabar.internal.contagemEstoqueDia.controller;

import com.bar.sistemabar.internal.contagemEstoqueDia.dto.ContagemEstoqueDiaRequestRecord;
import com.bar.sistemabar.internal.contagemEstoqueDia.dto.ContagemEstoqueDiaResponseRecord;
import com.bar.sistemabar.internal.contagemEstoqueDia.dto.ContagemFinalRequestRecord;
import com.bar.sistemabar.internal.contagemEstoqueDia.service.ContagemEstoqueDiaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contagens-estoque")
public class ContagemEstoqueDiaController {

    private final ContagemEstoqueDiaService contagemEstoqueDiaService;

    public ContagemEstoqueDiaController(
            ContagemEstoqueDiaService contagemEstoqueDiaService
    ) {

        this.contagemEstoqueDiaService = contagemEstoqueDiaService;
    }

    @PostMapping
    public ResponseEntity<ContagemEstoqueDiaResponseRecord> registrarContagemInicial(
            @RequestBody @Valid ContagemEstoqueDiaRequestRecord request
    ) {

        ContagemEstoqueDiaResponseRecord response =
                contagemEstoqueDiaService.registrarContagemInicial(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}/final")
    public ResponseEntity<ContagemEstoqueDiaResponseRecord> registrarContagemFinal(
            @PathVariable Long id,
            @RequestBody @Valid ContagemFinalRequestRecord request
    ) {

        ContagemEstoqueDiaResponseRecord response =
                contagemEstoqueDiaService.registrarContagemFinal(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/movimento/{movimentoDiaId}")
    public ResponseEntity<List<ContagemEstoqueDiaResponseRecord>> listarPorMovimento(
            @PathVariable Long movimentoDiaId
    ) {

        return ResponseEntity.ok(
                contagemEstoqueDiaService.listarPorMovimento(movimentoDiaId)
        );
    }
}
