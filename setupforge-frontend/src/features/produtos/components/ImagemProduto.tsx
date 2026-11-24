import { useState } from 'react';
import { ActivityIndicator, StyleSheet, Text, View, type StyleProp, type ViewStyle } from 'react-native';
import { Image } from 'expo-image';
import { urlImagemProduto } from '../utils/imagemProduto';

interface Props {
    imagemUrl: string | null;
    nome: string;
    style: StyleProp<ViewStyle>;
    prioridade?: 'low' | 'normal' | 'high';
}

export default function ImagemProduto({ imagemUrl, nome, style, prioridade = 'normal' }: Props) {
    const uri = urlImagemProduto(imagemUrl);

    if (!uri) {
        return (
            <View style={[style, styles.fallback]}>
                <Text style={styles.fallbackText}>Imagem indisponível</Text>
            </View>
        );
    }

    return <ImagemRemota key={uri} uri={uri} nome={nome} style={style} prioridade={prioridade} />;
}

interface ImagemRemotaProps {
    uri: string;
    nome: string;
    style: StyleProp<ViewStyle>;
    prioridade: 'low' | 'normal' | 'high';
}

function ImagemRemota({ uri, nome, style, prioridade }: ImagemRemotaProps) {
    const [carregando, setCarregando] = useState(Boolean(uri));
    const [erro, setErro] = useState(false);

    if (erro) {
        return (
            <View style={[style, styles.fallback]}>
                <Text style={styles.fallbackText}>Imagem indisponível</Text>
            </View>
        );
    }

    return (
        <View style={style}>
            <Image
                source={{ uri }}
                style={StyleSheet.absoluteFill}
                contentFit="contain"
                cachePolicy="disk"
                priority={prioridade}
                transition={150}
                recyclingKey={uri}
                accessibilityLabel={nome}
                onLoad={() => setCarregando(false)}
                onError={() => { setCarregando(false); setErro(true); }}
            />
            {carregando && <ActivityIndicator style={styles.indicator} color="#FF3434" accessibilityLabel={`Carregando imagem de ${nome}`} />}
        </View>
    );
}

const styles = StyleSheet.create({
    fallback: { alignItems: 'center', justifyContent: 'center', padding: 12, backgroundColor: '#181818' },
    fallbackText: { color: '#888888', fontSize: 12, textAlign: 'center' },
    indicator: { ...StyleSheet.absoluteFill, alignItems: 'center', justifyContent: 'center' },
});
