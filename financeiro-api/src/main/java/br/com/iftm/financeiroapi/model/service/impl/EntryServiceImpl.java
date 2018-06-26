package br.com.iftm.financeiroapi.model.service.impl;

import br.com.iftm.financeiroapi.model.domain.Category;
import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.exceptions.BusinessException;
import br.com.iftm.financeiroapi.model.repository.EntryRepository;
import br.com.iftm.financeiroapi.model.service.EntryService;
import br.com.iftm.financeiroapi.model.utils.EntryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Override
    public Entry save(Entry entry) throws IOException, BusinessException {
        if(entry.getCategories().isEmpty()) {
            throw new BusinessException("Deve ser informado ao menos uma categoria!");
        }
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
        return findAll().stream().filter(e ->
                e.getCategories().stream().filter(c -> c.getName().contains(categoryName)).count() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal sumEntriesByCategory(String categoryName) throws IOException {
        return EntryUtil.sumEntries(findByCategoryName(categoryName));
    }

}
