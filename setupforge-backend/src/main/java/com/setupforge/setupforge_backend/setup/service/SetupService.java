package com.setupforge.setupforge_backend.setup.service;


import com.setupforge.setupforge_backend.setup.dto.setupDTO.request.SetupRequest;
import com.setupforge.setupforge_backend.setup.dto.setupDTO.response.SetupResponse;
import com.setupforge.setupforge_backend.setup.mapper.SetupMapper;
import com.setupforge.setupforge_backend.setup.model.Setup;
import com.setupforge.setupforge_backend.setup.repository.SetupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetupService {

    private final SetupRepository setupRepository;
    private final SetupMapper setupMapper;

    public SetupResponse criarSetup(SetupRequest request) {
        Setup setup = setupMapper.toSetup(request);

        setupRepository.save(setup);

        return setupMapper.toSetupResponse(setup);
    }

    @Transactional(readOnly = true)
    public List<SetupResponse> listarSetups() {
        return setupRepository.findAll()
                .stream()
                .map(setupMapper::toSetupResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SetupResponse buscarPorId(Long id) {
        Setup setup = setupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Setup não encontrado: " + id
                ));

        return setupMapper.toSetupResponse(setup);
    }

    @Transactional
    public void removerSetup(Long id) {
        if (!setupRepository.existsById(id)) {
            throw new EntityNotFoundException("Setup não encontrado: " + id);
        }

        setupRepository.deleteById(id);
    }

}
