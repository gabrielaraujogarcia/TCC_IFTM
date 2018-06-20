package br.com.iftm.financeiroapi.model.utils;

import br.com.iftm.financeiroapi.model.domain.Entry;

import java.math.BigDecimal;
import java.util.List;

public abstract class EntryUtil {

//    private static BigDecimal total = BigDecimal.ZERO;
    /*
     * Não foi possivel utilizar uma expressão lambda pois o escopo da variável total não é final e, sendo assim, não é
     * possível atribuir a variável total o resultado da operação total.add no corpo da expressão lambda após a operação
     * BigDecimal.add(e.getValue())
     */
    public static BigDecimal sumEntries(List<Entry> entries) {
        BigDecimal total = BigDecimal.ZERO;
        for(Entry e : entries) {
            if(e.getValue() != null) {
                total = total.add(e.getValue());
            }
        }
//        total = BigDecimal.ZERO;
//        entries.forEach(e -> {
//            if(e.getValue() != null) {
//                total = total.add(e.getValue());
//            }
//        });
        return total;
    }

}
