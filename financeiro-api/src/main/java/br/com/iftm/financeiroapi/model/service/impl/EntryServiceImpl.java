package br.com.iftm.financeiroapi.model.service.impl;

import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.repository.EntryRepository;
import br.com.iftm.financeiroapi.model.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Override
    public Entry save(Entry entry) {
        entryRepository.save(entry);
        return entry;
    }

    @Override
    public void delete(String id) {
        entryRepository.delete(id);
    }

    @Override
    public List<Entry> findAll() {
        return entryRepository.findAll();
    }

    @Override
    public Entry findById(Long id) {
        //return entryRepository.findById(id).orElse(null);
        return null;
    }
}
