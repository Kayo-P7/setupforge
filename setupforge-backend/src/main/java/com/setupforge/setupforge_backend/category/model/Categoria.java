package com.setupforge.setupforge_backend.category.model;

import com.setupforge.setupforge_backend.product.model.Produto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "categoria_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String nome;
    @Column(length = 255)
    private String descricao;

    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos;
}
