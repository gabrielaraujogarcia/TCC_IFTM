package br.com.iftm.financeiroapi.model.service.impl

import br.com.iftm.financeiroapi.model.domain.Category
import br.com.iftm.financeiroapi.model.repository.CategoryRepository
import br.com.iftm.financeiroapi.model.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CategoryServiceImpl : CategoryService {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    override fun save(category: Category): Category = categoryRepository.save(category)

    override fun delete(category: Category) = categoryRepository.delete(category)

    override fun findAll(): List<Category> = categoryRepository.findAll().toList()

    override fun findOne(id: Long): Category = categoryRepository.findById(id).orElse(null)
}