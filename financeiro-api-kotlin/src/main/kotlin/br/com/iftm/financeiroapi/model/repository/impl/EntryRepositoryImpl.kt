package br.com.iftm.financeiroapi.model.repository.impl

import br.com.iftm.financeiroapi.model.domain.Entry
import br.com.iftm.financeiroapi.model.repository.EntryRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption


@Component
class EntryRepositoryImpl: EntryRepository {

    private val USER_DIR = Paths.get(System.getProperty("user.dir"))
    private val ENTRIES_FILE = USER_DIR.parent.resolve("entries.txt")
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val mapper = ObjectMapper().registerModule(KotlinModule())

    init {
        log.info("Validação do ENTRIES_FILE $ENTRIES_FILE")
        validateENTRIES_FILE()
        log.info("ENTRIES_FILE OK")
    }

    override fun save(entry: Entry) {
        val line = mapper.writeValueAsString(entry)
        Files.write(ENTRIES_FILE, line.toByteArray(Charset.forName("UTF-8")), StandardOpenOption.APPEND)
        Files.write(ENTRIES_FILE, System.getProperty("line.separator").toByteArray(), StandardOpenOption.APPEND)
    }

    override fun findAll(): List<Entry> {
        val entries = ArrayList<Entry>()
        Files.readAllLines(ENTRIES_FILE)
                .filter {line -> line.isNotBlank() }
                .forEach{line -> entries.add(mapper.readValue(line, Entry::class.java))}
        return entries
    }

    override fun findById(id: String): Entry? = findAll()
            .filter { e -> e.id == id }
            .firstOrNull()

    override fun update(entry: Entry) {
        val updated = ArrayList<String>()
        findAll().forEach { e ->
                if (e.id == entry.id) {
                    updated.add(mapper.writeValueAsString(entry))
                } else {
                    updated.add(mapper.writeValueAsString(e))
                }
        }
        Files.write(ENTRIES_FILE, updated)
    }

    override fun delete(id: String) {
        val updated = ArrayList<String>()
        findAll().forEach { e ->
            if (e.id != id) {
                updated.add(mapper.writeValueAsString(e))
            }
        }
        Files.write(ENTRIES_FILE, updated)
    }

    private fun validateENTRIES_FILE() {
        if (!Files.exists(ENTRIES_FILE)) {
            Files.createFile(ENTRIES_FILE);
            log.info("ARQUIVO CRIADO NO DIRETÓRIO: $ENTRIES_FILE")
        }
    }

}

