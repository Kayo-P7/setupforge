
import { Cpu } from 'lucide-react-native';
import { StyleSheet, Text, View } from 'react-native';

export default function HomeBanner() {
    return (
        <View style={styles.container}>
            <View style={styles.content}>
                <Text style={styles.label}>SETUPFORGE</Text>

                <Text style={styles.title}>
                    Monte o setup
                    {'\n'}
                    <Text style={styles.highlight}>dos seus sonhos.</Text>
                </Text>

                <Text style={styles.description}>
                    Encontre os componentes ideais para
                    transformar sua experiência.
                </Text>
            </View>

            <View style={styles.iconContainer}>
                <Cpu color="#FF3434" size={58} strokeWidth={1.4} />
            </View>

            <View style={styles.accent} />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        minHeight: 190,
        backgroundColor: '#1C1C1C',
        borderRadius: 18,
        padding: 20,
        overflow: 'hidden',
        justifyContent: 'center',
        borderWidth: 1,
        borderColor: '#303030',
    },
    content: {
        width: '75%',
        gap: 10,
        zIndex: 1,
    },
    label: {
        color: '#FF3434',
        fontSize: 11,
        fontWeight: '800',
        letterSpacing: 2,
    },
    title: {
        color: '#FFFFFF',
        fontSize: 23,
        fontWeight: '900',
        lineHeight: 29,
    },
    highlight: {
        color: '#FF3434',
    },
    description: {
        color: '#AAAAAA',
        fontSize: 12,
        lineHeight: 18,
    },
    iconContainer: {
        position: 'absolute',
        right: 18,
        top: 55,
        opacity: 0.9,
    },
    accent: {
        position: 'absolute',
        width: 120,
        height: 120,
        borderRadius: 60,
        backgroundColor: '#FF3434',
        opacity: 0.07,
        right: -35,
        bottom: -45,
    },
});
