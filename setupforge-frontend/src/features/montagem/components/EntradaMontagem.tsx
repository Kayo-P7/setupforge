import { useRouter } from 'expo-router';
import { Cpu, ChevronRight } from 'lucide-react-native';
import { Pressable, StyleSheet, Text, View } from 'react-native';

export default function EntradaMontagem() {
    const router = useRouter();

    return (
        <Pressable
            onPress={() => router.push('/montar-pc')}
            style={({ pressed }) => [styles.card, pressed && styles.pressed]}
            accessibilityRole="button"
        >
            <View style={styles.icon}><Cpu color="#FF3434" size={26} /></View>
            <View style={styles.texts}>
                <Text style={styles.title}>Montar meu PC</Text>
                <Text style={styles.description}>Escolha os componentes para sua configuração</Text>
            </View>
            <ChevronRight color="#AAAAAA" size={22} />
        </Pressable>
    );
}

const styles = StyleSheet.create({
    card: { minHeight: 82, padding: 16, flexDirection: 'row', alignItems: 'center', gap: 14, borderRadius: 16, borderWidth: 1, borderColor: '#462626', backgroundColor: '#201A1A' },
    pressed: { opacity: 0.75, borderColor: '#FF3434' },
    icon: { width: 46, height: 46, alignItems: 'center', justifyContent: 'center', borderRadius: 13, backgroundColor: '#351D1D' },
    texts: { flex: 1, gap: 4 },
    title: { color: '#FFFFFF', fontSize: 16, fontWeight: '800' },
    description: { color: '#AAAAAA', fontSize: 12 },
});
