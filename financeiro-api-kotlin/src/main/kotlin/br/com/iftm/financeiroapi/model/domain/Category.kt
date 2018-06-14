package br.com.iftm.financeiroapi.model.domain

import io.swagger.annotations.ApiModelProperty
import java.util.*

data class Category (

        @ApiModelProperty(hidden = true)
        var id: String = UUID.randomUUID().toString(),

        var name: String = "",

        var color: String = ""


)
//{
//    override fun toString(): String {
//        return "{\"id\":\"$id\", \"name\":\"$name\", \"color\":\"$color\"}"
//    }
//}