package com.bar.sistemabar.internal.fiado.controller;

import com.bar.sistemabar.internal.fiado.dto.RegistroFiadoRequestRecord;
import com.bar.sistemabar.internal.fiado.dto.RegistroFiadoResponseRecord;
import com.bar.sistemabar.internal.fiado.service.RegistroFiadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fiados")
public class RegistroFiadoController {

    private final RegistroFiadoService registroFiadoService;

    public RegistroFiadoController(RegistroFiadoService registroFiadoService) {
        this.registroFiadoService = registroFiadoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroFiadoResponseRecord registrar(
            @RequestBody @Valid RegistroFiadoRequestRecord request
    ) {

        return registroFiadoService.registrar(request);
    }

    @GetMapping
    public List<RegistroFiadoResponseRecord> listar() {

        return registroFiadoService.listar();
    }

    @GetMapping("/pessoa/{pessoaFiadoId}")
    public List<RegistroFiadoResponseRecord> listarPorPessoa(
            @PathVariable Long pessoaFiadoId
    ) {

        return registroFiadoService.listarPorPessoa(pessoaFiadoId);
    }

    @GetMapping("/abertos")
    public List<RegistroFiadoResponseRecord> listarEmAberto() {

        return registroFiadoService.listarEmAberto();
    }

    @PatchMapping("/{id}/pagar")
    public RegistroFiadoResponseRecord marcarComoPago(@PathVariable Long id) {

        return registroFiadoService.marcarComoPago(id);
    }
}
