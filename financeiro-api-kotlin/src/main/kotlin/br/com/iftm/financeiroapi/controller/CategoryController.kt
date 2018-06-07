package br.com.iftm.financeiroapi.controller

import br.com.iftm.financeiroapi.model.domain.Category
import br.com.iftm.financeiroapi.model.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/category")
class CategoryController (val categoryService: CategoryService) {

    @PostMapping
    fun save(@RequestBody category : Category) : ResponseEntity<Any> {
        try {
            return ResponseEntity.ok().body(categoryService.save(category))
        } catch (e: Exception) {
            val msg = "Erro no cadastro de categoria. Motivo: " + e.message
            return ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping
    fun delete(@RequestBody category : Category) : ResponseEntity<Any> {
        try {
            categoryService.delete(category)
            return ResponseEntity.ok().build()
        } catch (e: Exception) {
            val msg = "Erro na delecção da categoria. Motivo: " + e.message
            return ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping
    fun findAll() : ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(categoryService.findAll())
        } catch (e: Exception) {
            val msg = "Erro ao listar as categoria. Motivo: " + e.message
            return ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(categoryService.findOne(id))
        } catch (e: Exception) {
            val msg = "Erro na consulta da categoria. Motivo: " + e.message
            return ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}