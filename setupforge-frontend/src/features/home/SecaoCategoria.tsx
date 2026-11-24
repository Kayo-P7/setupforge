
import { useEffect, useState } from 'react';
import { useRouter } from 'expo-router';
import {
    ActivityIndicator,
    Pressable,
    ScrollView,
    StyleSheet,
    Text,
    View,
} from 'react-native';

import { buscarCategorias } from '@/features/home/services/categoriaService';
import type { Categoria } from '@/features/types/categoria';

export default function SecaoCategoria() {
    const router = useRouter();

    const [categorias, setCategorias] = useState<Categoria[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
        async function carregarCategorias() {
            try {
                setError(false);

                const dados = await buscarCategorias();

                setCategorias(dados);
            } catch {
                setError(true);
            } finally {
                setLoading(false);
            }
        }

        carregarCategorias();
    }, []);

    function abrirCategoria(categoria: Categoria) {
        router.push({
            pathname: '/categoria/[categoria]',
            params: {
                categoria: String(categoria.id),
                nome: categoria.nome,
            },
        });
    }

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Categorias</Text>

            {loading ? (
                <ActivityIndicator color="#FF3434" size="large" accessibilityLabel="Carregando categorias" />
            ) : error ? (
                <Text style={styles.message}>
                    Não foi possível carregar as categorias.
                </Text>
            ) : categorias.length === 0 ? (
                <Text style={styles.message}>
                    Nenhuma categoria encontrada.
                </Text>
            ) : (
                <ScrollView
                    horizontal
                    showsHorizontalScrollIndicator={false}
                    contentContainerStyle={styles.list}
                >
                    {categorias.map((categoria) => (
                        <Pressable
                            key={categoria.id}
                            style={({ pressed }) => [
                                styles.card,
                                pressed && styles.cardPressed,
                            ]}
                            onPress={() => abrirCategoria(categoria)}
                            accessibilityRole="button"
                            accessibilityLabel={`Abrir categoria ${categoria.nome}`}
                        >
                            <Text style={styles.categoryName}>
                                {categoria.nome}
                            </Text>

                            <Text style={styles.description} numberOfLines={2}>
                                {categoria.descricao}
                            </Text>
                        </Pressable>
                    ))}
                </ScrollView>
            )}
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        gap: 16,
    },
    title: {
        color: '#FFFFFF',
        fontSize: 20,
        fontWeight: '800',
    },
    list: {
        gap: 12,
        paddingRight: 20,
    },
    card: {
        width: 190,
        minHeight: 110,
        padding: 16,
        justifyContent: 'center',
        gap: 10,
        backgroundColor: '#202020',
        borderRadius: 14,
        borderWidth: 1,
        borderColor: '#303030',
    },
    cardPressed: {
        opacity: 0.7,
        borderColor: '#FF3434',
    },
    categoryName: {
        color: '#FF3434',
        fontSize: 14,
        fontWeight: '800',
    },
    description: {
        color: '#AAAAAA',
        fontSize: 12,
        lineHeight: 18,
    },
    message: {
        color: '#AAAAAA',
        fontSize: 13,
    },
});
