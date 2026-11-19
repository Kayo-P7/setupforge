package com.setupforge.setupforge_backend.setup.repository;

import com.setupforge.setupforge_backend.setup.model.SetupProduto;
import com.setupforge.setupforge_backend.setup.projection.ProdutoValorTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetupProdutoRepository extends JpaRepository<SetupProduto, Long> {


    @Query("""
            SELECT COALESCE( SUM(sp.precoUnitario * sp.quantidade), 0 )
             FROM SetupProduto sp
              WHERE sp.setup.id = : setupId 
            
            """)
    ProdutoValorTotal produtoValorTotal(@Param("id") Long setupId);
}
