
import { Search } from 'lucide-react-native';
import { StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

export default function ExploreScreen() {
  return (
      <SafeAreaView style={styles.container}>
        <View style={styles.content}>
          <Search color="#FF3434" size={32} />

          <Text style={styles.title}>Explorar produtos</Text>

          <Text style={styles.description}>
            Encontre componentes para montar seu setup ideal.
          </Text>
        </View>
      </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#101010',
  },
  content: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 24,
    gap: 16,
  },
  title: {
    color: '#FFFFFF',
    fontSize: 22,
    fontWeight: '800',
  },
  description: {
    color: '#999999',
    fontSize: 14,
    textAlign: 'center',
    lineHeight: 21,
  },
});
