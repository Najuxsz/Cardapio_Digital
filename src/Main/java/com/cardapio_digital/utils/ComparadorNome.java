package com.cardapio_digital.utils;

import com.cardapio_digital.model.Prato;
import java.util.Comparator;

//Comparator que ordena objeto prato pelo nome em ordem alfabética.
public class ComparadorNome implements Comparator<Prato> {

    @Override
    public int compare(Prato p1, Prato p2) {
        if (p1 == null) throw new NullPointerException("O primeiro prato (p1) é nulo.");
        if (p2 == null) throw new NullPointerException("O segundo prato (p2) é nulo.");

        // Comparação alfabética sem diferenciar maiúsculas/minúsculas
        return p1.getNome().compareToIgnoreCase(p2.getNome());
    }
}
