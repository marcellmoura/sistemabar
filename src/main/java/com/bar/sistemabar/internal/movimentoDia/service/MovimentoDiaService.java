package com.bar.sistemabar.internal.movimentoDia.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.contagemEstoqueDia.entity.ContagemEstoqueDiaEntity;
import com.bar.sistemabar.internal.contagemEstoqueDia.repository.ContagemEstoqueDiaRepository;
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
import java.util.List;

@Service
public class MovimentoDiaService {

    private final MovimentoDiaRepository movimentoDiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContagemEstoqueDiaRepository contagemEstoqueDiaRepository;

    public MovimentoDiaService(
            MovimentoDiaRepository movimentoDiaRepository,
            UsuarioRepository usuarioRepository,
            ContagemEstoqueDiaRepository contagemEstoqueDiaRepository
    ) {

        this.movimentoDiaRepository = movimentoDiaRepository;
        this.usuarioRepository = usuarioRepository;
        this.contagemEstoqueDiaRepository = contagemEstoqueDiaRepository;
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

        List<ContagemEstoqueDiaEntity> contagens =
                contagemEstoqueDiaRepository.findByMovimentoDiaId(id);

        if (contagens.isEmpty()) {
            throw new BusinessException(
                    "Não é possível fechar o movimento sem contagens de estoque registradas"
            );
        }

        boolean existeContagemSemFinal = contagens.stream()
                .anyMatch(contagem -> contagem.getQuantidadeFinal() == null);

        if (existeContagemSemFinal) {
            throw new BusinessException(
                    "Não é possível fechar o movimento. Existem produtos sem contagem final"
            );
        }

        movimentoDia.setStatus(StatusMovimentoDia.FECHADO);
        movimentoDia.setDataHoraFechamento(LocalDateTime.now());

        MovimentoDiaEntity movimentoFechado =
                movimentoDiaRepository.save(movimentoDia);

        return MovimentoDiaMapperRecord
                .entityToResponseRecord(movimentoFechado);
    }
}