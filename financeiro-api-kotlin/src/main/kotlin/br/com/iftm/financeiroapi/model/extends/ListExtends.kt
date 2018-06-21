package br.com.iftm.financeiroapi.model.extends

import br.com.iftm.financeiroapi.model.domain.Entry
import java.math.BigDecimal
import java.util.*

fun List<Entry>.sumEntries(): BigDecimal {
    var total = BigDecimal.ZERO
    this.forEach { e -> total = total.add(e.value) }
    return total
}
