
import { Search } from 'lucide-react-native';
import { Pressable, StyleSheet, TextInput, View } from 'react-native';

interface SearchBarProps {
    value: string;
    onChangeText: (text: string) => void;
    onSubmit: () => void;
}

export default function SearchBar({
                                      value,
                                      onChangeText,
                                      onSubmit,
                                  }: SearchBarProps) {
    return (
        <View style={styles.container}>
            <Pressable
                onPress={onSubmit}
                accessibilityRole="button"
                accessibilityLabel="Buscar produtos"
                hitSlop={8}
            >
                <Search color="#999999" size={20} />
            </Pressable>

            <TextInput
                style={styles.input}
                placeholder="Buscar produtos..."
                placeholderTextColor="#777777"
                value={value}
                onChangeText={onChangeText}
                onSubmitEditing={onSubmit}
                autoCapitalize="none"
                returnKeyType="search"
                accessibilityLabel="Buscar produtos"
            />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        height: 52,
        flexDirection: 'row',
        alignItems: 'center',
        gap: 12,
        paddingHorizontal: 16,
        backgroundColor: '#202020',
        borderRadius: 12,
        borderWidth: 1,
        borderColor: '#303030',
    },
    input: {
        flex: 1,
        color: '#FFFFFF',
        fontSize: 14,
    },
});
