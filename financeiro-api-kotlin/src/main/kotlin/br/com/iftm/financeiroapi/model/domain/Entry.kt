package br.com.iftm.financeiroapi.model.domain

import br.com.iftm.financeiroapi.model.extends.generateUUID
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal
import java.util.*


data class Entry (
        var description: String = "",
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        var date: Date = Date(),
        var value: BigDecimal = BigDecimal.ZERO,
        var categories: Set<Category> = emptySet()
) {
    @ApiModelProperty(hidden = true)
    var id: String = generateUUID()
}
