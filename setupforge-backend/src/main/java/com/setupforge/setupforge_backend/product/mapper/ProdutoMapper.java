package com.setupforge.setupforge_backend.product.mapper;

import com.setupforge.setupforge_backend.product.dto.request.ProdutoRequest;
import com.setupforge.setupforge_backend.product.dto.response.ProdutoResponse;
import com.setupforge.setupforge_backend.product.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProdutoMapper {


    @Mapping(target = "categoriaNome", source = "categoria.nome")
    @Mapping(target = "categoriaDescricao", source = "categoria.descricao")
    public abstract ProdutoResponse toProdutoResponse(Produto produto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    public abstract Produto toProduto(ProdutoRequest produtoRequest);
}
