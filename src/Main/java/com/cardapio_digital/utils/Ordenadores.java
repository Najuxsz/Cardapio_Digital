package com.cardapio_digital.utils;
import com.cardapio_digital.model.Prato;
import java.util.Comparator;
import java.util.List;

/** Classe utilitária responsável por implementar diferentes algoritmos de ordenação
 * para vetores de objetos {@link Prato}.
 * - Cada método implementa um algoritmo clássico (BubbleSort, InsertionSort, QuickSort).*/
public class Ordenadores {

    /**
     * BubbleSort - Algoritmo de ordenação simples baseado em trocas de elementos adjacentes.
     * - Percorre o vetor várias vezes, comparando pares de elementos consecutivos.
     * - Quando dois elementos estão fora de ordem, eles são trocados de posição.
     * - Após cada passagem completa, o maior elemento “sobe” para o final do vetor.
     * @param lista Vetor de pratos a ser ordenado.
     * @param criterio Critério de ordenação ("nome", "preco"/"preço", "tempo").
     */
    public static void bubbleSort(List<Prato> lista, String criterio) {
        if (lista == null || lista.isEmpty()) {
            return;
        }

        int contTrocas = 0;
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = 0; j < lista.size() - i - 1; j++) {
                if (comparar(lista.get(j), lista.get(j + 1), criterio) > 0) {
                    Prato temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                    contTrocas++;
                }
            }
        }
        System.out.println("BubbleSort - Total de trocas: " + contTrocas);
    }
    /**
     * InsertionSort - Algoritmo de ordenação por inserção.
     * - Percorre o vetor e insere cada elemento na posição correta em relação
     *   aos elementos anteriores.
     * Características:
     * @param lista Vetor de pratos a ordenar.
     * @param criterio Objeto que define a lógica de comparação entre dois pratos.
     */
    public static void insertionSort(List<Prato> lista, String criterio) {
        if (lista == null || lista.isEmpty()) {
            return;
        }

        int contMovimentos = 0;
        for (int i = 1; i < lista.size(); i++) {
            Prato key = lista.get(i);
            int j = i - 1;

            while (j >= 0 && comparar(lista.get(j), key, criterio) > 0) {
                lista.set(j + 1, lista.get(j));
                contMovimentos++;
                j--;
            }
            lista.set(j + 1, key);
        }
        System.out.println("InsertionSort - Total de movimentos: " + contMovimentos);
    }
    /** O QuickSort é um algoritmo de ordenação baseado na estratégia de dividir para conquistar.
     No nosso QuickSort, quem escolhe o pivô é o método auxiliar partition,
     O Partition reorganiza o vetor de forma que:
     - todos os elementos menores (ou iguais) ao pivô ficam à esquerda;
     - todos os elementos maiores ficam à direita.
     Essa reorganização é feita pelo método auxiliar partition().
     Depois disso, o QuickSort chama a si próprio recursivamente para ordenar a parte da esquerda
     e a parte da direita. Por ser recursivo, o método partition() é chamado várias vezes durante a execução. */
     public static void quickSort(List<Prato> lista, int low, int high, String criterio) {
        if (lista == null || lista.isEmpty() || low >= high) {
            return;
        }

        if (low < high) {
            int pi = partition(lista, low, high, criterio);
            quickSort(lista, low, pi - 1, criterio);
            quickSort(lista, pi + 1, high, criterio);
        }
    }

    private static int contMovimentosQuick = 0;
    public static void resetContMovimentosQuick() {
        contMovimentosQuick = 0;
    }
    public static int getContMovimentosQuick() {
        return contMovimentosQuick;
    }
    /**Método auxiliar do QuickSort responsável por particionar o vetor.
     * - Define um pivô (neste caso, o último elemento do subvetor).
     * - Reorganiza o vetor de forma que todos os elementos menores ou iguais ao pivô
     *   fiquem à esquerda e os maiores à direita.
     * - Retorna o índice final do pivô, que será usado nas chamadas recursivas.
     * @param lista Vetor de pratos.
     * @param low Índice inicial.
     * @param high Índice final (onde está o pivô).
     * @param criterio Critério de ordenação.
     * @return Índice final do pivô após o particionamento.*/
    private static int partition(List<Prato> lista, int low, int high, String criterio) {
        Prato pivot = lista.get(high); //escolhe ultimo elemento como pivo
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparar(lista.get(j), pivot, criterio) <= 0) {
                i++;
                Prato temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
                contMovimentosQuick++; //cada troca conta como uma movimentação
            }
        }

        Prato temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(high));
        lista.set(high, temp);
        contMovimentosQuick++; // troca final do pivô
        return i + 1;
    }

    /**
     * Método genérico de comparação entre dois pratos com base em um critério textual.
     * - Permite que os métodos de ordenação usem a mesma lógica,
     *   evitando duplicação de código.
     * Critérios possíveis:
     * - "nome": compara alfabeticamente (ignorando maiúsculas e minúsculas).
     * - "preco" ou "preço": compara valores inteiros do preço.
     * - "tempo": compara tempos de preparo.
     * @param a Primeiro prato.
     * @param b Segundo prato.
     * @param criterio Critério de comparação.
     * @return Resultado numérico da comparação.
     */
    private static int comparar(Prato a, Prato b, String criterio) {
        if (a == null || b == null) {
            return 0;
        }

        return switch (criterio) {
            case "Nome" -> a.getNome().compareToIgnoreCase(b.getNome());
            case "Preço" -> Integer.compare(a.getPreco(), b.getPreco());
            case "Tempo de Preparo" -> Integer.compare(a.getTempoPreparo(), b.getTempoPreparo());
            default -> 0;
        };
    }
    /**
     * Método auxiliar que troca a posição de dois elementos em um vetor de pratos.
     * - Verifica se os índices são válidos antes da troca.
     * - Operação básica utilizada em quase todos os algoritmos de ordenação.
     * @param vetor Vetor de pratos.
     * @param i Índice do primeiro elemento.
     * @param j Índice do segundo elemento.
     */
    private static void trocar(Prato[] vetor, int i, int j) {
        if (i < 0 || j < 0 || i >= vetor.length || j >= vetor.length) {
            return;
        }
        Prato temp = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temp;
    }
}