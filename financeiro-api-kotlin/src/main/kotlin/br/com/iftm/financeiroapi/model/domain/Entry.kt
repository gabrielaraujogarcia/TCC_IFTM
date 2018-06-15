package br.com.iftm.financeiroapi.model.domain

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal
import java.util.*


data class Entry (

        @ApiModelProperty(hidden = true)
        var id: String = UUID.randomUUID().toString(),

        var description: String = "",

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        var date: Date = Date(),

        var value: BigDecimal = BigDecimal.ZERO,

        var categories: Set<Category> = emptySet()

)