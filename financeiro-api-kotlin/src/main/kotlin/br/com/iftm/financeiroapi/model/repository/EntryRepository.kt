package br.com.iftm.financeiroapi.model.repository

import br.com.iftm.financeiroapi.model.domain.Entry
import org.springframework.stereotype.Component

@Component
interface EntryRepository {

    fun save(entry: Entry)

    fun update(entry: Entry)

    fun delete(id: String)

    fun findById(id: String): Entry?

    fun findAll(): List<Entry>

}





