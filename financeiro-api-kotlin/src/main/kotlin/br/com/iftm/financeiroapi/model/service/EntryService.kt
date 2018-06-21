package br.com.iftm.financeiroapi.model.service

import br.com.iftm.financeiroapi.model.domain.Entry
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
interface EntryService {

    fun save(entry: Entry): Entry

    fun delete(id: String)

    fun findAll(): List<Entry>

    fun findById(id: String): Entry?

    fun findByCategoryName(categoryName: String): List<Entry>

    fun generateEntries()

    fun sumEntriesByCategory(categoryName: String): BigDecimal
}