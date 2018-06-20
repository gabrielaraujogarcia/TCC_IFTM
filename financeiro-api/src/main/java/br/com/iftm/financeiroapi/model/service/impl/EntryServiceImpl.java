package br.com.iftm.financeiroapi.model.service.impl;

import br.com.iftm.financeiroapi.model.domain.Category;
import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.exceptions.BusinessException;
import br.com.iftm.financeiroapi.model.repository.EntryRepository;
import br.com.iftm.financeiroapi.model.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class EntryServiceImpl implements EntryService {

    private static final Logger logger = Logger.getLogger(EntryServiceImpl.class.getName());
    private static final String DEFAULT_CATEGORY_NAME = "Categoria_";
    private static final String DEFAULT_ENTRY_NAME = "Lançamento_";

    @Autowired
    private EntryRepository entryRepository;

    @Override
    public Entry save(Entry entry) throws IOException {
        logger.info("Início do salvar ou atualizar...");
        LocalTime start = LocalTime.now();
        Entry aux = entryRepository.findById(entry.getId());
        if(aux != null) {
            entryRepository.update(entry);
            LocalTime end = LocalTime.now();
            localTimeDifference(start, end, "Tempo para atualizar o lançamento financeiro: ");
        } else {
            entryRepository.save(entry);
            LocalTime end = LocalTime.now();
            localTimeDifference(start, end, "Tempo para gravar o lançamento financeiro: ");
        }
        logger.info("Fim do salvar ou atualizar");
        return entry;
    }

    @Override
    public Entry findById(String id) throws IOException, BusinessException {
        logger.info("Início da consulta por ID");
        LocalTime start = LocalTime.now();
        Entry entry = entryRepository.findById(id);
        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo de consulta do lançamento financeiro pelo ID: ");
        if(entry == null) {
            throw new BusinessException("Registro não encontrado!");
        }
        logger.info("Fim da consulta por ID");
        return entry;
    }

    @Override
    public List<Entry> findAll() throws IOException {
        logger.info("Início da consulta de todos os lançamentos financeiros");
        LocalTime start = LocalTime.now();
        List<Entry> entries = entryRepository.findAll();
        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo de consulta de todas os lançamentos financeiros: ");
        logger.info("Fim da consulta de todos os lançamentos financeiros");
        return entries;
    }

    @Override
    public void delete(String id) throws IOException {
        logger.info("Início da deleção por ID");
        LocalTime start = LocalTime.now();
        entryRepository.delete(id);
        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para deletear o lançamento financeiro pelo ID: ");
        logger.info("Fim da deleção por ID");
    }

    @Override
    public List<Entry> findByCategoryName(String categoryName) throws IOException {
        logger.info("Início da consulta dos lançamentos financeiros pelo nome da categoria");
        LocalTime start = LocalTime.now();
        List<Entry> entries = findAll().stream()
                .filter(e -> e.getCategories().stream().filter(c -> c.getName().contains(categoryName)).count() > 0)
                .collect(Collectors.toList());
        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para consultar e filtrar os lançamento financeiro pelo nome da categoria: ");
        logger.info("Fim da consulta dos lançamentos financeiros pelo nome da categoria");
        return entries;
    }

    @Override
    public void generateEntries() {
        logger.info("Início da carga de lançamentos financeiros");
        int range = 30000;
        ExecutorService executor = Executors.newFixedThreadPool(5);
        LocalTime start = LocalTime.now();
        IntStream.range(0, range).forEach(i -> {
                Set<Category> categories = new HashSet<>();
                int categoryCode = randomNumber();
                categories.add(new Category(DEFAULT_CATEGORY_NAME + categoryCode, "#FFFFF" + categoryCode));
                categoryCode = randomNumber();
                categories.add(new Category(DEFAULT_CATEGORY_NAME + categoryCode, "#00000" + categoryCode));
                Entry entry = new Entry(DEFAULT_ENTRY_NAME + i, new Date(),
                        BigDecimal.ONE.multiply(BigDecimal.valueOf(new Long(i))), categories);
                try {
                    entryRepository.save(entry);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        );
        executor.shutdown();
        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo de execução da carga de " + range + " lançamentos financeiros: ");
        logger.info("Fim da carga de lançamentos financeiros");
    }

    private int randomNumber() {
        return new Random().nextInt((5 - 1) + 1) + 1;
    }

    private void localTimeDifference(LocalTime start, LocalTime end, String msg) {
        Long diff = ChronoUnit.MILLIS.between(start, end);
        logger.info( "Início: " + start);
        logger.info( "Fim: " + end);
        logger.info(msg + diff + " milisegundos, equivalentes à " + diff * 1.0 / 1000 + " segundos");
    }

}
