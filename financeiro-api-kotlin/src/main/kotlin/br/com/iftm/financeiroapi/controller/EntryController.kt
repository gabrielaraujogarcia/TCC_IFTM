package br.com.iftm.financeiroapi.controller

import br.com.iftm.financeiroapi.model.domain.Entry
import br.com.iftm.financeiroapi.model.service.EntryService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/entry")
class EntryController (val entryService: EntryService) {

    @PostMapping
    fun save(@RequestBody entry: Entry): ResponseEntity<Any> {
        try {
            val response = entryService.save(entry)
            return ResponseEntity(response, HttpStatus.OK)
        } catch (e: Exception) {
            val msg = "Erro no cadastro de lançamento financeiro. Moti" +
                    "vo: ${e.message}"
            return ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping
    fun findAll(): ResponseEntity<Any> {
        try {
            val entries = entryService.findAll()
            return ResponseEntity(entries, HttpStatus.OK)
        } catch (e: Exception) {
            val msg = "Erro na listagem de lançamentos financeiro. Motivo: ${e.message}"
            return ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): ResponseEntity<Any> {
        try {
            val entry = entryService.findById(id)
            return ResponseEntity(entry, HttpStatus.OK)
        } catch (e: Exception) {
            val msg = "Erro na consulta do lançamentos financeiro. Motivo: ${e.message}"
            return ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/category/{categoryName}")
    fun findByCategoryName(@PathVariable("categoryName") categoryName: String): ResponseEntity<Any> {
        try {
            val entries = entryService.findByCategoryName(categoryName)
            return ResponseEntity(entries, HttpStatus.OK)
        } catch (e: Exception) {
            val msg = "Erro na consulta do lançamentos financeiro por nome da categoria. Motivo: ${e.message}"
            return ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{categoryName}/sum")
    fun sumEntriesByCategoryName(@PathVariable("categoryName") categoryName: String): ResponseEntity<Any> {
        try {
            val total = entryService.sumEntriesByCategory(categoryName)
            return ResponseEntity(total, HttpStatus.OK)
        } catch (e: Exception) {
            val msg = "Erro na soma dos lançamentos financeiro por nome da categoria. Motivo: ${e.message}"
            return ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String): ResponseEntity<Any> {
        try {
            entryService.delete(id)
            return ResponseEntity(HttpStatus.OK)
        } catch (e: Exception) {
            val msg = "Erro na deleção do lançamento financeiro. Motivo: ${e.message}"
            return ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}