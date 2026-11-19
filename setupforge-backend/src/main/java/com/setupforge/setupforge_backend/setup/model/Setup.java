package com.setupforge.setupforge_backend.setup.model;

import com.setupforge.setupforge_backend.setup.enums.TipoMontagem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "setup_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Setup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150)
    private String nome;
    @Column(length = 1000)
    private String descricao;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal orcamento;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoMontagem", nullable = false)
    private TipoMontagem tipoMontagem;

    @OneToMany(mappedBy = "setup", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SetupProduto> produtos = new ArrayList<>();



}
