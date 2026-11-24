import { FlatList, StyleSheet, useWindowDimensions } from 'react-native';
import type { Produto } from '@/features/types/produto';
import ProdutoCard from './ProdutoCard';
import { identidadeLocalProduto } from '@/features/montagem/utils/montagem';

interface Props {
    produtos: Produto[];
    onSelecionar: (produto: Produto) => void;
}

export default function ProdutoList({ produtos, onSelecionar }: Props) {
    const { width } = useWindowDimensions();
    const columns = width >= 1000 ? 4 : width >= 680 ? 3 : 2;

    return (
        <FlatList
            key={columns}
            data={produtos}
            keyExtractor={(produto, index) => `${identidadeLocalProduto(produto)}-${index}`}
            numColumns={columns}
            columnWrapperStyle={styles.row}
            contentContainerStyle={styles.list}
            renderItem={({ item }) => <ProdutoCard produto={item} onPress={() => onSelecionar(item)} />}
        />
    );
}

const styles = StyleSheet.create({
    row: { justifyContent: 'space-between', gap: 10 },
    list: { paddingHorizontal: 16, paddingBottom: 28, gap: 10 },
});
