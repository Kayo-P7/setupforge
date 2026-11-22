
import { Bell } from 'lucide-react-native';
import { Pressable, StyleSheet, Text, View } from 'react-native';

export default function Header() {
    return (
        <View style={styles.container}>
            <View>
                <Text style={styles.logo}>
                    SETUP<Text style={styles.highlight}>FORGE</Text>
                </Text>

                <Text style={styles.subtitle}>
                    Monte o setup ideal.
                </Text>
            </View>

            <Pressable
                style={styles.notificationButton}
                accessibilityRole="button"
                accessibilityLabel="Notificações"
            >
                <Bell color="#FFFFFF" size={22} />
            </Pressable>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
    },
    logo: {
        color: '#FFFFFF',
        fontSize: 24,
        fontWeight: '900',
        letterSpacing: 1,
    },
    highlight: {
        color: '#FF3434',
    },
    subtitle: {
        color: '#999999',
        fontSize: 13,
        marginTop: 4,
    },
    notificationButton: {
        width: 44,
        height: 44,
        borderRadius: 12,
        backgroundColor: '#202020',
        alignItems: 'center',
        justifyContent: 'center',
    },
});
