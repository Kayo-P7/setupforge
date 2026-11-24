import { Pressable, StyleSheet, Text, View } from 'react-native';
import type { Produto } from '@/features/types/produto';
import ImagemProduto from './ImagemProduto';

interface Props {
    produto: Produto;
    onPress: () => void;
}

export function formatarPreco(preco: number) {
    return preco.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

export default function ProdutoCard({ produto, onPress }: Props) {
    return (
        <Pressable style={({ pressed }) => [styles.card, pressed && styles.pressed]} onPress={onPress}>
            <ImagemProduto imagemUrl={produto.imagemUrl} nome={produto.nome} style={styles.image} prioridade="low" />
            <View style={styles.info}>
                <Text style={styles.name} numberOfLines={2}>{produto.nome}</Text>
                <Text style={styles.brand} numberOfLines={1}>{produto.marca}</Text>
                <Text style={styles.price}>{formatarPreco(produto.preco)}</Text>
            </View>
        </Pressable>
    );
}

const styles = StyleSheet.create({
    card: { flex: 1, minWidth: 0, backgroundColor: '#202020', borderRadius: 14, borderWidth: 1, borderColor: '#303030', overflow: 'hidden' },
    pressed: { opacity: 0.75, borderColor: '#FF3434' },
    image: { width: '100%', height: 132, backgroundColor: '#181818' },
    info: { padding: 12, gap: 6 },
    name: { color: '#FFFFFF', fontSize: 14, fontWeight: '700', minHeight: 36 },
    brand: { color: '#AAAAAA', fontSize: 12 },
    price: { color: '#FF3434', fontSize: 16, fontWeight: '800', marginTop: 4 },
});
