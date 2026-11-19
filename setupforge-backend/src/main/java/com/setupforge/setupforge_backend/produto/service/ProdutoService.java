package com.setupforge.setupforge_backend.produto.service;


import com.setupforge.setupforge_backend.category.model.Categoria;
import com.setupforge.setupforge_backend.category.repository.CategoriaRepository;
import com.setupforge.setupforge_backend.produto.dto.request.ProdutoRequest;
import com.setupforge.setupforge_backend.produto.dto.response.ProdutoResponse;
import com.setupforge.setupforge_backend.produto.mapper.ProdutoMapper;
import com.setupforge.setupforge_backend.produto.model.Produto;
import com.setupforge.setupforge_backend.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;
    private final CategoriaRepository categoriaRepository;
    @Transactional()
    public ProdutoResponse salvar(ProdutoRequest produtoRequest) {

        Categoria categoria = categoriaRepository
                .findById(produtoRequest.categoriaId())
                .orElseThrow(() -> new RuntimeException("Nao foi possivele econtrar uma categoria com esse id"));
        Produto produto = produtoMapper.toProduto(produtoRequest);

        produto.setCategoria(categoria);

        produtoRepository.save(produto);
        return produtoMapper.toProdutoResponse(produto);
    }
    @Transactional(readOnly = true)
    public List<ProdutoResponse> buscarTudo(){

        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(produtoMapper::toProdutoResponse).toList();
    }
    @Transactional(readOnly = true)
    public List<ProdutoResponse> buscarPeloNome(String nome){

        List<Produto> produtos = produtoRepository
                .buscarPeloNome(nome);


        return produtos.stream().map(produtoMapper::toProdutoResponse).toList();
    }


}
