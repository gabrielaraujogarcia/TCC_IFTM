package br.com.iftm.financeiroapi.model.repository;

import br.com.iftm.financeiroapi.model.domain.Entry;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface EntryRepository {

    void save(Entry entry);

    void update(Entry entry);

    void delete(String id);

    Entry findById(String id);

    List<Entry> findAll();

}
