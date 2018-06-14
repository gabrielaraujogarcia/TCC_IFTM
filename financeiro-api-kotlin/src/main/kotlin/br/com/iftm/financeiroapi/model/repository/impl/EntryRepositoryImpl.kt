package br.com.iftm.financeiroapi.model.repository.impl

import br.com.iftm.financeiroapi.model.domain.Entry
import br.com.iftm.financeiroapi.model.exceptions.BusinessException
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

    private val ENTRIES_FILE_PATH = "/src/main/resources/database/entries.txt"
    private val PATH = Paths.get(System.getProperty("user.dir"), ENTRIES_FILE_PATH)
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val mapper = ObjectMapper().registerModule(KotlinModule())

    init {
        log.info("Validação do path ${PATH.toString()}")
        validatePath()
        log.info("Path OK")
    }

    override fun save(entry: Entry) {
        val line = mapper.writeValueAsString(entry)
        Files.write(PATH, line.toByteArray(Charset.forName("UTF-8")), StandardOpenOption.APPEND)
        Files.write(PATH, System.getProperty("line.separator").toByteArray(), StandardOpenOption.APPEND)
    }

    override fun findAll(): List<Entry> {
        val entries = ArrayList<Entry>()

        Files.readAllLines(PATH)
                .filter {line -> line.isNotBlank() }
                .forEach{line -> entries.add(mapper.readValue(line, Entry::class.java))}

        return entries

    }

    override fun findById(id: String): Entry? {
        return findAll()
                .filter { e -> e.id == id }
                .firstOrNull()
    }

    override fun update(entry: Entry) {
        val updated = ArrayList<String>()

        findAll().forEach { e ->
                if (e.id == entry.id) {
                    updated.add(mapper.writeValueAsString(entry))
                } else {
                    updated.add(mapper.writeValueAsString(e))
                }
        }

        Files.write(PATH, updated)
    }

    override fun delete(id: String) {
        val updated = ArrayList<String>()

        findAll().forEach { e ->
            if (e.id != id) {
                updated.add(mapper.writeValueAsString(e))
            }
        }

        Files.write(PATH, updated)
    }

    private fun validatePath() {
        if (!Files.exists(PATH)) {
            val msg = "O arquivo $ENTRIES_FILE_PATH não existe!"
            log.error(msg)
            throw BusinessException(msg)
        }
    }

}

