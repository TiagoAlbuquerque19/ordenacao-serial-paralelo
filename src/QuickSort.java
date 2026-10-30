// Quick Sort serial
public class QuickSort {

    public static void quickSort(int[] arr, int inicio, int fim) {
        while (inicio < fim) {
            int p = particiona(arr, inicio, fim);
            // ordena primeiro a parte menor para nao estourar a pilha
            if (p - inicio < fim - p) {
                quickSort(arr, inicio, p - 1);
                inicio = p + 1;
            } else {
                quickSort(arr, p + 1, fim);
                fim = p - 1;
            }
        }
    }

    // Pivo escolhido pela mediana de tres (inicio, meio e fim),
    // evita o pior caso quando o vetor ja esta ordenado
    public static int particiona(int[] arr, int inicio, int fim) {
        int meio = (inicio + fim) / 2;
        if (arr[meio] < arr[inicio]) troca(arr, meio, inicio);
        if (arr[fim] < arr[inicio]) troca(arr, fim, inicio);
        if (arr[meio] < arr[fim]) troca(arr, meio, fim);
        int pivo = arr[fim];

        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            if (arr[j] <= pivo) {
                i++;
                troca(arr, i, j);
            }
        }
        troca(arr, i + 1, fim);
        return i + 1;
    }

    private static void troca(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
