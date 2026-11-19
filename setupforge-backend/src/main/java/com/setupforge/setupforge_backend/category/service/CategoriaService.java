package com.setupforge.setupforge_backend.category.service;

import com.setupforge.setupforge_backend.category.dto.request.CategoriaRequest;
import com.setupforge.setupforge_backend.category.dto.response.CategoriaResponse;
import com.setupforge.setupforge_backend.category.mapper.CategoriaMappper;
import com.setupforge.setupforge_backend.category.model.Categoria;
import com.setupforge.setupforge_backend.category.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMappper categoriaMappper;

    public CategoriaResponse save(CategoriaRequest categoriaRequest){

        Categoria categoria = categoriaMappper.toCategoria(categoriaRequest);

        categoriaRepository.save(categoria);

        return categoriaMappper.toCategoriaResponse(categoria);
    }

}
