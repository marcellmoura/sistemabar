package com.bar.sistemabar.internal.movimentoDia.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.movimentoDia.dto.MovimentoDiaRequestRecord;
import com.bar.sistemabar.internal.movimentoDia.dto.MovimentoDiaResponseRecord;
import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.movimentoDia.entity.StatusMovimentoDia;
import com.bar.sistemabar.internal.movimentoDia.mapper.MovimentoDiaMapperRecord;
import com.bar.sistemabar.internal.movimentoDia.repository.MovimentoDiaRepository;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import com.bar.sistemabar.internal.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class MovimentoDiaService {

    private final MovimentoDiaRepository movimentoDiaRepository;
    private final UsuarioRepository usuarioRepository;

    public MovimentoDiaService(MovimentoDiaRepository movimentoDiaRepository,
                               UsuarioRepository usuarioRepository) {

        this.movimentoDiaRepository = movimentoDiaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public MovimentoDiaResponseRecord abrirMovimento(MovimentoDiaRequestRecord request) {

        if (movimentoDiaRepository
                .findByDataMovimentoAndStatus(
                        LocalDate.now(),
                        StatusMovimentoDia.ABERTO
                )
                .isPresent()) {

            throw new BusinessException(
                    "Já existe um movimento aberto para hoje"
            );
        }

        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioResponsavelId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Usuário não encontrado"));

        MovimentoDiaEntity movimentoDia = new MovimentoDiaEntity();

        movimentoDia.setDataMovimento(LocalDate.now());
        movimentoDia.setDataHoraAbertura(LocalDateTime.now());
        movimentoDia.setTrocoInicial(request.trocoInicial());
        movimentoDia.setStatus(StatusMovimentoDia.ABERTO);
        movimentoDia.setUsuarioResponsavel(usuario);

        MovimentoDiaEntity movimentoSalvo =
                movimentoDiaRepository.save(movimentoDia);

        return MovimentoDiaMapperRecord
                .entityToResponseRecord(movimentoSalvo);
    }

    @Transactional
    public MovimentoDiaResponseRecord fecharMovimento(Long id) {

        MovimentoDiaEntity movimentoDia = movimentoDiaRepository
                .findByIdAndStatus(id, StatusMovimentoDia.ABERTO)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Movimento aberto não encontrado"
                        ));

        movimentoDia.setStatus(StatusMovimentoDia.FECHADO);

        movimentoDia.setDataHoraFechamento(LocalDateTime.now());

        MovimentoDiaEntity movimentoFechado =
                movimentoDiaRepository.save(movimentoDia);

        return MovimentoDiaMapperRecord
                .entityToResponseRecord(movimentoFechado);
    }
}