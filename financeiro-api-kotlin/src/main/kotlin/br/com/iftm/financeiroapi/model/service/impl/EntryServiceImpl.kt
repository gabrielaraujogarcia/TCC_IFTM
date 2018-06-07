package br.com.iftm.financeiroapi.model.service.impl

import br.com.iftm.financeiroapi.model.domain.Entry
import br.com.iftm.financeiroapi.model.repository.EntryRepository
import br.com.iftm.financeiroapi.model.service.EntryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EntryServiceImpl : EntryService {

    @Autowired
    lateinit var entryRepository: EntryRepository

    override fun save(entry: Entry): Entry  = entryRepository.save(entry)

    override fun delete(entry: Entry) = entryRepository.delete(entry)

    override fun findAll(): List<Entry> = entryRepository.findAll()

    override fun findById(id: Long): Entry = entryRepository.findById(id).orElse(null)

}