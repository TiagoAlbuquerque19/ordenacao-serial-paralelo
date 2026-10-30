import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ForkJoinPool;

// Executa todos os testes e grava os resultados em CSV
public class Main {

    static final String[] ALGORITMOS = {"MergeSort", "QuickSort", "BubbleSort"};
    static final String[] TIPOS = {"aleatorio", "ordenado", "inverso"};
    static final int[] THREADS = {2, 4, 8};
    static final int AMOSTRAS = 4;
    static final int MAX_VALOR = 1000000;

    // o Bubble Sort e O(n^2), por isso usa vetores menores
    static final int[] TAMANHOS = {100000, 1000000, 5000000, 10000000};
    static final int[] TAMANHOS_BUBBLE = {5000, 10000, 25000, 50000};

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        System.out.println("Nucleos disponiveis: " + Runtime.getRuntime().availableProcessors());

        aquecimento();

        List<String> resumo = new ArrayList<>();
        PrintWriter csv = new PrintWriter(new FileWriter("resultados.csv"));
        csv.println("algoritmo,modo,threads,tamanho,tipo,amostra,tempo_ms");

        for (String alg : ALGORITMOS) {
            int[] tamanhos = alg.equals("BubbleSort") ? TAMANHOS_BUBBLE : TAMANHOS;
            for (String tipo : TIPOS) {
                for (int tam : tamanhos) {
                    int[] base = GeradorDados.gerar(tam, tipo, MAX_VALOR, 42);

                    // execucao serial
                    double[] serial = new double[AMOSTRAS];
                    for (int a = 0; a < AMOSTRAS; a++) {
                        serial[a] = medir(alg, base, 1);
                        csv.printf("%s,serial,1,%d,%s,%d,%.3f%n", alg, tam, tipo, a + 1, serial[a]);
                    }
                    double mediaSerial = media(serial);
                    resumo.add(linhaResumo(alg, "serial", 1, tam, tipo, serial, mediaSerial));

                    // execucoes paralelas
                    for (int th : THREADS) {
                        double[] par = new double[AMOSTRAS];
                        for (int a = 0; a < AMOSTRAS; a++) {
                            par[a] = medir(alg, base, th);
                            csv.printf("%s,paralelo,%d,%d,%s,%d,%.3f%n", alg, th, tam, tipo, a + 1, par[a]);
                        }
                        resumo.add(linhaResumo(alg, "paralelo", th, tam, tipo, par, mediaSerial));
                    }
                    csv.flush();
                    System.out.println(alg + " - " + tipo + " - " + tam + " elementos: ok");
                }
            }
        }
        csv.close();

        PrintWriter csvResumo = new PrintWriter(new FileWriter("resumo.csv"));
        csvResumo.println("algoritmo,modo,threads,tamanho,tipo,media_ms,desvio_ms,min_ms,max_ms,speedup,eficiencia");
        for (String linha : resumo) {
            csvResumo.println(linha);
        }
        csvResumo.close();
    }

    // Retorna o tempo de uma execucao em milissegundos (threads = 1 e a versao serial)
    static double medir(String alg, int[] base, int threads) {
        int[] v = base.clone();
        long tempoInicial = System.nanoTime();

        if (alg.equals("MergeSort")) {
            if (threads == 1) {
                MergeSort.mergeSort(v, v.length);
            } else {
                ForkJoinPool pool = new ForkJoinPool(threads);
                pool.invoke(new MergeSortTask(v));
                pool.shutdown();
            }
        } else if (alg.equals("QuickSort")) {
            if (threads == 1) {
                QuickSort.quickSort(v, 0, v.length - 1);
            } else {
                ForkJoinPool pool = new ForkJoinPool(threads);
                pool.invoke(new QuickSortTask(v));
                pool.shutdown();
            }
        } else {
            if (threads == 1) {
                BubbleSort.bubbleSort(v);
            } else {
                BubbleSortParalelo.bubbleSort(v, threads);
            }
        }

        long tempoFinal = System.nanoTime();
        if (!estaOrdenado(v)) {
            throw new RuntimeException(alg + " nao ordenou o vetor corretamente");
        }
        return (tempoFinal - tempoInicial) / 1000000.0;
    }

    // Algumas execucoes que nao sao registradas, para o JIT da JVM
    // compilar os metodos antes das medicoes
    static void aquecimento() {
        int[] grande = GeradorDados.gerar(1000000, "aleatorio", MAX_VALOR, 1);
        int[] pequeno = GeradorDados.gerar(5000, "aleatorio", MAX_VALOR, 1);
        for (int i = 0; i < 3; i++) {
            medir("MergeSort", grande, 1);
            medir("MergeSort", grande, 2);
            medir("QuickSort", grande, 1);
            medir("QuickSort", grande, 2);
            medir("BubbleSort", pequeno, 1);
            medir("BubbleSort", pequeno, 2);
        }
    }

    static boolean estaOrdenado(int[] v) {
        for (int i = 1; i < v.length; i++) {
            if (v[i - 1] > v[i]) return false;
        }
        return true;
    }

    static double media(double[] x) {
        double soma = 0;
        for (double d : x) soma += d;
        return soma / x.length;
    }

    static String linhaResumo(String alg, String modo, int th, int tam, String tipo, double[] x, double mediaSerial) {
        double m = media(x);
        double soma = 0, min = x[0], max = x[0];
        for (double d : x) {
            soma += (d - m) * (d - m);
            min = Math.min(min, d);
            max = Math.max(max, d);
        }
        double desvio = Math.sqrt(soma / (x.length - 1));
        double speedup = mediaSerial / m;
        double eficiencia = speedup / th;
        return String.format("%s,%s,%d,%d,%s,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f",
                alg, modo, th, tam, tipo, m, desvio, min, max, speedup, eficiencia);
    }
}
