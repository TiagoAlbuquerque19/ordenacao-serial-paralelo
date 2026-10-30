@echo off
cd /d "%~dp0"
if not exist bin mkdir bin
javac -d bin src\*.java
if errorlevel 1 (
    echo Erro ao compilar. Verifique se o JDK esta instalado.
    pause
    exit /b
)
java -cp bin Main
echo.
echo Concluido. Os arquivos resultados.csv e resumo.csv foram gerados nesta pasta.
pause
