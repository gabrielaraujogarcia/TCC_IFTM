package br.com.iftm.financeiroapi.model.service.impl

import br.com.iftm.financeiroapi.model.domain.Category
import br.com.iftm.financeiroapi.model.domain.Entry
import br.com.iftm.financeiroapi.model.exceptions.BusinessException
import br.com.iftm.financeiroapi.model.extends.sumEntries
import br.com.iftm.financeiroapi.model.repository.EntryRepository
import br.com.iftm.financeiroapi.model.service.EntryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*
import kotlin.collections.HashSet

@Component
class EntryServiceWithoutLogImpl : EntryService {

    private val DEFAULT_CATEGORY_NAME = "Categoria_"
    private val DEFAULT_ENTRY_NAME = "Lançamento_"

    @Autowired
    lateinit var entryRepository: EntryRepository

    override fun save(entry: Entry): Entry {
        val aux = entryRepository.findById(entry.id)
        if (aux != null) {
            entryRepository.update(entry)
        } else {
            entryRepository.save(entry)
        }
        return entry
    }

    @Throws(BusinessException::class)
    override fun findById(id: String): Entry =
            entryRepository.findById(id) ?: throw BusinessException("Registro não encontrado!")

    override fun findAll(): List<Entry> = entryRepository.findAll()

    override fun delete(id: String) = entryRepository.delete(id)

    override fun findByCategoryName(categoryName: String): List<Entry> =
            findAll().filter { e -> e.categories.filter{ c -> c.name.contains(categoryName) }.count() > 0 }

    override fun generateEntries() {
        val range = 30000
        for(i in 0..range) {
            val categories = HashSet<Category>()
            var categoryCode = randomNumber()
            categories.add(Category(DEFAULT_CATEGORY_NAME + categoryCode, "#FFFFF" + categoryCode))
            categoryCode = randomNumber()
            categories.add(Category(DEFAULT_CATEGORY_NAME + categoryCode, "#00000" + categoryCode))
            val entry = Entry(DEFAULT_ENTRY_NAME + i, Date(),
                    BigDecimal.TEN.multiply(BigDecimal.valueOf(randomNumber().toLong())), categories)
            entryRepository.save(entry)
        }
    }

    override fun sumEntriesByCategory(categoryName: String): BigDecimal = findByCategoryName(categoryName).sumEntries()

    private fun randomNumber(): Int = Random().nextInt((5 - 1) + 1)

}