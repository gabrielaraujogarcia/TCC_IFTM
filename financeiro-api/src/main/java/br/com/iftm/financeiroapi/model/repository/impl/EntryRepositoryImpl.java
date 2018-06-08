package br.com.iftm.financeiroapi.model.repository.impl;

import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.exceptions.BusinessException;
import br.com.iftm.financeiroapi.model.repository.EntryRepository;
import br.com.iftm.financeiroapi.model.utils.FileUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class EntryRepositoryImpl implements EntryRepository {

    private static final String ENTRIES_FILE_PATH = "src/main/resources/database/entries.txt";

    @Override
    public void save(Entry entry) {
        try {
            FileUtil.writeLine(ENTRIES_FILE_PATH, entry);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BusinessException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Entry entry) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Entry findById(String id) {
        return null;
    }

    @Override
    public List<Entry> findAll() {
        return null;
    }
}
