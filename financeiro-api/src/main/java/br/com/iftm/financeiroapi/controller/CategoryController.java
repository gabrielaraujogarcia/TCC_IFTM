package br.com.iftm.financeiroapi.controller;

import br.com.iftm.financeiroapi.model.domain.Category;
import br.com.iftm.financeiroapi.model.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Category category) {
        try {
            Category response = categoryService.save(category);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro no cadastro de categoria. Motivo: " + e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            categoryService.delete(new Category(id));
            return new ResponseEntity<>(HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro na deleção de categoria. Motivo: " + e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        try {
            List<Category> categories = categoryService.findAll();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro ao listar as categoria. Motivo: " + e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        try {
            Category category = categoryService.findOne(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }  catch(Exception e) {
            String msg =  "Erro na consulta da categoria. Motivo: " + e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
