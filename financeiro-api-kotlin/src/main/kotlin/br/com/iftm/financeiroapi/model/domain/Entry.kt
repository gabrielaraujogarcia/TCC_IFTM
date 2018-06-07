package br.com.iftm.financeiroapi.model.domain

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "entry")
data class Entry (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entry")
    var id: Long = 0,

    @Column(name = "description")
    var description: String? = "",

    @Column(name = "entryDate")
    var date: LocalDate = LocalDate .now(),

    @Column(name = "value")
    var value: BigDecimal = BigDecimal.ZERO,

    @OneToMany
    @JoinTable(
            name = "entryCategories",
            joinColumns = arrayOf(JoinColumn(name = "id_entry")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "id_category"))
    )
    var categories: List<Category> = emptyList()

)