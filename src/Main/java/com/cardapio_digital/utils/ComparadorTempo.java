package com.cardapio_digital.utils;
import com.cardapio_digital.model.Prato;
import java.util.Comparator;

/**Comparator que ordena objetos pratos pelo tempo de preparo (ordem crescente).
 *Lança NullPointerException se algum dos pratos for null */
public class ComparadorTempo implements Comparator<Prato> {

    /**Compara dois pratos pelo tempo de preparo.
     * @return negativo se p1 < p2, zero se iguais, positivo se p1 > p2*/
    @Override
    public int compare(Prato p1, Prato p2) {
        if (p1 == null) throw new NullPointerException("O primeiro prato (p1) é nulo.");
        if (p2 == null) throw new NullPointerException("O segundo prato (p2) é nulo.");

        return Integer.compare(p1.getTempoPreparo(), p2.getTempoPreparo());
    }
}
