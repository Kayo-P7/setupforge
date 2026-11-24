import { useRef, useState } from 'react';
import { ActivityIndicator, Modal, Pressable, SafeAreaView, ScrollView, StyleSheet, Text, TextInput, View } from 'react-native';
import { Stack, useLocalSearchParams } from 'expo-router';
import { consultarCep, type Endereco } from '@/features/produtos/services/produtoService';
import { formatarPreco } from '@/features/produtos/components/ProdutoCard';
import ImagemProduto from '@/features/produtos/components/ImagemProduto';

export default function ProdutoDetalhesScreen() {
    const params = useLocalSearchParams<{ nome: string; descricao: string; marca: string; preco: string; imagemUrl: string; categoriaNome: string }>();
    const [cep, setCep] = useState('');
    const [endereco, setEndereco] = useState<Endereco | null>(null);
    const [mensagem, setMensagem] = useState('');
    const [consultando, setConsultando] = useState(false);
    const [confirmacaoVisivel, setConfirmacaoVisivel] = useState(false);
    const confirmacaoAberta = useRef(false);
    const preco = Number(params.preco);

    async function verificarCep() {
        if (cep.length !== 8) {
            setEndereco(null);
            setMensagem('Digite um CEP com 8 números.');
            return;
        }
        setConsultando(true);
        setEndereco(null);
        setMensagem('');
        try {
            const resultado = await consultarCep(cep);
            setEndereco(resultado);
            setMensagem('CEP encontrado.');
        } catch (error) {
            setMensagem(error instanceof Error ? error.message : 'Não foi possível consultar o CEP. Tente novamente.');
        } finally {
            setConsultando(false);
        }
    }

    function alterarCep(valor: string) {
        setCep(valor.replace(/\D/g, '').slice(0, 8));
        setEndereco(null);
        setMensagem('');
    }

    function confirmarCompra() {
        if (!endereco || confirmacaoAberta.current) return;
        confirmacaoAberta.current = true;
        setConfirmacaoVisivel(true);
    }

    function fecharConfirmacao() {
        confirmacaoAberta.current = false;
        setConfirmacaoVisivel(false);
    }

    return (
        <SafeAreaView style={styles.container}>
            <Stack.Screen options={{ headerShown: true, title: 'Detalhes do produto', headerStyle: { backgroundColor: '#101010' }, headerTintColor: '#FFFFFF' }} />
            <ScrollView contentContainerStyle={styles.content} keyboardShouldPersistTaps="handled">
                <ImagemProduto imagemUrl={params.imagemUrl || null} nome={params.nome} style={styles.image} prioridade="high" />
                <Text style={styles.category}>{params.categoriaNome}</Text>
                <Text style={styles.name}>{params.nome}</Text>
                <Text style={styles.brand}>{params.marca}</Text>
                <Text style={styles.price}>{formatarPreco(preco)}</Text>
                {!!params.descricao && <Text style={styles.description}>{params.descricao}</Text>}

                <View style={styles.purchaseCard}>
                    <Text style={styles.sectionTitle}>Consulte seu CEP</Text>
                    <View style={styles.cepRow}>
                        <TextInput
                            style={styles.input}
                            value={cep}
                            onChangeText={alterarCep}
                            placeholder="00000000"
                            placeholderTextColor="#777777"
                            keyboardType="number-pad"
                            maxLength={8}
                            accessibilityLabel="CEP"
                        />
                        <Pressable style={({ pressed }) => [styles.lookupButton, pressed && styles.pressed]} onPress={verificarCep} disabled={consultando}>
                            {consultando ? <ActivityIndicator color="#FFFFFF" /> : <Text style={styles.lookupText}>Consultar</Text>}
                        </Pressable>
                    </View>
                    {!!mensagem && <Text style={[styles.feedback, endereco ? styles.successText : styles.errorText]}>{mensagem}</Text>}
                    {endereco && <Text style={styles.address}>{[endereco.logradouro, endereco.bairro, `${endereco.localidade} - ${endereco.uf}`, endereco.cep].filter(Boolean).join('\n')}</Text>}
                    <Pressable
                        disabled={!endereco || confirmacaoVisivel}
                        onPress={confirmarCompra}
                        style={({ pressed }) => [styles.buyButton, endereco ? styles.buyEnabled : styles.buyDisabled, pressed && endereco && styles.pressed]}
                    >
                        <Text style={styles.buyText}>Comprar</Text>
                    </Pressable>
                </View>
            </ScrollView>
            <Modal
                visible={confirmacaoVisivel}
                transparent
                animationType="fade"
                onRequestClose={fecharConfirmacao}
            >
                <View style={styles.modalBackdrop}>
                    <View style={styles.confirmationCard} accessibilityViewIsModal>
                        <View style={styles.confirmationAccent} />
                        <Text style={styles.confirmationTitle}>Compra realizada com sucesso!</Text>
                        <Text style={styles.confirmationMessage}>
                            Seu produto está sendo preparado para envio. Em breve ele estará a caminho!
                        </Text>
                        <Pressable
                            onPress={fecharConfirmacao}
                            style={({ pressed }) => [styles.confirmationButton, pressed && styles.pressed]}
                            accessibilityRole="button"
                            accessibilityLabel="Fechar confirmação de compra"
                        >
                            <Text style={styles.confirmationButtonText}>Continuar</Text>
                        </Pressable>
                    </View>
                </View>
            </Modal>
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#101010' },
    content: { padding: 20, paddingBottom: 36, gap: 10 },
    image: { height: 240, width: '100%', borderRadius: 14, backgroundColor: '#1A1A1A' },
    category: { color: '#FF3434', fontSize: 12, fontWeight: '700', marginTop: 8 },
    name: { color: '#FFFFFF', fontSize: 24, fontWeight: '900' },
    brand: { color: '#AAAAAA', fontSize: 14 },
    price: { color: '#FF3434', fontSize: 22, fontWeight: '900', marginVertical: 4 },
    description: { color: '#CCCCCC', fontSize: 14, lineHeight: 21, marginBottom: 10 },
    purchaseCard: { marginTop: 12, padding: 16, gap: 12, borderRadius: 14, backgroundColor: '#202020', borderWidth: 1, borderColor: '#303030' },
    sectionTitle: { color: '#FFFFFF', fontSize: 17, fontWeight: '800' },
    cepRow: { flexDirection: 'row', gap: 10 },
    input: { flex: 1, color: '#FFFFFF', backgroundColor: '#151515', borderWidth: 1, borderColor: '#404040', borderRadius: 9, paddingHorizontal: 12, height: 48, fontSize: 16 },
    lookupButton: { minWidth: 102, alignItems: 'center', justifyContent: 'center', backgroundColor: '#B92A2A', paddingHorizontal: 12, borderRadius: 9 },
    lookupText: { color: '#FFFFFF', fontWeight: '700' },
    feedback: { fontSize: 13 },
    successText: { color: '#48D597' },
    errorText: { color: '#FF7777' },
    address: { color: '#CCCCCC', fontSize: 13, lineHeight: 20 },
    buyButton: { alignItems: 'center', justifyContent: 'center', height: 50, borderRadius: 9, marginTop: 4 },
    buyEnabled: { backgroundColor: '#20A464' },
    buyDisabled: { backgroundColor: '#444444' },
    buyText: { color: '#FFFFFF', fontSize: 16, fontWeight: '800' },
    muted: { color: '#888888' },
    pressed: { opacity: 0.8 },
    modalBackdrop: { flex: 1, justifyContent: 'center', alignItems: 'center', padding: 24, backgroundColor: 'rgba(0, 0, 0, 0.78)' },
    confirmationCard: { width: '100%', maxWidth: 420, overflow: 'hidden', padding: 22, gap: 14, borderRadius: 18, borderWidth: 1, borderColor: '#553030', backgroundColor: '#1B1B1B' },
    confirmationAccent: { position: 'absolute', top: 0, left: 0, right: 0, height: 4, backgroundColor: '#FF3434' },
    confirmationTitle: { color: '#FFFFFF', fontSize: 20, lineHeight: 27, fontWeight: '900', marginTop: 4 },
    confirmationMessage: { color: '#CCCCCC', fontSize: 15, lineHeight: 22 },
    confirmationButton: { minHeight: 48, justifyContent: 'center', alignItems: 'center', marginTop: 4, borderRadius: 10, backgroundColor: '#B92A2A' },
    confirmationButtonText: { color: '#FFFFFF', fontSize: 15, fontWeight: '800' },
});
