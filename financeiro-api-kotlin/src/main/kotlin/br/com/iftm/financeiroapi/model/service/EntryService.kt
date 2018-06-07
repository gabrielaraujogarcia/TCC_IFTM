package br.com.iftm.financeiroapi.model.service

import br.com.iftm.financeiroapi.model.domain.Entry
import org.springframework.stereotype.Service

@Service
interface EntryService {

    fun save(entry: Entry): Entry

    fun delete(entry: Entry)

    fun findAll(): List<Entry>

    fun findById(id: Long): Entry
}