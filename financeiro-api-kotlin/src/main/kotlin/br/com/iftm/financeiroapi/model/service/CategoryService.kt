package br.com.iftm.financeiroapi.model.service

import br.com.iftm.financeiroapi.model.domain.Category
import org.springframework.stereotype.Service

@Service
interface CategoryService {

    fun save(category: Category): Category

    fun delete(category: Category)

    fun findAll(): List<Category>

    fun findOne(id: Long): Category

}