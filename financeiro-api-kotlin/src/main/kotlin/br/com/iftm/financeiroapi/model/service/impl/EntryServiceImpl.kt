package br.com.iftm.financeiroapi.model.service.impl

import br.com.iftm.financeiroapi.model.domain.Category
import br.com.iftm.financeiroapi.model.domain.Entry
import br.com.iftm.financeiroapi.model.exceptions.BusinessException
import br.com.iftm.financeiroapi.model.extends.sumEntries
import br.com.iftm.financeiroapi.model.repository.EntryRepository
import br.com.iftm.financeiroapi.model.service.EntryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.Executors
import java.util.stream.IntStream

@Component
class EntryServiceImpl : EntryService {

    private val logger = LoggerFactory.getLogger(EntryServiceImpl::class.java.name)
    private val DEFAULT_CATEGORY_NAME = "Categoria_"
    private val DEFAULT_ENTRY_NAME = "Lançamento_"

    @Autowired
    lateinit var entryRepository: EntryRepository

    override fun save(entry: Entry): Entry {
        logger.info("Início do salvar ou atualizar...")
        val start = LocalTime.now()
        val aux = entryRepository.findById(entry.id)
        if (aux != null) {
            entryRepository.update(entry)
            val end = LocalTime.now()
            localTimeDifference(start, end, "Tempo para atualizar o lançamento financeiro: ")
        } else {
            entryRepository.save(entry)
            val end = LocalTime.now()
            localTimeDifference(start, end, "Tempo para gravar o lançamento financeiro: ")
        }
        logger.info("Fim do salvar ou atualizar")
        return entry
    }

//    @Throws(BusinessException::class)
//    override fun findById(id: String): Entry =
//            entryRepository.findById(id) ?: throw BusinessException("Registro não encontrado!")

    @Throws(BusinessException::class)
    override fun findById(id: String): Entry {
        logger.info("Início da consulta por ID")
        val start = LocalTime.now()
        val entry = entryRepository.findById(id) ?: throw BusinessException("Registro não encontrado!")
        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo de consulta do lançamento financeiro pelo ID: ")
        logger.info("Fim da consulta por ID")
        return entry
    }

//    override fun findAll(): List<Entry> = entryRepository.findAll()

    override fun findAll(): List<Entry> {
        logger.info("Início da consulta de todos os lançamentos financeiros")
        val start = LocalTime.now()
        val entries = entryRepository.findAll()
        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo de consulta de todas os lançamentos financeiros: ")
        logger.info("Fim da consulta de todos os lançamentos financeiros")
        return entries
    }

//    override fun delete(id: String) = entryRepository.delete(id)

    override fun delete(id: String) {
        logger.info("Início da deleção por ID")
        val start = LocalTime.now()
        entryRepository.delete(id)
        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo para deletear o lançamento financeiro pelo ID: ")
        logger.info("Fim da deleção por ID")
    }

//    override fun findByCategoryName(categoryName: String): List<Entry> =
//        findAll().filter { e -> e.categories.filter{ c -> c.name.contains(categoryName) }.count() > 0 }

    override fun findByCategoryName(categoryName: String): List<Entry> {
        logger.info("Início da consulta dos lançamentos financeiros pelo nome da categoria")
        val start = LocalTime.now()
        val entries = findAll().filter { e -> e.categories.filter{ c -> c.name.contains(categoryName) }.count() > 0 }
        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo para consultar e filtrar os lançamento financeiro pelo nome da categoria: ")
        logger.info("Fim da consulta dos lançamentos financeiros pelo nome da categoria")
        return entries
    }

    override fun generateEntries() {
        logger.info("Início da carga de lançamentos financeiros")
        val range = 30000
        val executor = Executors.newFixedThreadPool(5)
        val start = LocalTime.now()
        IntStream.range(0, range).forEach { i ->
            val categories = HashSet<Category>()
            var categoryCode = randomNumber()
            categories.add(Category(DEFAULT_CATEGORY_NAME + categoryCode, "#FFFFF" + categoryCode))
            categoryCode = randomNumber()
            categories.add(Category(DEFAULT_CATEGORY_NAME + categoryCode, "#00000" + categoryCode))
            val entry = Entry(DEFAULT_ENTRY_NAME + i, Date(),
                    BigDecimal.ONE.multiply(BigDecimal.valueOf(i.toLong())), categories)
            entryRepository.save(entry)
        }
        executor.shutdown()
        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo de execução da carga de $range lançamentos financeiros: ")
        logger.info("Fim da carga de lançamentos financeiros")
    }

//    override fun sumEntriesByCategory(categoryName: String): BigDecimal = findByCategoryName(categoryName).sumEntries()

    override fun sumEntriesByCategory(categoryName: String): BigDecimal {
        logger.info("Início da soma dos lançamentos financeiros")
        val start = LocalTime.now()
        val total = findByCategoryName(categoryName).sumEntries()
        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo de execução da soma dos lançamentos financeiros: ")
        logger.info("Fim da soma dos lançamentos financeiros")
        return total
    }

    private fun randomNumber(): Int {
        return Random().nextInt(5 - 1 + 1) + 1
    }

    private fun localTimeDifference(start: LocalTime, end: LocalTime, msg: String) {
        val diff = ChronoUnit.MILLIS.between(start, end)
        logger.info("Início: " + start)
        logger.info("Fim: " + end)
        logger.info("$msg $diff milisegundos, equivalentes à ${diff * 1.0 / 1000} segundos")
    }
}