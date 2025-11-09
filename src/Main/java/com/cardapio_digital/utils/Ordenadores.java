package com.cardapio_digital.utils;
import com.cardapio_digital.model.Prato;
import java.util.Comparator;

/**
 * Classe utilitária responsável por implementar diferentes algoritmos de ordenação
 * para vetores de objetos {@link Prato}.
 *
 * - Cada método implementa um algoritmo clássico (BubbleSort, InsertionSort, QuickSort).
 */
public class Ordenadores {

    /**
     * BubbleSort - Algoritmo de ordenação simples baseado em trocas de elementos adjacentes.
     *
     * - Percorre o vetor várias vezes, comparando pares de elementos consecutivos.
     * - Quando dois elementos estão fora de ordem, eles são trocados de posição.
     * - Após cada passagem completa, o maior elemento “sobe” para o final do vetor.
     *
     * @param vetor Vetor de pratos a ser ordenado.
     * @param criterio Critério de ordenação ("nome", "preco"/"preço", "tempo").
     */
    public static void bubbleSort(Prato[] vetor, String criterio) {
        if (vetor == null || vetor.length <= 1) { // Verifica se o vetor é nulo ou se tem 0 ou 1 elemento
            return;// sai do método sem ordenar nadA
        }

        for (int i = 0; i < vetor.length - 1; i++) {
            boolean trocou = false;

            for (int j = 0; j < vetor.length - i - 1; j++) { //Ganrantir comparação em pares
                if (comparar(vetor[j], vetor[j + 1], criterio) > 0) {
                    trocar(vetor, j, j + 1);
                    trocou = true;
                }
            }
            // Flag de otimização: se não houve trocas, já está ordenado
            if (!trocou) break;
        }
    }

    /**
     * InsertionSort - Algoritmo de ordenação por inserção.
     *
     * - Percorre o vetor e insere cada elemento na posição correta em relação
     *   aos elementos anteriores.
     * Características:
     *
     * @param pratos Vetor de pratos a ordenar.
     * @param comp Objeto que define a lógica de comparação entre dois pratos.
     */
    public static void insertionSort(Prato[] pratos, Comparator<Prato> comp) {
        if (pratos == null || pratos.length <= 1 || comp == null) { // Verifica se o vetor é nulo ou
            return;
        }

        for (int i = 1; i < pratos.length; i++) {
            Prato chave = pratos[i];
            int j = i - 1;

            // Move os elementos maiores que a chave uma posição à frente
            while (j >= 0 && comp.compare(pratos[j], chave) > 0) {
                pratos[j + 1] = pratos[j];
                j--;
            }

            // Insere a chave na posição correta
            pratos[j + 1] = chave;
        }
    }

    /**
     * QuickSort - Algoritmo de ordenação baseado em "divisão e conquista".
     *
     * - Escolhe um elemento como pivô e o posiciona em seu lugar correto.
     * - Todos os elementos menores que o pivô são colocados à esquerda;
     *   todos os maiores, à direita.
     * - O processo é repetido recursivamente para as subpartes.
     *
     * Complexidade:
     * - Caso médio: O(n log n)
     * - Pior caso (vetor já ordenado ou pivô mal escolhido): O(n²)
     *
     * Características:
     * - Não é estável.
     * - Muito eficiente para grandes quantidades de dados.
     *
     * @param vetor Vetor de pratos a ser ordenado.
     * @param inicio Índice inicial do subvetor.
     * @param fim Índice final do subvetor.
     * @param criterio Critério de ordenação ("nome", "preco"/"preço", "tempo").
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

    /**
     * Método auxiliar do QuickSort responsável por particionar o vetor.
     *
     * - Define um pivô (neste caso, o último elemento do subvetor).
     * - Reorganiza o vetor de forma que todos os elementos menores ou iguais ao pivô
     *   fiquem à esquerda e os maiores à direita.
     * - Retorna o índice final do pivô, que será usado nas chamadas recursivas.
     *
     * @param vetor Vetor de pratos.
     * @param inicio Índice inicial.
     * @param fim Índice final (onde está o pivô).
     * @param criterio Critério de ordenação.
     * @return Índice final do pivô após o particionamento.
     */
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

    /**
     * Método genérico de comparação entre dois pratos com base em um critério textual.
     *
     * - Permite que os métodos de ordenação usem a mesma lógica,
     *   evitando duplicação de código.
     *
     * Critérios possíveis:
     * - "nome": compara alfabeticamente (ignorando maiúsculas e minúsculas).
     * - "preco" ou "preço": compara valores inteiros do preço.
     * - "tempo": compara tempos de preparo.
     *
     * @param a Primeiro prato.
     * @param b Segundo prato.
     * @param criterio Critério de comparação.
     * @return Resultado numérico da comparação.
     */
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

    /**
     * Método auxiliar que troca a posição de dois elementos em um vetor de pratos.
     *
     * - Verifica se os índices são válidos antes da troca.
     * - Operação básica utilizada em quase todos os algoritmos de ordenação.
     *
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
