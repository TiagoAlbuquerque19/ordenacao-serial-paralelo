import java.util.Random;

public class GeradorDados {

    public static int[] gerar(int tamanho, String tipo, int maxValor, long semente) {
        int[] v = new int[tamanho];
        Random r = new Random(semente);
        for (int i = 0; i < tamanho; i++) v[i] = r.nextInt(maxValor + 1);
        if (tipo.equals("ordenado") || tipo.equals("inverso")) {
            java.util.Arrays.sort(v);
            if (tipo.equals("inverso")) {
                for (int i = 0, j = tamanho - 1; i < j; i++, j--) {
                    int t = v[i];
                    v[i] = v[j];
                    v[j] = t;
                }
            }
        }
        return v;
    }
}
