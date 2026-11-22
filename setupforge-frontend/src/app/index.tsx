
import { useState } from 'react';
import { SafeAreaView } from 'react-native-safe-area-context';
import { ScrollView, StyleSheet, View } from 'react-native';

import Header from '@/components/layout/Header';
import SearchBar from '@/components/layout/SearchBar';

export default function HomeScreen() {
  const [search, setSearch] = useState('');

  return (
      <SafeAreaView style={styles.container}>
        <ScrollView
            showsVerticalScrollIndicator={false}
            contentContainerStyle={styles.content}
        >
          <Header />

          <SearchBar
              value={search}
              onChangeText={setSearch}
          />

          <View style={styles.sections}>
            {/* As próximas seções da Home serão adicionadas aqui. */}
          </View>
        </ScrollView>
      </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#101010',
  },
  content: {
    paddingHorizontal: 20,
    paddingTop: 16,
    paddingBottom: 32,
    gap: 28,
  },
  sections: {
    gap: 24,
  },
});
