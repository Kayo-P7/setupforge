import { useEffect, useState } from 'react';
import { ActivityIndicator, SafeAreaView, StyleSheet, Text, View } from 'react-native';
import { Stack, useLocalSearchParams, useRouter } from 'expo-router';
import ProdutoList from '@/features/produtos/components/ProdutoList';
import { buscarProdutos } from '@/features/produtos/services/produtoService';
import type { Produto } from '@/features/types/produto';
import { useMontagem } from '@/features/montagem/context/MontagemContext';
import { categoriaApiDaChave, produtosDaCategoria } from '@/features/montagem/utils/montagem';
import type { CategoriaMontagem } from '@/features/montagem/types/montagem';

export default function SelecionarComponenteScreen() {
    const { categoria } = useLocalSearchParams<{ categoria: string }>();
    const router = useRouter();
    const { selecionarProduto } = useMontagem();
    const categoriaMontagem = categoriaApiDaChave(categoria ?? '');
    const [produtos, setProdutos] = useState<Produto[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
        let ativo = true;
        buscarProdutos()
            .then((dados) => { if (ativo) setProdutos(dados); })
            .catch(() => { if (ativo) setError(true); })
            .finally(() => { if (ativo) setLoading(false); });
        return () => { ativo = false; };
    }, []);

    const produtosFiltrados = categoriaMontagem
        ? produtosDaCategoria(produtos, categoriaMontagem.chave as CategoriaMontagem)
        : [];

    function selecionar(produto: Produto) {
        if (!categoriaMontagem) return;
        selecionarProduto(categoriaMontagem.chave, produto);
        router.back();
    }

    return (
        <SafeAreaView style={styles.container}>
            <Stack.Screen options={{ headerShown: true, title: categoriaMontagem?.nome ?? 'Selecionar componente', headerStyle: { backgroundColor: '#101010' }, headerTintColor: '#FFFFFF' }} />
            <View style={styles.heading}>
                <Text style={styles.title}>{categoriaMontagem?.nome ?? 'Categoria inválida'}</Text>
                <Text style={styles.subtitle}>{categoriaMontagem ? `Produtos da categoria ${categoriaMontagem.categoriaApi}` : 'Volte à montagem e escolha uma categoria válida.'}</Text>
            </View>
            {loading ? <ActivityIndicator color="#FF3434" size="large" style={styles.state} accessibilityLabel="Carregando componentes" /> : error ? (
                <Text style={styles.message}>Não foi possível carregar os produtos da API.</Text>
            ) : produtosFiltrados.length === 0 ? (
                <Text style={styles.message}>A API não retornou produtos para esta categoria.</Text>
            ) : <ProdutoList produtos={produtosFiltrados} onSelecionar={selecionar} />}
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#101010' },
    heading: { padding: 18, gap: 6 },
    title: { color: '#FFFFFF', fontSize: 21, fontWeight: '900' },
    subtitle: { color: '#AAAAAA', fontSize: 12 },
    state: { marginTop: 30 },
    message: { color: '#AAAAAA', textAlign: 'center', padding: 24 },
});
