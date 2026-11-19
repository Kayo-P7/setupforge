package com.setupforge.setupforge_backend.category.mapper;

import com.setupforge.setupforge_backend.category.dto.request.CategoriaRequest;
import com.setupforge.setupforge_backend.category.dto.response.CategoriaResponse;
import com.setupforge.setupforge_backend.category.model.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CategoriaMappper {


    @Mapping(target = "produtos", ignore = true)
    public abstract CategoriaResponse toCategoriaResponse(Categoria categoria);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    public abstract Categoria toCategoria(CategoriaRequest categoriaRequest);

}
