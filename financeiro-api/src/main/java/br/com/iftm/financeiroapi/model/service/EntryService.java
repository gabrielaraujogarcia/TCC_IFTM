package br.com.iftm.financeiroapi.model.service;

import br.com.iftm.financeiroapi.model.domain.Entry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EntryService {

    Entry save(Entry entry);

    void delete(String id);

    List<Entry> findAll();

    Entry findById(Long id);

}
