package br.com.iftm.financeiroapi.controller

import br.com.iftm.financeiroapi.model.domain.Entry
import br.com.iftm.financeiroapi.model.service.EntryService
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
            return ResponseEntity<Any>(response, HttpStatus.OK)
        } catch (e: Exception) {
            val msg = "Erro no cadastro de lançamento financeiro. Motivo: " + e.message
            return ResponseEntity<Any>(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {
        try {
            entryService.delete(Entry(id))
            return ResponseEntity<Any>(HttpStatus.OK)
        } catch (e: Exception) {
            val msg = "Erro na deleção do lançamento financeiro. Motivo: " + e.message
            return ResponseEntity<Any>(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @GetMapping
    fun findAll(): ResponseEntity<Any> {
        try {
            val entries = entryService.findAll()
            return ResponseEntity<Any>(entries, HttpStatus.OK)
        } catch (e: Exception) {
            val msg = "Erro na listagem de lançamentos financeiro. Motivo: " + e.message
            return ResponseEntity<Any>(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        try {
            val entry = entryService.findById(id)
            return ResponseEntity<Any>(entry, HttpStatus.OK)
        } catch (e: Exception) {
            val msg = "Erro na consulta do lançamentos financeiro. Motivo: " + e.message
            return ResponseEntity<Any>(msg, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

}