
import { Stack } from 'expo-router';
import { MontagemProvider } from '@/features/montagem/context/MontagemContext';

export default function RootLayout() {
    return (
      <MontagemProvider>
        <Stack
            screenOptions={{
                headerShown: false,
                contentStyle: {
                    backgroundColor: '#101010',
                },
            }}
        />
      </MontagemProvider>
    );
}
