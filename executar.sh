#!/bin/sh
cd "$(dirname "$0")"
mkdir -p bin
javac -d bin src/*.java || { echo "Erro ao compilar. Verifique se o JDK esta instalado."; exit 1; }
java -cp bin Main
echo "Concluido. Os arquivos resultados.csv e resumo.csv foram gerados nesta pasta."
