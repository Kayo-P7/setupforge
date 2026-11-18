package com.setupforge.setupforge_backend.category.repository;

import com.setupforge.setupforge_backend.category.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
