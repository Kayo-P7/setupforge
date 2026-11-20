package com.setupforge.setupforge_backend.setup.repository;

import com.setupforge.setupforge_backend.setup.model.SetupProduto;
import com.setupforge.setupforge_backend.setup.projection.ProdutoValorTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SetupProdutoRepository extends JpaRepository<SetupProduto, Long> {


    @Query("""
            SELECT COALESCE( SUM(sp.precoUnitario * sp.quantidade), 0 ) AS valorTotal
             FROM SetupProduto sp
              WHERE sp.setup.id = :setupId 
            
            """)
    ProdutoValorTotal produtoValorTotal(@Param("setupId") Long setupId);

   List<SetupProduto> findBySetupId(Long setupId);

    Optional<SetupProduto> findByIdAndSetupId(Long itemId, Long setupId);
}
