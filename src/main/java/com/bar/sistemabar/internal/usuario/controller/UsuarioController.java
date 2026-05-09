package com.bar.sistemabar.internal.usuario.controller;

import com.bar.sistemabar.internal.usuario.dto.UsuarioRequestRecord;
import com.bar.sistemabar.internal.usuario.dto.UsuarioResponseRecord;
import com.bar.sistemabar.internal.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseRecord> cadastrar(@RequestBody @Valid UsuarioRequestRecord request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseRecord> editar(@PathVariable Long id,
                                                        @RequestBody @Valid UsuarioRequestRecord request) {
        return ResponseEntity.ok(usuarioService.editar(id, request));
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<UsuarioResponseRecord> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.ativar(id));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<UsuarioResponseRecord> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.inativar(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseRecord> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseRecord>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
}