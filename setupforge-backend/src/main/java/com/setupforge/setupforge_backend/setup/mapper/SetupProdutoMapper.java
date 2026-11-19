package com.setupforge.setupforge_backend.setup.mapper;


import com.setupforge.setupforge_backend.produto.model.Produto;
import com.setupforge.setupforge_backend.produto.repository.ProdutoRepository;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request.SetupProdutoRequest;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.response.SetupProdutoResponse;
import com.setupforge.setupforge_backend.setup.model.Setup;
import com.setupforge.setupforge_backend.setup.model.SetupProduto;
import com.setupforge.setupforge_backend.setup.repository.SetupRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")

public abstract class SetupProdutoMapper {

    @Autowired
    protected SetupRepository setupRepository;
    @Autowired
    protected ProdutoRepository produtoRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "setup", expression = "java(buscarSetup(request.setupId()))")
    @Mapping(target = "produto", source = "produtoId")
    @Mapping(target = "precoUnitario", ignore = true)
    public abstract SetupProduto toSetupProduto(SetupProdutoRequest request);

    protected Produto mapProduto(Long produtoId) {

        return produtoRepository.findById(produtoId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    protected Setup buscarSetup(Long setupId) {
        return setupRepository.findById(setupId).orElseThrow(() -> new RuntimeException("Setup não encontrado"));
    }

    @AfterMapping
    protected void preencherPreco(@MappingTarget SetupProduto setupProduto) {
        setupProduto.setPrecoUnitario(setupProduto.getProduto().getPreco());
    }

    public abstract SetupProdutoResponse tosetupProdutoResponse(SetupProduto setupProduto);


}
