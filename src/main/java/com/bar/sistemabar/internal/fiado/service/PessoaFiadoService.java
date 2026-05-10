package com.bar.sistemabar.internal.fiado.service;

import com.bar.sistemabar.config.exception.BusinessException;
import com.bar.sistemabar.config.exception.RecursoNaoEncontradoException;
import com.bar.sistemabar.internal.fiado.dto.PessoaFiadoRequestRecord;
import com.bar.sistemabar.internal.fiado.dto.PessoaFiadoResponseRecord;
import com.bar.sistemabar.internal.fiado.entity.PessoaFiadoEntity;
import com.bar.sistemabar.internal.fiado.entity.StatusPessoaFiado;
import com.bar.sistemabar.internal.fiado.mapper.PessoaFiadoMapperRecord;
import com.bar.sistemabar.internal.fiado.repository.PessoaFiadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaFiadoService {

    private final PessoaFiadoRepository pessoaFiadoRepository;

    public PessoaFiadoService(PessoaFiadoRepository pessoaFiadoRepository) {
        this.pessoaFiadoRepository = pessoaFiadoRepository;
    }

    public PessoaFiadoResponseRecord cadastrar(PessoaFiadoRequestRecord request) {

        if (pessoaFiadoRepository.existsByNome(request.nome())) {
            throw new BusinessException(
                    "Já existe uma pessoa cadastrada para fiado com esse nome."
            );
        }

        PessoaFiadoEntity pessoaFiado =
                PessoaFiadoMapperRecord.requestToEntity(request);

        PessoaFiadoEntity pessoaSalva =
                pessoaFiadoRepository.save(pessoaFiado);

        return PessoaFiadoMapperRecord.entityToResponse(pessoaSalva);
    }

    public List<PessoaFiadoResponseRecord> listar() {

        List<PessoaFiadoEntity> pessoas =
                pessoaFiadoRepository.findAll();

        return PessoaFiadoMapperRecord
                .entityListToResponseList(pessoas);
    }

    public PessoaFiadoResponseRecord buscarPorId(Long id) {

        PessoaFiadoEntity pessoa =
                buscarEntityPorId(id);

        return PessoaFiadoMapperRecord.entityToResponse(pessoa);
    }

    public PessoaFiadoResponseRecord ativar(Long id) {

        PessoaFiadoEntity pessoa =
                buscarEntityPorId(id);

        pessoa.setStatus(StatusPessoaFiado.ATIVO);

        PessoaFiadoEntity pessoaAtualizada =
                pessoaFiadoRepository.save(pessoa);

        return PessoaFiadoMapperRecord.entityToResponse(pessoaAtualizada);
    }

    public PessoaFiadoResponseRecord inativar(Long id) {

        PessoaFiadoEntity pessoa =
                buscarEntityPorId(id);

        pessoa.setStatus(StatusPessoaFiado.INATIVO);

        PessoaFiadoEntity pessoaAtualizada =
                pessoaFiadoRepository.save(pessoa);

        return PessoaFiadoMapperRecord.entityToResponse(pessoaAtualizada);
    }

    private PessoaFiadoEntity buscarEntityPorId(Long id) {

        return pessoaFiadoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Pessoa do fiado não encontrada."
                        )
                );
    }
}
