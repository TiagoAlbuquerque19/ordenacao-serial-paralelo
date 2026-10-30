import java.util.concurrent.RecursiveAction;

// Quick Sort paralelo com Fork/Join, no mesmo modelo do MergeSortTask
public class QuickSortTask extends RecursiveAction {

    private static final int LIMITE = 10000;

    private int[] array;
    private int inicio;
    private int fim;

    public QuickSortTask(int[] array) {
        this(array, 0, array.length - 1);
    }

    public QuickSortTask(int[] array, int inicio, int fim) {
        this.array = array;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    protected void compute() {
        if (inicio < fim) {
            if (fim - inicio + 1 <= LIMITE) {
                QuickSort.quickSort(array, inicio, fim);
                return;
            }
            int p = QuickSort.particiona(array, inicio, fim);
            // cada lado do pivo e ordenado em paralelo (fork)
            invokeAll(new QuickSortTask(array, inicio, p - 1),
                      new QuickSortTask(array, p + 1, fim));
        }
    }
}
