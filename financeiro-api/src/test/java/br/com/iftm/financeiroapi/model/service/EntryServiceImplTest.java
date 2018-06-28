package br.com.iftm.financeiroapi.model.service;

import br.com.iftm.financeiroapi.model.domain.Category;
import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.exceptions.BusinessException;
import br.com.iftm.financeiroapi.model.repository.impl.EntryRepositoryImpl;
import br.com.iftm.financeiroapi.model.service.impl.EntryServiceImpl;
import org.junit.AfterClass;
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
    private static final String DEFAULT_ENTRY_DESCRIPTION = "Lançamento_";
    private static final String FIND_CATEGORY_NAME = DEFAULT_CATEGORY_NAME + "2";

    private static final Map<String, List<Long>> iterations = new HashMap<>();
    private static final String SAVE = "saveTest";
    private static final String UPDATE = "updateTest";
    private static final String FIND_BY_ID = "findByIdTest";
    private static final String FIND_ALL = "findAllTest";
    private static final String DELETE = "deleteTest";
    private static final String FIND_BY_CATEGORY_NAME = "findByCategoryNameTest";
    private static final String SUM_ENTRIES_BY_CATEGORY = "sumEntriesByCategoryTest";
    private static final String GENERATE_ENTRIES = "generateEntries";

    @Autowired
    private EntryService entryService;

    @BeforeClass
    public static void init() throws IOException {
        iterations.put(SAVE, new ArrayList<>());
        iterations.put(UPDATE, new ArrayList<>());
        iterations.put(FIND_BY_ID, new ArrayList<>());
        iterations.put(FIND_ALL, new ArrayList<>());
        iterations.put(DELETE, new ArrayList<>());
        iterations.put(FIND_BY_CATEGORY_NAME, new ArrayList<>());
        iterations.put(SUM_ENTRIES_BY_CATEGORY, new ArrayList<>());
        iterations.put(GENERATE_ENTRIES, new ArrayList<>());

        List<Entry> entries = new EntryRepositoryImpl().findAll();
        int range = 100000;

        if(entries.stream().count() < range) {
            range = range - entries.size();
            generateEntries(range);
        }

        logger.info("Iniciando os testes na app Java");
    }

    @AfterClass
    public static void end() {
        logger.info("Iterações [JAVA]: " );
        iterations.forEach((key, value) -> {
            logger.info("Iterações do método " + key);
            value.forEach(time -> logger.info(time.toString()));
        });
        logger.info("Fim dos testes na app Java");
    }

    @Test
    @Repeat(100)
    public void saveTest() throws IOException, BusinessException {
        Entry entry = getEntry(new Random().nextInt(999999999) + 1);

        LocalTime start = LocalTime.now();
        logger.info("Início do salvar: " + start);
        entry = entryService.save(entry);

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para salvar o lançamento financeiro: ", SAVE);
        logger.info("Fim do salvar: " + end);

        Entry newEntry = entryService.findById(entry.getId());
        Assert.assertTrue("O salvar falhou", newEntry != null);
    }

    @Test
    @Repeat(100)
    public void updateTest() throws IOException, BusinessException {
        Entry entry = entryService.findAll().get(0);
        entry.setDescription(entry.getDescription() + " Alteração do JUnit " + LocalTime.now());

        LocalTime start = LocalTime.now();
        logger.info("Início do atualizar: " + start);
        entry = entryService.save(entry);

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para atualizar o lançamento financeiro: ", UPDATE);
        logger.info("Fim do atualizar: " + end);

        Entry newEntry = entryService.findById(entry.getId());
        Assert.assertTrue("O atualizar falhou", newEntry != null &&
                newEntry.getDescription().contains(" Alteração do JUnit " ));
    }

    @Test
    @Repeat(100)
    public void findByIdTest() throws IOException, BusinessException {
        Entry entry = entryService.findAll().get(10);

        LocalTime start = LocalTime.now();
        logger.info("Início da consulta por ID: " + start);
        Entry newEntry = entryService.findById(entry.getId());

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo de consulta do lançamento financeiro pelo ID: ", FIND_BY_ID);;
        logger.info("Fim da consulta por ID: " + end);

        Assert.assertTrue("A consulta falhou", newEntry != null);
    }

    @Test
    @Repeat(100)
    public void findAllTest() throws IOException, BusinessException {
        LocalTime start = LocalTime.now();
        logger.info("Início da consulta de todos os lançamentos financeiros: " + start);
        List<Entry> entries = entryService.findAll();

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo de consulta de todas os lançamentos financeiros: ", FIND_ALL);
        logger.info("Fim da consulta de todos os lançamentos financeiros: " + end);

        Assert.assertTrue("A consulta falhou", !entries.isEmpty());
    }

    @Test(expected = BusinessException.class)
    @Repeat(100)
    public void deleteTest() throws IOException, BusinessException {
        Entry entry = entryService.findAll().get(0);

        LocalTime start = LocalTime.now();
        logger.info("Início da deleção por ID: " + start);
        entryService.delete(entry.getId());

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para deletar o lançamento financeiro pelo ID: ", DELETE);
        logger.info("Fim da deleção por ID: " + end);

        try {
            entryService.findById(entry.getId());
        } catch (BusinessException e) {
            Assert.assertTrue("A a deleção falhou", e.getMessage().equals("Registro não encontrado!"));
            throw e;
        }

    }

    @Test
    @Repeat(100)
    public void findByCategoryNameTest() throws IOException {
        final String categoryName = FIND_CATEGORY_NAME;

        LocalTime start = LocalTime.now();
        logger.info("Início da consulta dos lançamentos financeiros pelo nome da categoria: " + start);
        List<Entry> entries = entryService.findByCategoryName(categoryName);

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para consultar e filtrar os lançamento financeiro pelo nome da categoria: ", FIND_BY_CATEGORY_NAME);
        logger.info("Fim da consulta dos lançamentos financeiros pelo nome da categoria: " + end);

        Assert.assertTrue("A consulta falhou", !entries.isEmpty());
    }

    @Test
    @Repeat(100)
    public void sumEntriesByCategoryTest() throws IOException {
        final String categoryName = FIND_CATEGORY_NAME;

        LocalTime start = LocalTime.now();
        logger.info("Início da soma de lançamentos financeiros: " + start);
        BigDecimal total = entryService.sumEntriesByCategory(categoryName);

        LocalTime end = LocalTime.now();
        localTimeDifference(start, end, "Tempo para somar os lançamento financeiro pelo nome da categoria: ", SUM_ENTRIES_BY_CATEGORY);
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
        localTimeDifference(start, end, "Tempo de execução da carga de " + range + " lançamentos financeiros: ", GENERATE_ENTRIES);
        logger.info("Fim da carga de lançamentos financeiros");
    }

    private static Entry getEntry(Integer index) {
        Set<Category> categories = new HashSet<>();

        int categoryCode = randomNumber();
        categories.add(new Category(DEFAULT_CATEGORY_NAME + categoryCode, "#FFFFF" + categoryCode));

        categoryCode = randomNumber();
        categories.add(new Category(DEFAULT_CATEGORY_NAME + categoryCode, "#00000" + categoryCode));

        return new Entry(DEFAULT_ENTRY_DESCRIPTION + index, new Date(),
                BigDecimal.TEN.multiply(BigDecimal.valueOf(new Long(randomNumber()))), categories);
    }

    private static void localTimeDifference(LocalTime start, LocalTime end, String msg, String key) {
        Long diff = ChronoUnit.MILLIS.between(start, end);
        logger.info(msg + diff + " milisegundos, equivalentes à " + diff * 1.0 / 1000 + " segundos");
        iterations.get(key).add(diff);
    }

    private static int randomNumber() {
        return new Random().nextInt(5) + 1;
    }

}
