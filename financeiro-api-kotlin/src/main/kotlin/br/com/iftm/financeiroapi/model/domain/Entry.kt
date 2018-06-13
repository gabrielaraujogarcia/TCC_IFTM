package br.com.iftm.financeiroapi.model.domain

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.annotations.ApiModelProperty
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class Entry (

        @ApiModelProperty(hidden = true)
        var id: String = UUID.randomUUID().toString(),

        var description: String = "",

        var date: LocalDate = LocalDate .now(),

        var value: BigDecimal = BigDecimal.ZERO,

        var categories: List<Category> = emptyList()

)