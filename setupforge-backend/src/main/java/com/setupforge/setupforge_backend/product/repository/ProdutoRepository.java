package com.setupforge.setupforge_backend.product.repository;

import com.setupforge.setupforge_backend.product.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
