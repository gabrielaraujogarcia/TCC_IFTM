package br.com.iftm.financeiroapi.model.service;

import br.com.iftm.financeiroapi.model.domain.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    Category save(Category category);

    void delete(Category category);

    List<Category> findAll();

    Category findOne(Long id);

}
