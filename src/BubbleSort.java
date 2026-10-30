// Bubble Sort serial (codigo da Aula 11)
public class BubbleSort {

    public static void bubbleSort(int[] arr) {
        bubbleSort(arr, 0, arr.length);
    }

    // ordena apenas o trecho [inicio, fim) do vetor
    public static void bubbleSort(int[] arr, int inicio, int fim) {
        int n = fim - inicio;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = inicio; j < fim - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Troca os elementos
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // Se nao houve trocas nesta iteracao, o vetor ja esta ordenado
            if (!swapped) {
                break;
            }
        }
    }
}
