package com.setupforge.setupforge_backend.setup.mapper;

import com.setupforge.setupforge_backend.produto.model.Produto;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.request.SetupProdutoRequest;
import com.setupforge.setupforge_backend.setup.dto.setupProdutoDTO.response.SetupProdutoResponse;
import com.setupforge.setupforge_backend.setup.model.Setup;
import com.setupforge.setupforge_backend.setup.model.SetupProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public abstract class SetupProdutoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "setup", source = "setup")
    @Mapping(target = "produto", source = "produto")
    @Mapping(target = "precoUnitario", source = "produto.preco")
   public abstract SetupProduto toSetupProduto(
            SetupProdutoRequest request,
            Setup setup,
            Produto produto
    );

    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "produtoNome", source = "produto.nome")
    @Mapping(target = "marca", source = "produto.marca")
    @Mapping(
            target = "subtotal",
            expression = "java(setupProduto.getPrecoUnitario().multiply(BigDecimal.valueOf(setupProduto.getQuantidade())))"
    )
    public abstract SetupProdutoResponse toSetupProdutoResponse(SetupProduto setupProduto);
}