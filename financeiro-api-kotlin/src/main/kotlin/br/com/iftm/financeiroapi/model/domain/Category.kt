package br.com.iftm.financeiroapi.model.domain

import io.swagger.annotations.ApiModelProperty
import kotlinx.serialization.Serializable
import java.util.*

data class Category (

        @ApiModelProperty(hidden = true)
        var id: String = UUID.randomUUID().toString(),

        var name: String = "",

        var color: String = ""

)