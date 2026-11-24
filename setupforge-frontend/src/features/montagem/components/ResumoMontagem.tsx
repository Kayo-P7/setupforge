import { StyleSheet, Text, View } from 'react-native';
import { formatarPreco } from '@/features/produtos/components/ProdutoCard';
import { CATEGORIAS_MONTAGEM, type SelecaoMontagem } from '../types/montagem';
import { categoriasPendentes, totalMontagem } from '../utils/montagem';

interface Props {
    selecao: SelecaoMontagem;
}

export default function ResumoMontagem({ selecao }: Props) {
    const pendentes = categoriasPendentes(selecao);
    const selecionadas = CATEGORIAS_MONTAGEM.filter(({ chave }) => selecao[chave]);

    return (
        <View style={styles.card}>
            <Text style={styles.title}>Resumo da configuração</Text>
            {selecionadas.length === 0 ? <Text style={styles.muted}>Nenhum componente selecionado.</Text> : selecionadas.map(({ chave, nome }) => {
                const produto = selecao[chave];
                if (!produto) return null;
                return (
                    <View key={chave} style={styles.line}>
                        <View style={styles.itemText}>
                            <Text style={styles.category}>{nome}</Text>
                            <Text style={styles.product} numberOfLines={1}>{produto.nome}</Text>
                        </View>
                        <Text style={styles.price}>{formatarPreco(produto.preco)}</Text>
                    </View>
                );
            })}
            <View style={styles.separator} />
            <View style={styles.line}>
                <Text style={styles.totalLabel}>Total</Text>
                <Text style={styles.total}>{formatarPreco(totalMontagem(selecao))}</Text>
            </View>
            <Text style={styles.pendingTitle}>Categorias pendentes ({pendentes.length})</Text>
            <Text style={styles.muted}>{pendentes.length ? pendentes.map(({ nome }) => nome).join(', ') : 'Todas as categorias foram preenchidas.'}</Text>
        </View>
    );
}

const styles = StyleSheet.create({
    card: { padding: 16, gap: 12, backgroundColor: '#1B1B1B', borderWidth: 1, borderColor: '#343434', borderRadius: 14 },
    title: { color: '#FFFFFF', fontSize: 17, fontWeight: '800' },
    line: { flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between', gap: 12 },
    itemText: { flex: 1, gap: 3 },
    category: { color: '#FF3434', fontSize: 11, fontWeight: '700' },
    product: { color: '#DDDDDD', fontSize: 13 },
    price: { color: '#FFFFFF', fontSize: 13, fontWeight: '700' },
    separator: { height: 1, backgroundColor: '#383838' },
    totalLabel: { color: '#FFFFFF', fontSize: 15, fontWeight: '700' },
    total: { color: '#FF3434', fontSize: 19, fontWeight: '900' },
    pendingTitle: { color: '#CCCCCC', fontSize: 12, fontWeight: '700', marginTop: 2 },
    muted: { color: '#888888', fontSize: 12, lineHeight: 18 },
});
