package br.com.iftm.financeiroapi.model.service.impl;

import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.exceptions.BusinessException;
import br.com.iftm.financeiroapi.model.repository.EntryRepository;
import br.com.iftm.financeiroapi.model.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Override
    public Entry save(Entry entry) throws IOException {
        Entry aux = entryRepository.findById(entry.getId());
        if(aux != null) {
            entryRepository.update(entry);
        } else {
            entryRepository.save(entry);
        }
        return entry;
    }

    @Override
    public Entry findById(String id) throws IOException, BusinessException {
        Entry entry = entryRepository.findById(id);
        if(entry == null) {
            throw new BusinessException("Registro não encontrado!");
        }
        return entry;
    }

    @Override
    public List<Entry> findAll() throws IOException {
        return entryRepository.findAll();
    }

    @Override
    public void delete(String id) throws IOException {
        entryRepository.delete(id);
    }

    @Override
    public List<Entry> findByCategoryName(String categoryName) throws IOException {
        return findAll().stream()
                .filter(e -> e.getCategories().stream().filter(c -> c.getName().contains(categoryName)).count() > 0)
                .collect(Collectors.toList());
    }
}
