// Merge Sort serial (baseado no codigo da Aula 11)
public class MergeSort {

    public static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return; // caso base: array com tamanho 1 ou vazio
        }

        int mid = n / 2;
        int[] left = new int[mid];
        int[] right = new int[n - mid];

        // Preenche os arrays left e right com os elementos
        for (int i = 0; i < mid; i++) {
            left[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            right[i - mid] = a[i];
        }

        // Recursivamente ordena as metades
        mergeSort(left, mid);
        mergeSort(right, n - mid);

        // Combina as metades ordenadas
        merge(a, left, right, mid, n - mid);
    }

    public static void merge(int[] a, int[] left, int[] right, int leftSize, int rightSize) {
        int i = 0, j = 0, k = 0;

        // Compara os elementos de left e right e coloca o menor no array a
        while (i < leftSize && j < rightSize) {
            if (left[i] <= right[j]) {
                a[k++] = left[i++];
            } else {
                a[k++] = right[j++];
            }
        }

        // Copia os elementos restantes de left e right para o array a
        while (i < leftSize) {
            a[k++] = left[i++];
        }
        while (j < rightSize) {
            a[k++] = right[j++];
        }
    }
}
