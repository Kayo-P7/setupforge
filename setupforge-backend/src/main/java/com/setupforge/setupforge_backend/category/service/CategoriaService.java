package com.setupforge.setupforge_backend.category.service;

import com.setupforge.setupforge_backend.category.dto.request.CategoriaRequest;
import com.setupforge.setupforge_backend.category.dto.response.CategoriaResponse;
import com.setupforge.setupforge_backend.category.mapper.CategoriaMappper;
import com.setupforge.setupforge_backend.category.model.Categoria;
import com.setupforge.setupforge_backend.category.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMappper categoriaMappper;

    public CategoriaResponse save(CategoriaRequest categoriaRequest) {
        Categoria categoria = categoriaMappper.toCategoria(categoriaRequest);

        categoriaRepository.save(categoria);

        return categoriaMappper.toCategoriaResponse(categoria);
    }

    public List<CategoriaResponse> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMappper::toCategoriaResponse)
                .toList();
    }

    public CategoriaResponse findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Categoria não encontrada: " + id
                ));

        return categoriaMappper.toCategoriaResponse(categoria);
    }

    public CategoriaResponse update(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Categoria não encontrada: " + id
                ));

        categoria.setNome(request.nome());
        categoria.setDescricao(request.descricao());

        return categoriaMappper.toCategoriaResponse(categoria);
    }

    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Categoria não encontrada: " + id
            );
        }

        categoriaRepository.deleteById(id);
    }
}