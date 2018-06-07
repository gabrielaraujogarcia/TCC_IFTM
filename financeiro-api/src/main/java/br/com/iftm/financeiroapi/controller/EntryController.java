package br.com.iftm.financeiroapi.controller;

import br.com.iftm.financeiroapi.model.domain.Category;
import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.service.CategoryService;
import br.com.iftm.financeiroapi.model.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entry")
public class EntryController {

    @Autowired
    private EntryService entryService;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            entryService.delete(new Entry(id));
            return new ResponseEntity<>(HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro na deleção do lançamento financeiro. Motivo: " + e.getMessage();
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
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        try {
            Entry entry = entryService.findById(id);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro na consulta do lançamentos financeiro. Motivo: " + e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}