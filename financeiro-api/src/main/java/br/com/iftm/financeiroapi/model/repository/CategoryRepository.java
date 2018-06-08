package br.com.iftm.financeiroapi.model.repository;


import br.com.iftm.financeiroapi.model.domain.Category;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface CategoryRepository  {

    void save(Category category);

    void update(Category category);

    void delete(String id);

    Category findById(String id);

    List<Category> findAll();

}
