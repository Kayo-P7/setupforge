package com.setupforge.setupforge_backend.setup.service;

import com.setupforge.setupforge_backend.produto.model.Produto;
import com.setupforge.setupforge_backend.produto.repository.ProdutoRepository;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request.SetupProdutoRequest;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.response.SetupProdutoResponse;
import com.setupforge.setupforge_backend.setup.mapper.SetupProdutoMapper;
import com.setupforge.setupforge_backend.setup.model.Setup;
import com.setupforge.setupforge_backend.setup.model.SetupProduto;
import com.setupforge.setupforge_backend.setup.repository.SetupProdutoRepository;
import com.setupforge.setupforge_backend.setup.repository.SetupRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SetupProdutoService {

    private final ProdutoRepository produtoRepository;
    private final SetupRepository setupRepository;
    private final SetupProdutoRepository setupProdutoRepository;
    private final SetupProdutoMapper setupProdutoMapper;

    @Transactional
    public SetupProdutoResponse adicionarProduto(SetupProdutoRequest request) {

        Setup setup = setupRepository.findById(request.setupId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Setup não encontrado: " + request.setupId()
                ));

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Produto não encontrado: " + request.produtoId()
                ));

        SetupProduto setupProduto = setupProdutoMapper.toSetupProduto(
                request,
                setup,
                produto
        );

        setup.adicionarProduto(setupProduto);

        setupProdutoRepository.save(setupProduto);

        return setupProdutoMapper.toSetupProdutoResponse(setupProduto);
    }

    @Transactional
    public SetupProdutoResponse atualizarQuantidade(
            Long setupId,
            Long itemId,
            Integer quantidade
    ) {

        SetupProduto item = buscarItem(setupId, itemId);

        item.setQuantidade(quantidade);

        return setupProdutoMapper.toSetupProdutoResponse(item);
    }

    @Transactional
    public void removerProduto(Long setupId, Long itemId) {

        SetupProduto item = buscarItem(setupId, itemId);

        item.getSetup().removerProduto(item);
    }

    @Transactional(readOnly = true)
    public BigDecimal valorTotal(Long setupId) {

        if (!setupRepository.existsById(setupId)) {
            throw new EntityNotFoundException("Setup não encontrado: " + setupId);
        }

        return setupProdutoRepository.produtoValorTotal(setupId)
                .getValorTotal();
    }

    private SetupProduto buscarItem(Long setupId, Long itemId) {

        return setupProdutoRepository.findByIdAndSetupId(itemId, setupId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Produto não encontrado neste setup."
                ));
    }

    @Transactional(readOnly = true)
    public List<SetupProdutoResponse> listarProdutos(Long setupId) {

        if (!setupRepository.existsById(setupId)) {
            throw new EntityNotFoundException(
                    "Setup não encontrado: " + setupId
            );
        }

        return setupProdutoRepository.findBySetupId(setupId)
                .stream()
                .map(setupProdutoMapper::toSetupProdutoResponse)
                .toList();
    }
}
