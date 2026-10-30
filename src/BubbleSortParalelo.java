// Bubble Sort paralelo, seguindo a dica da Aula 11: o vetor e dividido
// entre as threads, cada uma ordena a sua parte e no final as partes
// sao intercaladas pela thread principal
public class BubbleSortParalelo {

    public static void bubbleSort(int[] array, int numThreads) {
        int tamanhoParte = array.length / numThreads;
        int[] inicios = new int[numThreads + 1];
        Thread[] threads = new Thread[numThreads];

        // Passo 1: cada thread ordena um subarray
        for (int i = 0; i < numThreads; i++) {
            final int start = i * tamanhoParte;
            final int end = (i == numThreads - 1) ? array.length : (i + 1) * tamanhoParte;
            inicios[i] = start;

            threads[i] = new Thread(() -> BubbleSort.bubbleSort(array, start, end));
            threads[i].start();
        }
        inicios[numThreads] = array.length;

        // Aguardar a conclusao de todas as threads
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Passo 2: intercala as partes ordenadas, uma de cada vez
        int[] aux = new int[array.length];
        for (int i = 1; i < numThreads; i++) {
            intercala(array, aux, 0, inicios[i], inicios[i + 1]);
        }
    }

    // junta os trechos ordenados [ini, meio) e [meio, fim)
    private static void intercala(int[] a, int[] aux, int ini, int meio, int fim) {
        System.arraycopy(a, ini, aux, ini, fim - ini);
        int i = ini, j = meio, k = ini;
        while (i < meio && j < fim) {
            if (aux[i] <= aux[j]) {
                a[k++] = aux[i++];
            } else {
                a[k++] = aux[j++];
            }
        }
        while (i < meio) {
            a[k++] = aux[i++];
        }
        while (j < fim) {
            a[k++] = aux[j++];
        }
    }
}
