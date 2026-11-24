import { Stack, useRouter } from 'expo-router';
import { SafeAreaView } from 'react-native-safe-area-context';
import { Pressable, ScrollView, StyleSheet, Text, View } from 'react-native';
import EtapaComponente from '@/features/montagem/components/EtapaComponente';
import ResumoMontagem from '@/features/montagem/components/ResumoMontagem';
import { useMontagem } from '@/features/montagem/context/MontagemContext';
import { CATEGORIAS_MONTAGEM } from '@/features/montagem/types/montagem';

export default function MontarPcScreen() {
    const router = useRouter();
    const { selecao, removerProduto } = useMontagem();

    return (
        <SafeAreaView style={styles.container}>
            <Stack.Screen options={{ headerShown: true, title: 'Montar meu PC', headerStyle: { backgroundColor: '#101010' }, headerTintColor: '#FFFFFF' }} />
            <ScrollView contentContainerStyle={styles.content}>
                <View style={styles.intro}>
                    <Text style={styles.title}>Monte sua configuração</Text>
                    <Text style={styles.subtitle}>Escolha um componente disponível em cada categoria.</Text>
                </View>

                <View style={styles.list}>
                    {CATEGORIAS_MONTAGEM.map(({ chave, nome }) => (
                        <EtapaComponente
                            key={chave}
                            categoria={chave}
                            titulo={nome}
                            produto={selecao[chave]}
                            onEscolher={() => router.push({ pathname: '/montar-pc/selecionar', params: { categoria: chave } })}
                            onRemover={() => removerProduto(chave)}
                        />
                    ))}
                </View>

                <ResumoMontagem selecao={selecao} />
                <Pressable style={({ pressed }) => [styles.reviewButton, pressed && styles.pressed]} onPress={() => router.push('/montar-pc/revisao')}>
                    <Text style={styles.reviewText}>Revisar configuração</Text>
                </Pressable>
                <Text style={styles.note}>A montagem é demonstrativa e não verifica compatibilidade técnica.</Text>
            </ScrollView>
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#101010' },
    content: { padding: 18, paddingBottom: 32, gap: 18 },
    intro: { gap: 6 },
    title: { color: '#FFFFFF', fontSize: 23, fontWeight: '900' },
    subtitle: { color: '#AAAAAA', fontSize: 13, lineHeight: 19 },
    list: { gap: 10 },
    reviewButton: { alignItems: 'center', justifyContent: 'center', minHeight: 50, backgroundColor: '#B92A2A', borderRadius: 12 },
    reviewText: { color: '#FFFFFF', fontSize: 15, fontWeight: '800' },
    pressed: { opacity: 0.8 },
    note: { color: '#777777', textAlign: 'center', fontSize: 11 },
});
