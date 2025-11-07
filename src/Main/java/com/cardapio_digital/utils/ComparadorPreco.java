package com.cardapio_digital.utils;

import com.cardapio_digital.model.Prato;
import java.util.Comparator;

public class ComparadorPreco implements Comparator<Prato> {

    @Override
    public int compare(Prato p1, Prato p2) {
        if (p1 == null && p2 == null) return 0;
        if (p1 == null) return 1;
        if (p2 == null) return -1;

        return Integer.compare(p1.getPreco(), p2.getPreco());
    }
}