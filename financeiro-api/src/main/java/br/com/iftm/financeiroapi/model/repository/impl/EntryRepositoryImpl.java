package br.com.iftm.financeiroapi.model.repository.impl;

import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.repository.EntryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Component
public class EntryRepositoryImpl implements EntryRepository {

    private static final Path USER_DIR = Paths.get(System.getProperty("user.dir"));
    private static final Path ENTRIES_FILE = USER_DIR.getParent().resolve("entries.txt");
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper mapper = new ObjectMapper();

    public EntryRepositoryImpl() throws IOException {
        log.info("Validação do path " + ENTRIES_FILE);
        validatePath();
        log.info("Path OK");
    }

    @Override
    public void save(Entry entry) throws IOException {
        String line = mapper.writeValueAsString(entry);
        Files.write(ENTRIES_FILE, line.getBytes(Charset.forName("UTF-8")), StandardOpenOption.APPEND);
        Files.write(ENTRIES_FILE, System.getProperty("line.separator").getBytes(), StandardOpenOption.APPEND);
    }

    @Override
    public List<Entry> findAll() throws IOException {
        List<Entry> entries = new ArrayList<>();
        Files.readAllLines(ENTRIES_FILE).stream()
                .filter(line ->  !line.isEmpty())
                .forEach(line -> {
                    try {
                        entries.add(mapper.readValue(line, Entry.class));
                    } catch (IOException e) {
                        log.error("Erro ao serializar a linha. Motivo: " + e.getMessage());
                    }
                });
        return entries;
    }

    @Override
    public Entry findById(String id) throws IOException {
        return findAll().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Entry entry) throws IOException {
        List<String> updated = new ArrayList<>();
        findAll().stream().forEach(e -> {
            try {
                if (e.getId().equals(entry.getId())) {
                    updated.add(mapper.writeValueAsString(entry));
                } else {
                    updated.add(mapper.writeValueAsString(e));
                }
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        });
        Files.write(ENTRIES_FILE, updated);
    }

    @Override
    public void delete(String id) throws IOException {
        List<String> updated = new ArrayList<>();
        findAll().stream().forEach(e -> {
            if (!e.getId().equals(id)) {
                try {
                    updated.add(mapper.writeValueAsString(e));
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                }
            }
        });
        Files.write(ENTRIES_FILE, updated);
    }

    private void validatePath() throws IOException {
        if (!Files.exists(ENTRIES_FILE)) {
            try {
                Files.createFile(ENTRIES_FILE);
            } catch (IOException e) {
                log.error("ERRO AO CRIAR O ARQUIVO " + ENTRIES_FILE);
                throw e;
            }
            log.info("ARQUIVO CRIADO NO DIRETÓRIO: " + ENTRIES_FILE);
        }
    }

}
