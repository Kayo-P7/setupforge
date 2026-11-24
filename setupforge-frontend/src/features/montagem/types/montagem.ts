export const CATEGORIAS_MONTAGEM = [
    { chave: 'processador', nome: 'Processador', categoriaApi: 'PROCESSADORES' },
    { chave: 'placaMae', nome: 'Placa-mãe', categoriaApi: 'PLACAS MAE' },
    { chave: 'placaVideo', nome: 'Placa de vídeo', categoriaApi: 'PLACAS DE VIDEO' },
    { chave: 'memoriaRam', nome: 'Memória RAM', categoriaApi: 'MEMORIAS RAM' },
    { chave: 'armazenamento', nome: 'Armazenamento', categoriaApi: 'ARMAZENAMENTO' },
    { chave: 'fonte', nome: 'Fonte', categoriaApi: 'FONTES' },
    { chave: 'gabinete', nome: 'Gabinete', categoriaApi: 'GABINETES' },
    { chave: 'refrigeracao', nome: 'Refrigeração', categoriaApi: 'REFRIGERACAO' },
] as const;

export type CategoriaMontagem = (typeof CATEGORIAS_MONTAGEM)[number]['chave'];
export type SelecaoMontagem = Partial<Record<CategoriaMontagem, import('@/features/types/produto').Produto>>;
