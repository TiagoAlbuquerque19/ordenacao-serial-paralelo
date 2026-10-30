# Ordenação Serial x Paralela em Java

Trabalho da disciplina Computação Paralela e Concorrente (Unifor).
Compara as versões serial e paralela do Merge Sort, Quick Sort e Bubble Sort.

Autor: Tiago Rodrigues Albuquerque (matrícula 2410375)

## Requisito

JDK 8 ou superior instalado (comandos `javac` e `java` disponíveis no terminal).
Para conferir: `java -version`

## Como executar

**Windows:** dar dois cliques em `executar.bat`.

**Linux / macOS:** no terminal, dentro da pasta do projeto:

```
./executar.sh
```

**Manualmente (qualquer sistema):** dentro da pasta do projeto:

```
javac -d bin src/*.java
java -cp bin Main
```

A execução completa leva poucos minutos (cerca de 3 minutos numa máquina de 2 núcleos).
Ao final são gerados na pasta do projeto os arquivos:

- `resultados.csv`: tempo de cada amostra (4 por configuração)
- `resumo.csv`: média, desvio padrão, mínimo, máximo, speedup e eficiência de cada configuração

Os resultados usados no relatório estão na pasta `resultados/`.

## Arquivos

- `src/MergeSort.java`: Merge Sort serial
- `src/MergeSortTask.java`: Merge Sort paralelo (Fork/Join)
- `src/QuickSort.java`: Quick Sort serial
- `src/QuickSortTask.java`: Quick Sort paralelo (Fork/Join)
- `src/BubbleSort.java`: Bubble Sort serial
- `src/BubbleSortParalelo.java`: Bubble Sort paralelo (threads + intercalação final)
- `src/GeradorDados.java`: gera os vetores de teste (aleatório, ordenado e inverso)
- `src/Main.java`: executa os testes e grava os CSV
