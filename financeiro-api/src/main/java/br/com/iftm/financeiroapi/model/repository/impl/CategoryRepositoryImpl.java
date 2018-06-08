package br.com.iftm.financeiroapi.model.repository.impl;

import br.com.iftm.financeiroapi.model.domain.Category;
import br.com.iftm.financeiroapi.model.exceptions.BusinessException;
import br.com.iftm.financeiroapi.model.repository.CategoryRepository;
import br.com.iftm.financeiroapi.model.utils.FileUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String ENTRIES_FILE_PATH = "src/main/resources/database/categories.txt";

    @Override
    public void save(Category category) {
        try {
            FileUtil.writeLine(ENTRIES_FILE_PATH, category);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BusinessException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Category category) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Category findById(String id) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        return null;
    }

}
