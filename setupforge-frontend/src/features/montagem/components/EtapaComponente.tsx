import { Pressable, StyleSheet, Text, View } from 'react-native';
import type { Produto } from '@/features/types/produto';
import { formatarPreco } from '@/features/produtos/components/ProdutoCard';
import type { CategoriaMontagem } from '../types/montagem';

interface Props {
    categoria: CategoriaMontagem;
    titulo: string;
    produto?: Produto;
    onEscolher: () => void;
    onRemover: () => void;
}

export default function EtapaComponente({ titulo, produto, onEscolher, onRemover }: Props) {
    return (
        <View style={styles.card}>
            <View style={styles.details}>
                <Text style={styles.category}>{titulo}</Text>
                {produto ? (
                    <>
                        <Text style={styles.name} numberOfLines={2}>{produto.nome}</Text>
                        <Text style={styles.meta}>{produto.marca} · {formatarPreco(produto.preco)}</Text>
                    </>
                ) : <Text style={styles.empty}>Nenhum componente selecionado</Text>}
            </View>
            <View style={styles.actions}>
                <Pressable onPress={onEscolher} style={styles.selectButton}>
                    <Text style={styles.selectText}>{produto ? 'Substituir' : 'Selecionar'}</Text>
                </Pressable>
                {produto && <Pressable onPress={onRemover} hitSlop={8}><Text style={styles.removeText}>Remover</Text></Pressable>}
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    card: { flexDirection: 'row', alignItems: 'center', gap: 12, padding: 15, backgroundColor: '#202020', borderWidth: 1, borderColor: '#303030', borderRadius: 14 },
    details: { flex: 1, gap: 5 },
    category: { color: '#FF3434', fontSize: 12, fontWeight: '800' },
    name: { color: '#FFFFFF', fontSize: 14, fontWeight: '700' },
    meta: { color: '#AAAAAA', fontSize: 12 },
    empty: { color: '#777777', fontSize: 12 },
    actions: { alignItems: 'flex-end', gap: 8 },
    selectButton: { paddingVertical: 8, paddingHorizontal: 11, borderRadius: 9, backgroundColor: '#342020' },
    selectText: { color: '#FFFFFF', fontSize: 12, fontWeight: '700' },
    removeText: { color: '#FF7777', fontSize: 11 },
});
