package com.cardapio_digital.utils;
import com.cardapio_digital.model.Prato;
import java.util.Comparator;

public class Ordenadores{

    public static void bubbleSort(Prato[] vetor) {
        // BubbleSort compara elementos adjacentes e os troca se estiverem fora de ordem.
        // Ele percorre o vetor várias vezes até tudo estar ordenado.
        for (int i = 0; i < vetor.length - 1; i++) {
            for (int j = 0; j < vetor.length - i - 1; j++) {
                // Aqui você pode escolher o critério de comparação (nome, preço, tempo)
                if (vetor[j].getPreco() > vetor[j + 1].getPreco()) {
                    // Troca de posição
                    trocar(vetor, j, j + 1);
                }
            }
        }
    }


    /**
     * Insert sort: Ordena o vetor de pratos com base em um comparador recebido.
     *
     * <p>Esse método percorre o vetor da esquerda para a direita e insere cada
     * elemento na posição correta, deslocando os elementos maiores à direita.
     * É um algoritmo simples, estável e eficiente para vetores pequenos.</p>
     *
     * @param pratos vetor de pratos a ser ordenado
     * @param comp comparador usado para definir o critério (nome, preço, tempo)
     */
    public static void insertionSort(Prato[] pratos, Comparator<Prato> comp) {

        // Percorre o vetor começando do segundo elemento
        for (int i = 1; i < pratos.length; i++) {

            // Guarda o prato atual que será inserido na posição correta
            Prato chave = pratos[i];
            int j = i - 1;

            // Move os elementos maiores (segundo o comparador) uma posição à frente
            while (j >= 0 && comp.compare(pratos[j], chave) > 0) {
                pratos[j + 1] = pratos[j];
                j--;
            }

            // Insere a chave (o prato atual) na posição correta
            pratos[j + 1] = chave;
        }

        //Dica para teste rápido no consol
        System.out.println("\n🔹 Vetor ordenado pelo InsertionSort:");
        for (Prato p : pratos) {
            System.out.println("🍽️ " + p.getNome() + " | R$" + p.getPreco() + " | " + p.getTempoPreparo() + "min");
        }
    }



    public static void quickSort(Prato[] vetor, int inicio, int fim, String criterioDeBusca){
        // QuickSort usa o conceito de "pivô" para dividir o vetor e ordenar recursivamente.
        // O pivô serve como referência: tudo menor fica à esquerda, tudo maior à direita.
        if(inicio < fim){
            int indicePivo = particionar(vetor, inicio, fim, criterioDeBusca);

            // Exemplo de debug: mostrar o pivô escolhido durante a ordenação
            System.out.println("Pivô: " + vetor[indicePivo].getNome() + " | Critério: " + criterioDeBusca);

            quickSort(vetor, inicio, indicePivo - 1, criterioDeBusca);
            quickSort(vetor, indicePivo + 1, fim, criterioDeBusca);
        }
    }

    private static int particionar(Prato[] vetor, int inicio, int fim, String criterio) {
        Prato pivo = vetor[fim];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            // Se o prato atual for "menor" que o pivô (baseado no critério), troca de lugar
            if (comparar(vetor[j], pivo, criterio) <= 0) {
                i++;
                trocar(vetor, i, j);
            }
        }

        // Coloca o pivô na posição correta
        trocar(vetor, i + 1, fim);
        return i + 1;
    }

    private static int comparar(Prato a, Prato b, String criterio) {
        // Aqui podemos mudar facilmente o critério de ordenação.
        switch (criterio.toLowerCase()) {
            case "nome":
                return a.getNome().compareToIgnoreCase(b.getNome());
            case "preco":
                // Se o preço for double, mude para Double.compare(a.getPreco(), b.getPreco());
                return Integer.compare(a.getPreco(), b.getPreco());
            case "tempo":
                return Integer.compare(a.getTempoPreparo(), b.getTempoPreparo());
            default:
                return 0;
        }
    }

    private static void trocar(Prato[] vetor, int i, int j) {
        // Troca simples de posição entre dois pratos
        Prato temp = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temp;
    }

}