package br.com.iftm.financeiroapi.model.domain

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*


data class Entry (

        @ApiModelProperty(hidden = true)
        var id: String = UUID.randomUUID().toString(),

        var description: String = "",

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        var date: Date = Date(),

        var value: BigDecimal = BigDecimal.ZERO,

        var categories: List<Category> = emptyList()

)
//{
//    override fun toString(): String {
//        val jsonCategories: StringBuilder = StringBuilder("[")
//        categories.forEach{c -> jsonCategories.append(c.toString())}
//        jsonCategories.append("]")
//        return "{\"id\":\"$id\", \"description\":\"$description\", \"date\":\"$date\", \"value\":\"$value\", " +
//                "\"categories\":$jsonCategories}"
//    }
//}