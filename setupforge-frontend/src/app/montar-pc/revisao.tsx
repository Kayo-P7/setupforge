import { useState } from 'react';
import { Alert, Pressable, SafeAreaView, ScrollView, StyleSheet, Text, View } from 'react-native';
import { Stack } from 'expo-router';
import ResumoMontagem from '@/features/montagem/components/ResumoMontagem';
import { useMontagem } from '@/features/montagem/context/MontagemContext';
import { CATEGORIAS_MONTAGEM } from '@/features/montagem/types/montagem';
import { categoriasPendentes, totalMontagem } from '@/features/montagem/utils/montagem';
import { formatarPreco } from '@/features/produtos/components/ProdutoCard';

export default function RevisaoMontagemScreen() {
    const { selecao } = useMontagem();
    const [finalizada, setFinalizada] = useState(false);
    const pendentes = categoriasPendentes(selecao);
    const podeFinalizar = pendentes.length === 0;

    function finalizar() {
        if (!podeFinalizar) return;
        setFinalizada(true);
        Alert.alert('Configuração finalizada', 'Sua montagem demonstrativa foi finalizada com sucesso!');
    }

    return (
        <SafeAreaView style={styles.container}>
            <Stack.Screen options={{ headerShown: true, title: 'Revisão da montagem', headerStyle: { backgroundColor: '#101010' }, headerTintColor: '#FFFFFF' }} />
            <ScrollView contentContainerStyle={styles.content}>
                <Text style={styles.title}>Revise sua configuração</Text>
                <ResumoMontagem selecao={selecao} />
                {CATEGORIAS_MONTAGEM.filter(({ chave }) => selecao[chave]).map(({ chave, nome }) => {
                    const produto = selecao[chave];
                    if (!produto) return null;
                    return (
                        <View key={chave} style={styles.item}>
                            <Text style={styles.category}>{nome}</Text>
                            <Text style={styles.product}>{produto.nome}</Text>
                            <Text style={styles.meta}>{produto.marca} · {formatarPreco(produto.preco)}</Text>
                        </View>
                    );
                })}
                {!podeFinalizar && <Text style={styles.pending}>Preencha as categorias pendentes antes de finalizar: {pendentes.map(({ nome }) => nome).join(', ')}.</Text>}
                {finalizada && <Text style={styles.success}>Montagem demonstrativa finalizada.</Text>}
                <Pressable
                    disabled={!podeFinalizar}
                    onPress={finalizar}
                    style={({ pressed }) => [styles.button, podeFinalizar ? styles.enabled : styles.disabled, pressed && podeFinalizar && styles.pressed]}
                >
                    <Text style={styles.buttonText}>{podeFinalizar ? 'Finalizar montagem' : 'Complete os componentes'}</Text>
                </Pressable>
                <Text style={styles.total}>Total: {formatarPreco(totalMontagem(selecao))}</Text>
                <Text style={styles.note}>Finalização demonstrativa. Nenhum pedido ou pagamento será criado.</Text>
            </ScrollView>
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#101010' },
    content: { padding: 18, paddingBottom: 32, gap: 14 },
    title: { color: '#FFFFFF', fontSize: 23, fontWeight: '900' },
    item: { maxHeight: 90, padding: 14, backgroundColor: '#202020', borderWidth: 1, borderColor: '#303030', borderRadius: 12 },
    category: { color: '#FF3434', fontSize: 12, fontWeight: '800' },
    product: { color: '#FFFFFF', fontSize: 14, fontWeight: '700', marginTop: 4 },
    meta: { color: '#AAAAAA', fontSize: 12, marginTop: 4 },
    pending: { color: '#FF9999', lineHeight: 20 },
    success: { color: '#48D597', textAlign: 'center' },
    button: { minHeight: 50, alignItems: 'center', justifyContent: 'center', borderRadius: 12 },
    enabled: { backgroundColor: '#B92A2A' },
    disabled: { backgroundColor: '#444444' },
    pressed: { opacity: 0.8 },
    buttonText: { color: '#FFFFFF', fontWeight: '800', fontSize: 15 },
    total: { color: '#FF3434', textAlign: 'center', fontSize: 20, fontWeight: '900' },
    note: { color: '#777777', textAlign: 'center', fontSize: 11, lineHeight: 17 },
});
