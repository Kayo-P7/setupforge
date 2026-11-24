import type { Produto } from '@/features/types/produto';
import { CATEGORIAS_MONTAGEM, type CategoriaMontagem, type SelecaoMontagem } from '../types/montagem';

export function categoriaApiDaChave(chave: string) {
    return CATEGORIAS_MONTAGEM.find((categoria) => categoria.chave === chave);
}

export function produtosDaCategoria(produtos: Produto[], chave: CategoriaMontagem) {
    const categoria = categoriaApiDaChave(chave);
    if (!categoria) return [];

    return produtos.filter((produto) => produto.categoriaNome?.trim().toLocaleUpperCase('pt-BR') === categoria.categoriaApi);
}

export function categoriasPendentes(selecao: SelecaoMontagem) {
    return CATEGORIAS_MONTAGEM.filter(({ chave }) => !selecao[chave]);
}

export function totalMontagem(selecao: SelecaoMontagem) {
    return Object.values(selecao).reduce((total, produto) => total + (produto?.preco ?? 0), 0);
}

export function identidadeLocalProduto(produto: Produto) {
    return JSON.stringify([
        produto.nome,
        produto.marca,
        produto.preco,
        produto.imagemUrl,
        produto.categoriaNome,
        produto.descricao,
    ]);
}
