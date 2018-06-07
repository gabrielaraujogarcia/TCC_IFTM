package br.com.iftm.financeiroapi.model.domain

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity(name = "category")
data class Category (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    var id: Long = 0,

    @Column(name = "name")
    @get: NotBlank
    var name: String = "",

    @Column(name = "color")
    @get: NotBlank
    var color: String = ""

)