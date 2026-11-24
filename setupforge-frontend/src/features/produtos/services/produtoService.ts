import api from '@/services/api';
import type { Produto } from '@/features/types/produto';

const CACHE_TTL_MS = 60_000;
let produtosCache: { dados: Produto[]; salvoEm: number } | null = null;
let requisicaoProdutos: Promise<Produto[]> | null = null;

export async function buscarProdutos(): Promise<Produto[]> {
    if (produtosCache && Date.now() - produtosCache.salvoEm < CACHE_TTL_MS) {
        return produtosCache.dados;
    }

    if (requisicaoProdutos) return requisicaoProdutos;

    requisicaoProdutos = api.get<Produto[]>('/produto')
        .then(({ data }) => {
            if (!Array.isArray(data)) {
                throw new Error('A API retornou uma lista de produtos inválida.');
            }
            produtosCache = { dados: data, salvoEm: Date.now() };
            return data;
        })
        .finally(() => {
            requisicaoProdutos = null;
        });

    return requisicaoProdutos;
}

export interface Endereco {
    cep: string;
    logradouro: string;
    complemento: string;
    bairro: string;
    localidade: string;
    uf: string;
}

interface RespostaViaCep extends Endereco {
    erro?: boolean;
}

export async function consultarCep(cep: string): Promise<Endereco> {
    const response = await api.get<RespostaViaCep>(
        `https://viacep.com.br/ws/${cep}/json/`,
        { timeout: 10000 },
    );

    if (response.data.erro) {
        throw new Error('CEP não encontrado. Confira os números e tente novamente.');
    }

    return response.data;
}
