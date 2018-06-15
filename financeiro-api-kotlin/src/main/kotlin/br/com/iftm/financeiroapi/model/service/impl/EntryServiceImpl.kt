package br.com.iftm.financeiroapi.model.service.impl

import br.com.iftm.financeiroapi.model.domain.Entry
import br.com.iftm.financeiroapi.model.exceptions.BusinessException
import br.com.iftm.financeiroapi.model.repository.EntryRepository
import br.com.iftm.financeiroapi.model.service.EntryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EntryServiceImpl : EntryService {

    @Autowired
    lateinit var entryRepository: EntryRepository

    override fun save(entry: Entry): Entry {
        val aux= entryRepository.findById(entry.id)
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

}