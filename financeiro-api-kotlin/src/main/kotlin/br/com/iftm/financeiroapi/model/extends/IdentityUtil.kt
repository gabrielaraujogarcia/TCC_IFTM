package br.com.iftm.financeiroapi.model.extends

import java.util.*

fun generateUUID(): String = UUID.randomUUID().toString().replace("-", "").toUpperCase()