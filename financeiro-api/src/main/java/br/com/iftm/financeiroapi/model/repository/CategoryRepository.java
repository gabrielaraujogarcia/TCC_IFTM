package br.com.iftm.financeiroapi.model.repository;


import br.com.iftm.financeiroapi.model.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
