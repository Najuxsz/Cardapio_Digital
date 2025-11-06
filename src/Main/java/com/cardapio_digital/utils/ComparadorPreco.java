package com.cardapio_digital.utils;

import java.util.Comparator;
import com.cardapio_digital.model.Prato;

public class ComparadorPreco implements Comparator<Prato> {

    @Override
    public int compare(Prato p1, Prato p2) {
        // Retorna negativo se p1 < p2, positivo se p1 > p2, zero se iguais
        return Double.compare(p1.getPreco(), p2.getPreco());
    }
}
