package com.cardapio_digital.utils;
import com.cardapio_digital.model.Prato;
import java.util.Comparator;

/**
 * Classe responsável por comparar dois objetos da classe {@link Prato}
 * com base em seus preços.
 * - Implementa a interface {@link Comparator}, que define uma forma personalizada
 *   de comparar objetos.
 */

public class ComparadorPreco implements Comparator<Prato> {

    /**
     * Método que compara dois objetos {@link Prato} com base em seus preços.
     *
     * - Retorna:
     *   → Valor negativo se o preço do primeiro prato for menor;
     *   → Zero se forem iguais;
     *   → Valor positivo se o preço do primeiro prato for maior.
     *
     * - Também trata casos onde um ou ambos os objetos são nulos, evitando exceções.
     *
     * Lógica de comparação:
     * - Utiliza {@link Integer#compare(int, int)} para simplificar e garantir
     *   uma comparação consistente e legível.
     *
     * @param p1 Primeiro prato a ser comparado.
     * @param p2 Segundo prato a ser comparado.
     * @return Resultado da comparação numérica entre os preços.
     */
    @Override
    public int compare(Prato p1, Prato p2) {
        if (p1 == null && p2 == null) return 0;
        if (p1 == null) return 1;
        if (p2 == null) return -1;

        return Integer.compare(p1.getPreco(), p2.getPreco());
    }
}
