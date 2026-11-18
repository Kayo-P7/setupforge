package com.setupforge.setupforge_backend.product.service;


import com.setupforge.setupforge_backend.category.model.Categoria;
import com.setupforge.setupforge_backend.category.repository.CategoriaRepository;
import com.setupforge.setupforge_backend.product.dto.request.ProdutoRequest;
import com.setupforge.setupforge_backend.product.dto.response.ProdutoResponse;
import com.setupforge.setupforge_backend.product.mapper.ProdutoMapper;
import com.setupforge.setupforge_backend.product.model.Produto;
import com.setupforge.setupforge_backend.product.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;
    private final CategoriaRepository categoriaRepository;

    public ProdutoResponse save(ProdutoRequest produtoRequest) {

        Categoria categoria = categoriaRepository
                .findById(produtoRequest.categoriaId())
                .orElseThrow(() -> new RuntimeException("Nao foi possivele econtrar uma categoria com esse id"));
        Produto produto = produtoMapper.toProduto(produtoRequest);

        produto.setCategoria(categoria);

        produtoRepository.save(produto);
        return produtoMapper.toProdutoResponse(produto);
    }
}
