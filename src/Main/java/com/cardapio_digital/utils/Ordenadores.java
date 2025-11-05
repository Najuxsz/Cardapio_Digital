package com.cardapio_digital.utils;

import com.cardapio_digital.model.Prato;

public class Ordenadores{
    public static void bubbleSort(Prato[] vetor){}

    public static void insertionSort(Prato[] vetor){}

    public static void quickSort(Prato[] vetor, int inicio, int fim, String criterioDeBusca){
        if(inicio < fim){
            int indicePivo = particionar(vetor, inicio, fim, criterioDeBusca);
            quickSort(vetor, inicio, indicePivo - 1, criterioDeBusca);
            quickSort(vetor, indicePivo + 1, fim, criterioDeBusca);
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
        switch (criterio.toLowerCase()) {
            case "nome":
                return a.getNome().compareToIgnoreCase(b.getNome());
            case "preco":
                return Integer.compare(a.getPreco(), b.getPreco());
            case "tempo":
                return Integer.compare(a.getTempoPreparo(), b.getTempoPreparo());
            default:
                return 0;
        }
    }

    private static void trocar(Prato[] vetor, int i, int j) {
        Prato temp = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temp;
    }

}