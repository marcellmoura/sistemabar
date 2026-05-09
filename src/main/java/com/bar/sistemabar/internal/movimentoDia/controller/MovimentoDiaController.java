package com.bar.sistemabar.internal.movimentoDia.controller;

import com.bar.sistemabar.internal.movimentoDia.dto.MovimentoDiaRequestRecord;
import com.bar.sistemabar.internal.movimentoDia.dto.MovimentoDiaResponseRecord;
import com.bar.sistemabar.internal.movimentoDia.service.MovimentoDiaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimentos-dia")
public class MovimentoDiaController {

    private final MovimentoDiaService movimentoDiaService;

    public MovimentoDiaController(MovimentoDiaService movimentoDiaService) {
        this.movimentoDiaService = movimentoDiaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimentoDiaResponseRecord abrirMovimento(@RequestBody @Valid MovimentoDiaRequestRecord request) {
        return movimentoDiaService.abrirMovimento(request);
    }

    @PatchMapping("/{id}/fechar")
    public MovimentoDiaResponseRecord fecharMovimento(@PathVariable Long id) {
        return movimentoDiaService.fecharMovimento(id);
    }
}