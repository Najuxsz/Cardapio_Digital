package com.cardapio_digital.utils;

import com.cardapio_digital.model.Prato;
import java.util.Comparator;

public class Ordenadores {

    /**
     * BubbleSort - Algoritmo de ordenação por comparação de elementos adjacentes
     * Complexidade: O(n²)
     */
    public static void bubbleSort(Prato[] vetor, String criterio) {
        if (vetor == null || vetor.length <= 1) {
            return;
        }

        for (int i = 0; i < vetor.length - 1; i++) {
            boolean trocou = false;
            for (int j = 0; j < vetor.length - i - 1; j++) {
                if (comparar(vetor[j], vetor[j + 1], criterio) > 0) {
                    trocar(vetor, j, j + 1);
                    trocou = true;
                }
            }
            // Otimização: se não houve troca, o vetor já está ordenado
            if (!trocou) break;
        }
    }

    /**
     * InsertionSort - Ordena inserindo cada elemento na posição correta
     * Complexidade: O(n²) pior caso, O(n) melhor caso
     * Estável e eficiente para vetores pequenos
     */
    public static void insertionSort(Prato[] pratos, Comparator<Prato> comp) {
        if (pratos == null || pratos.length <= 1 || comp == null) {
            return;
        }

        for (int i = 1; i < pratos.length; i++) {
            Prato chave = pratos[i];
            int j = i - 1;

            while (j >= 0 && comp.compare(pratos[j], chave) > 0) {
                pratos[j + 1] = pratos[j];
                j--;
            }
            pratos[j + 1] = chave;
        }
    }

    /**
     * QuickSort - Algoritmo de ordenação por divisão e conquista
     * Complexidade: O(n log n) médio caso, O(n²) pior caso
     */
    public static void quickSort(Prato[] vetor, int inicio, int fim, String criterio) {
        if (vetor == null || inicio >= fim || inicio < 0 || fim >= vetor.length) {
            return;
        }

        if (inicio < fim) {
            int indicePivo = particionar(vetor, inicio, fim, criterio);
            quickSort(vetor, inicio, indicePivo - 1, criterio);
            quickSort(vetor, indicePivo + 1, fim, criterio);
        }
    }

    private static int particionar(Prato[] vetor, int inicio, int fim, String criterio) {
        Prato pivo = vetor[fim];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (comparar(vetor[j], pivo, criterio) <= 0) {
                i++;
                trocar(vetor, i, j);
            }
        }

        trocar(vetor, i + 1, fim);
        return i + 1;
    }

    private static int comparar(Prato a, Prato b, String criterio) {
        if (a == null && b == null) return 0;
        if (a == null) return 1;
        if (b == null) return -1;

        return switch (criterio.toLowerCase()) {
            case "nome" -> a.getNome().compareToIgnoreCase(b.getNome());
            case "preco", "preço" -> Integer.compare(a.getPreco(), b.getPreco());
            case "tempo" -> Integer.compare(a.getTempoPreparo(), b.getTempoPreparo());
            default -> 0;
        };
    }

    private static void trocar(Prato[] vetor, int i, int j) {
        if (i < 0 || j < 0 || i >= vetor.length || j >= vetor.length) {
            return;
        }
        Prato temp = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temp;
    }
}