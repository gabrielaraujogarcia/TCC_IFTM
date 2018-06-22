package br.com.iftm.financeiroapi.model.service;

import br.com.iftm.financeiroapi.model.domain.Category;
import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.exceptions.BusinessException;
import br.com.iftm.financeiroapi.model.repository.impl.EntryRepositoryImpl;
import br.com.iftm.financeiroapi.model.service.impl.EntryServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntryServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(EntryServiceImpl.class.getName());
    private static final String DEFAULT_CATEGORY_NAME = "Categoria_";
    private static final String DEFAULT_ENTRY_NAME = "Lançamento_";

    @Autowired
    private EntryService entryService;

    @BeforeClass
    public static void init() throws IOException {
        List<Entry> entries = new EntryRepositoryImpl().findAll();
        int range = 100000;

        if(entries.stream().count() < range) {
            range = range - entries.size();
            generateEntries(range);
        }
    }

    @Test
    @Repeat(10)
    public void saveTest() throws IOException, BusinessException {
        Entry entry = getEntry(99998);

        LocalTime start = LocalTime.now();
        logger.info("Início do salvar: " + start);
        entry = entryService.save(entry);

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para salvar o lançamento financeiro: ");
        logger.info("Fim do salvar: " + end);

        Entry newEntry = entryService.findById(entry.getId());
        Assert.assertTrue("O salvar falhou", newEntry != null);
    }

    @Test
    @Repeat(10)
    public void updateTest() throws IOException, BusinessException {
        Entry entry = entryService.findAll().get(0);
        entry.setDescription(entry.getDescription() + " Alteração do JUnit " + LocalTime.now());

        LocalTime start = LocalTime.now();
        logger.info("Início do atualizar: " + start);
        entry = entryService.save(entry);

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para atualizar o lançamento financeiro: ");
        logger.info("Fim do atualizar: " + end);

        Entry newEntry = entryService.findById(entry.getId());
        Assert.assertTrue("O atualizar falhou", newEntry != null &&
                newEntry.getDescription().contains(" Alteração do JUnit " ));
    }

    @Test
    @Repeat(10)
    public void findByIdTest() throws IOException, BusinessException {
        Entry entry = entryService.findAll().get(10);

        LocalTime start = LocalTime.now();
        logger.info("Início da consulta por ID: " + start);
        Entry newEntry = entryService.findById(entry.getId());

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo de consulta do lançamento financeiro pelo ID: ");;
        logger.info("Fim da consulta por ID: " + end);

        Assert.assertTrue("A consulta falhou", newEntry != null);
    }

    @Test
    @Repeat(10)
    public void findAllTest() throws IOException, BusinessException {
        LocalTime start = LocalTime.now();
        logger.info("Início da consulta de todos os lançamentos financeiros: " + start);
        List<Entry> entries = entryService.findAll();

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo de consulta de todas os lançamentos financeiros: ");
        logger.info("Fim da consulta de todos os lançamentos financeiros: " + end);

        Assert.assertTrue("A consulta falhou", !entries.isEmpty());
    }

    @Test(expected = BusinessException.class)
    @Repeat(10)
    public void deleteTest() throws IOException, BusinessException {
        Entry entry = entryService.findAll().get(0);

        LocalTime start = LocalTime.now();
        logger.info("Início da deleção por ID: " + start);
        entryService.delete(entry.getId());

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para deletar o lançamento financeiro pelo ID: ");
        logger.info("Fim da deleção por ID: " + end);

        try {
            entryService.findById(entry.getId());
        } catch (BusinessException e) {
            Assert.assertTrue("A a deleção falhou", e.getMessage().equals("Registro não encontrado!"));
            throw e;
        }

    }

    @Test
    @Repeat(10)
    public void findByCategoryNameTest() throws IOException {
        final String categoryName = DEFAULT_CATEGORY_NAME + "1";

        LocalTime start = LocalTime.now();
        logger.info("Início da consulta dos lançamentos financeiros pelo nome da categoria: " + start);
        List<Entry> entries = entryService.findByCategoryName(categoryName);

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para consultar e filtrar os lançamento financeiro pelo nome da categoria: ");
        logger.info("Fim da consulta dos lançamentos financeiros pelo nome da categoria: " + end);

        Assert.assertTrue("A consulta falhou", !entries.isEmpty());
    }

    @Test
    @Repeat(10)
    public void sumEntriesByCategoryTest() throws IOException {
        final String categoryName = DEFAULT_CATEGORY_NAME + "1";

        LocalTime start = LocalTime.now();
        logger.info("Início da soma de lançamentos financeiros: " + start);
        BigDecimal total = entryService.sumEntriesByCategory(categoryName);

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para somar os lançamento financeiro pelo nome da categoria: ");
        logger.info("Fim da soma de lançamentos financeiros: " + end);

        Assert.assertTrue("A soma falhou", total != null && !BigDecimal.ZERO.equals(total));
    }

    private static void generateEntries(int range) throws IOException {
        EntryRepositoryImpl repository = new EntryRepositoryImpl();
        logger.info("Início da carga de lançamentos financeiros");
        LocalTime start = LocalTime.now();

        IntStream.range(0, range).forEach(i -> {
            Entry entry = getEntry(i);

            try {
                repository.save(entry);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo de execução da carga de " + range + " lançamentos financeiros: ");
        logger.info("Fim da carga de lançamentos financeiros");
    }

    private static Entry getEntry(Integer index) {
        Set<Category> categories = new HashSet<>();

        int categoryCode = randomNumber();
        categories.add(new Category(DEFAULT_CATEGORY_NAME + categoryCode, "#FFFFF" + categoryCode));

        categoryCode = randomNumber();
        categories.add(new Category(DEFAULT_CATEGORY_NAME + categoryCode, "#00000" + categoryCode));

        return new Entry(DEFAULT_ENTRY_NAME + index, new Date(),
                BigDecimal.TEN.multiply(BigDecimal.valueOf(new Long(randomNumber()))), categories);
    }

    private static void localTimeDifference(LocalTime start, LocalTime end, String msg) {
        Long diff = ChronoUnit.MILLIS.between(start, end);
        logger.info(msg + diff + " milisegundos, equivalentes à " + diff * 1.0 / 1000 + " segundos");
    }

    private static int randomNumber() {
        return new Random().nextInt(5) + 1;
    }

}
