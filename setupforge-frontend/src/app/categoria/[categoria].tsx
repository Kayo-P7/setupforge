import { useEffect, useMemo, useState } from 'react';
import { Stack, useLocalSearchParams, useRouter } from 'expo-router';
import { ActivityIndicator, SafeAreaView, StyleSheet, Text, View } from 'react-native';
import ProdutoList from '@/features/produtos/components/ProdutoList';
import { buscarProdutos } from '@/features/produtos/services/produtoService';
import type { Produto } from '@/features/types/produto';

export default function CategoriaScreen() {
    const { categoria, nome } = useLocalSearchParams<{ categoria: string; nome: string }>();
    const router = useRouter();
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

    const produtosCategoria = useMemo(
        () => produtos.filter((produto) => produto.categoriaNome?.trim().toLocaleLowerCase('pt-BR') === nome?.trim().toLocaleLowerCase('pt-BR')),
        [produtos, nome],
    );

    function abrirProduto(produto: Produto) {
        router.push({
            pathname: '/produtos/detalhes',
            params: {
                nome: produto.nome,
                descricao: produto.descricao ?? '',
                marca: produto.marca,
                preco: String(produto.preco),
                imagemUrl: produto.imagemUrl ?? '',
                categoriaNome: produto.categoriaNome,
                categoriaDescricao: produto.categoriaDescricao ?? '',
            },
        });
    }

    return (
        <SafeAreaView style={styles.container}>
            <Stack.Screen options={{ headerShown: true, title: nome || 'Categoria', headerStyle: { backgroundColor: '#101010' }, headerTintColor: '#FFFFFF', headerTitleStyle: { fontWeight: 'bold' } }} />
            <View style={styles.heading}>
                <Text style={styles.title}>{nome || 'Categoria'}</Text>
                <Text style={styles.subtitle}>Produtos desta categoria</Text>
                <Text style={styles.categoryId}>Categoria {categoria}</Text>
            </View>
            {loading ? <ActivityIndicator style={styles.state} color="#FF3434" size="large" accessibilityLabel="Carregando produtos" /> : error ? (
                <Text style={styles.stateText}>Não foi possível carregar os produtos.</Text>
            ) : produtosCategoria.length === 0 ? (
                <Text style={styles.stateText}>Nenhum produto encontrado nesta categoria.</Text>
            ) : (
                <ProdutoList produtos={produtosCategoria} onSelecionar={abrirProduto} />
            )}
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#101010' },
    heading: { paddingHorizontal: 20, paddingTop: 16, paddingBottom: 18, gap: 6 },
    title: { color: '#FFFFFF', fontSize: 24, fontWeight: '900' },
    subtitle: { color: '#AAAAAA', fontSize: 14 },
    categoryId: { color: '#777777', fontSize: 11 },
    state: { marginTop: 40 },
    stateText: { color: '#AAAAAA', textAlign: 'center', padding: 24 },
});
