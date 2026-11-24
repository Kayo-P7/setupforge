import api from '@/services/api';
import type { Categoria } from '@/features/types/categoria';

export async function buscarCategorias(): Promise<Categoria[]> {
    const response = await api.get<Categoria[]>('/categorias');

    return response.data;
}