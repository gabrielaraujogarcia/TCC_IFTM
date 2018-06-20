package br.com.iftm.financeiroapi.model.extends

import br.com.iftm.financeiroapi.model.domain.Entry
import java.math.BigDecimal

fun List<Entry>.sumEntries(): BigDecimal {
    var total = BigDecimal.ZERO
    this.forEach { e -> total = total.add(e.value) }
    return total
}