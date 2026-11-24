import { createContext, useContext, useMemo, useState, type PropsWithChildren } from 'react';
import type { Produto } from '@/features/types/produto';
import type { CategoriaMontagem, SelecaoMontagem } from '../types/montagem';

interface MontagemContextValue {
    selecao: SelecaoMontagem;
    selecionarProduto: (categoria: CategoriaMontagem, produto: Produto) => void;
    removerProduto: (categoria: CategoriaMontagem) => void;
}

const MontagemContext = createContext<MontagemContextValue | null>(null);

export function MontagemProvider({ children }: PropsWithChildren) {
    const [selecao, setSelecao] = useState<SelecaoMontagem>({});

    const value = useMemo<MontagemContextValue>(() => ({
        selecao,
        selecionarProduto: (categoria, produto) => setSelecao((atual) => ({ ...atual, [categoria]: produto })),
        removerProduto: (categoria) => setSelecao((atual) => {
            const novaSelecao = { ...atual };
            delete novaSelecao[categoria];
            return novaSelecao;
        }),
    }), [selecao]);

    return <MontagemContext.Provider value={value}>{children}</MontagemContext.Provider>;
}

export function useMontagem() {
    const context = useContext(MontagemContext);
    if (!context) throw new Error('useMontagem deve ser usado dentro de MontagemProvider.');
    return context;
}
