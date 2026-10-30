import java.util.concurrent.RecursiveAction;

// Merge Sort paralelo com Fork/Join (baseado no codigo da Aula 11)
public class MergeSortTask extends RecursiveAction {

    // abaixo desse tamanho a parte e ordenada de forma serial,
    // para nao criar tarefas pequenas demais
    private static final int LIMITE = 10000;

    private int[] array;
    private int inicio;
    private int fim;

    public MergeSortTask(int[] array) {
        this(array, 0, array.length - 1);
    }

    public MergeSortTask(int[] array, int inicio, int fim) {
        this.array = array;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    protected void compute() {
        if (inicio < fim) {
            if (fim - inicio + 1 <= LIMITE) {
                ordenaSerial();
                return;
            }
            int meio = (inicio + fim) / 2; // calcula o meio
            // Realiza as chamadas recursivas paralelamente (fork)
            invokeAll(new MergeSortTask(array, inicio, meio),
                      new MergeSortTask(array, meio + 1, fim));
            merge(inicio, meio, fim); // Intercala os subvetores (join)
        }
    }

    private void ordenaSerial() {
        int n = fim - inicio + 1;
        int[] parte = new int[n];
        System.arraycopy(array, inicio, parte, 0, n);
        MergeSort.mergeSort(parte, n);
        System.arraycopy(parte, 0, array, inicio, n);
    }

    private void merge(int inicio, int meio, int fim) {
        int leftSize = meio - inicio + 1;
        int rightSize = fim - meio;
        int[] left = new int[leftSize];
        int[] right = new int[rightSize];
        System.arraycopy(array, inicio, left, 0, leftSize);
        System.arraycopy(array, meio + 1, right, 0, rightSize);

        int i = 0, j = 0, k = inicio;
        while (i < leftSize && j < rightSize) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        while (i < leftSize) {
            array[k++] = left[i++];
        }
        while (j < rightSize) {
            array[k++] = right[j++];
        }
    }
}
