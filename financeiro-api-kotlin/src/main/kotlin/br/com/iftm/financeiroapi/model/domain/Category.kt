package br.com.iftm.financeiroapi.model.domain

import br.com.iftm.financeiroapi.model.extends.generateUUID
import io.swagger.annotations.ApiModelProperty

data class Category (
        var name: String = "",
        var color: String = ""
) {
    @ApiModelProperty(hidden = true)
    var id: String = generateUUID()
}