package br.com.iftm.financeiroapi.model.service.impl;

import br.com.iftm.financeiroapi.model.domain.Category;
import br.com.iftm.financeiroapi.model.repository.CategoryRepository;
import br.com.iftm.financeiroapi.model.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        categoryRepository.save(category);
        return category;
    }

    public void delete(String id){
        categoryRepository.delete(id);
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category findOne(Long id){
//        return categoryRepository.findById(id).orElse(null);
        return null;
    }

}
