package br.com.iftm.financeiroapi.model.repository

import br.com.iftm.financeiroapi.model.domain.Entry
import org.springframework.data.jpa.repository.JpaRepository

interface EntryRepository : JpaRepository<Entry, Long>

