import SecaoCategoria from '@/features/home/SecaoCategoria';
import { useState } from 'react';
import { useRouter } from 'expo-router';
import { SafeAreaView } from 'react-native-safe-area-context';
import { ScrollView, StyleSheet, View } from 'react-native';
import HomeBanner from '@/features/home/HomeBanner';
import Header from '@/components/layout/Header';
import SearchBar from '@/components/layout/SearchBar';
import EntradaMontagem from '@/features/montagem/components/EntradaMontagem';
import type { Metadata } from 'expo-router/server';

export function generateMetadata(): Metadata {
  return {
    title: 'SetupForge | Componentes para seu setup',
    description: 'Explore componentes de hardware e monte uma configuração personalizada com o catálogo SetupForge.',
  };
}

export default function HomeScreen() {
  const router = useRouter();
  const [search, setSearch] = useState('');

  function buscarProdutos() {
    const termo = search.trim();
    if (!termo) return;
    router.push({ pathname: '/produtos/busca', params: { termo } });
  }

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
              onSubmit={buscarProdutos}
          />

          <View style={styles.sections}>
            <HomeBanner />
            <EntradaMontagem />
            <SecaoCategoria />
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
