import { useEffect, useMemo, useState } from 'react';
import { ActivityIndicator, Pressable, SafeAreaView, StyleSheet, Text, View } from 'react-native';
import { Stack, useLocalSearchParams, useRouter } from 'expo-router';
import ProdutoList from '@/features/produtos/components/ProdutoList';
import { buscarProdutos } from '@/features/produtos/services/produtoService';
import type { Produto } from '@/features/types/produto';

function normalizar(valor: string) {
    return valor.normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLocaleLowerCase('pt-BR');
}

export default function BuscaProdutosScreen() {
    const { termo: parametro } = useLocalSearchParams<{ termo?: string | string[] }>();
    const termo = (Array.isArray(parametro) ? parametro[0] : parametro ?? '').trim();
    const router = useRouter();
    const [produtos, setProdutos] = useState<Produto[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);
    const [tentativa, setTentativa] = useState(0);

    useEffect(() => {
        let ativo = true;
        buscarProdutos()
            .then((dados) => { if (ativo) setProdutos(dados); })
            .catch(() => { if (ativo) setError(true); })
            .finally(() => { if (ativo) setLoading(false); });
        return () => { ativo = false; };
    }, [tentativa]);

    const resultados = useMemo(() => {
        const consulta = normalizar(termo);
        if (!consulta) return [];
        return produtos.filter((produto) => normalizar([
            produto.nome,
            produto.marca,
            produto.categoriaNome,
            produto.descricao ?? '',
        ].join(' ')).includes(consulta));
    }, [produtos, termo]);

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
            <Stack.Screen options={{ headerShown: true, title: 'Resultados da busca', headerStyle: { backgroundColor: '#101010' }, headerTintColor: '#FFFFFF' }} />
            <View style={styles.heading}>
                <Text style={styles.title}>Busca de produtos</Text>
                <Text style={styles.subtitle}>Resultados para “{termo}”</Text>
            </View>
            {loading ? (
                <ActivityIndicator style={styles.state} color="#FF3434" size="large" accessibilityLabel="Buscando produtos" />
            ) : error ? (
                <View style={styles.state}>
                    <Text style={styles.message}>Não foi possível carregar os produtos. Verifique sua conexão e tente novamente.</Text>
                    <Pressable
                        onPress={() => {
                            setLoading(true);
                            setError(false);
                            setTentativa((valor) => valor + 1);
                        }}
                        style={styles.retry}
                        accessibilityRole="button"
                    >
                        <Text style={styles.retryText}>Tentar novamente</Text>
                    </Pressable>
                </View>
            ) : resultados.length === 0 ? (
                <Text style={styles.message}>Nenhum produto encontrado para “{termo}”.</Text>
            ) : (
                <>
                    <Text style={styles.count}>{resultados.length} produto(s) encontrado(s)</Text>
                    <ProdutoList produtos={resultados} onSelecionar={abrirProduto} />
                </>
            )}
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#101010' },
    heading: { paddingHorizontal: 20, paddingTop: 18, paddingBottom: 10, gap: 6 },
    title: { color: '#FFFFFF', fontSize: 22, fontWeight: '900' },
    subtitle: { color: '#AAAAAA', fontSize: 14 },
    count: { color: '#AAAAAA', fontSize: 12, paddingHorizontal: 20, paddingBottom: 12 },
    state: { marginTop: 32, marginHorizontal: 24, alignItems: 'center', gap: 16 },
    message: { color: '#AAAAAA', textAlign: 'center', padding: 24 },
    retry: { minHeight: 44, justifyContent: 'center', paddingHorizontal: 20, backgroundColor: '#B92A2A', borderRadius: 10 },
    retryText: { color: '#FFFFFF', fontWeight: '700' },
});
