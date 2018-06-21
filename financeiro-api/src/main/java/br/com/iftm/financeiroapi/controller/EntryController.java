package br.com.iftm.financeiroapi.controller;

import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/entry")
public class EntryController {

    @Autowired
    @Qualifier("entryServiceImpl") private EntryService entryService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Entry entry) {
        try {
            Entry response = entryService.save(entry);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro no cadastro de lançamento financeiro. Motivo: " + e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        try {
            List<Entry> entries = entryService.findAll();
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro na listagem de lançamentos financeiro. Motivo: " + e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") String id) {
        try {
            Entry entry = entryService.findById(id);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro na consulta do lançamentos financeiro. Motivo: " + e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Object> findByCategoryName(@PathVariable("categoryName") String categoryName) {
        try {
            List<Entry> entries = entryService.findByCategoryName(categoryName);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro na consulta do lançamentos financeiro por nome da categoria. Motivo: " + e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{categoryName}/sum")
    public ResponseEntity<Object> sumEntriesByCategoryName(@PathVariable("categoryName") String categoryName) {
        try {
            BigDecimal total = entryService.sumEntriesByCategory(categoryName);
            return new ResponseEntity(total, HttpStatus.OK);
        } catch (Exception e) {
            String msg = "Erro na soma dos lançamentos financeiro por nome da categoria. Motivo: " + e.getMessage();
            return new ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        try {
            entryService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro na deleção do lançamento financeiro. Motivo: " + e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<Object> generateEntries() {
        try {
            entryService.generateEntries();
            return new ResponseEntity<>(HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro na carga de lançamentos financeiro. Motivo: " + e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
