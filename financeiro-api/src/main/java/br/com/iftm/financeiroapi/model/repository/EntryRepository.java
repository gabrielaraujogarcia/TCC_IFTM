package br.com.iftm.financeiroapi.model.repository;

import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.exceptions.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Component
public interface EntryRepository {

    void save(Entry entry) throws IOException;

    void update(Entry entry) throws IOException;

    void delete(String id) throws IOException;

    Entry findById(String id) throws IOException;

    List<Entry> findAll() throws IOException;

}
