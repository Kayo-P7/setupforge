package com.setupforge.setupforge_backend.setup.service;


import com.setupforge.setupforge_backend.setup.dto.setupDTO.request.SetupRequest;
import com.setupforge.setupforge_backend.setup.dto.setupDTO.response.SetupResponse;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request.SetupProdutoRequest;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.response.SetupProdutoResponse;
import com.setupforge.setupforge_backend.setup.mapper.SetupMapper;
import com.setupforge.setupforge_backend.setup.mapper.SetupProdutoMapper;
import com.setupforge.setupforge_backend.setup.model.Setup;
import com.setupforge.setupforge_backend.setup.model.SetupProduto;
import com.setupforge.setupforge_backend.setup.repository.SetupProdutoRepository;
import com.setupforge.setupforge_backend.setup.repository.SetupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SetupService {

    private final SetupProdutoRepository setupProdutoRepository;
    private final SetupRepository setupRepository;
    private final SetupMapper setupMapper;
    private final SetupProdutoMapper setupProdutoMapper;

    public SetupResponse saveSetup(SetupRequest setupRequest){

        Setup setup = setupMapper.toSetup(setupRequest);

        setupRepository.save(setup);
        return setupMapper.toSetupResponse(setup);
    }



    @Transactional
    public SetupProdutoResponse saveSetupProduto(SetupProdutoRequest setupProdutoRequest){

        SetupProduto setupProduto = setupProdutoMapper.toSetupProduto(setupProdutoRequest);

        setupProdutoRepository.save(setupProduto);

        return setupProdutoMapper.tosetupProdutoResponse(setupProduto);
    }


    @Transactional(readOnly = true)
    public BigDecimal valorTotal(Long id) {

        return setupProdutoRepository
                .produtoValorTotal(id)
                .getValueTotal();
    }

}
