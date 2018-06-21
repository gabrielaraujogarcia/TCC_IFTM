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
public class EntryServiceWithoutLogImpl implements EntryService {

    private static final String DEFAULT_CATEGORY_NAME = "Categoria_";
    private static final String DEFAULT_ENTRY_NAME = "Lançamento_";

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
        List<Entry> entries = findAll().stream()
                .filter(e -> e.getCategories().stream().filter(c -> c.getName().contains(categoryName)).count() > 0)
                .collect(Collectors.toList());
        return entries;
    }

    @Override
    public void generateEntries() {
        int range = 30000;
        IntStream.range(0, range).forEach(i -> {
                Set<Category> categories = new HashSet<>();
                int categoryCode = randomNumber();
                categories.add(new Category(DEFAULT_CATEGORY_NAME + categoryCode, "#FFFFF" + categoryCode));
                categoryCode = randomNumber();
                categories.add(new Category(DEFAULT_CATEGORY_NAME + categoryCode, "#00000" + categoryCode));
                Entry entry = new Entry(DEFAULT_ENTRY_NAME + i, new Date(),
                        BigDecimal.TEN.multiply(BigDecimal.valueOf(new Long(randomNumber()))), categories);
                try {
                    entryRepository.save(entry);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        );
    }

    @Override
    public BigDecimal sumEntriesByCategory(String categoryName) throws IOException {
        return EntryUtil.sumEntries(findByCategoryName(categoryName));
    }

    private int randomNumber() {
        return new Random().nextInt((5 - 1) + 1);
    }

}
