package br.com.iftm.financeiroapi.model.service

import br.com.iftm.financeiroapi.model.domain.Category
import br.com.iftm.financeiroapi.model.domain.Entry
import br.com.iftm.financeiroapi.model.exceptions.BusinessException
import br.com.iftm.financeiroapi.model.repository.impl.EntryRepositoryImpl
import br.com.iftm.financeiroapi.model.service.impl.EntryServiceImpl
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Repeat
import org.springframework.test.context.junit4.SpringRunner
import java.io.IOException
import java.math.BigDecimal
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.IntStream

@RunWith(SpringRunner::class)
@SpringBootTest
class EntryServiceImplTest {


    companion object {

        private val LOGGER = LoggerFactory.getLogger(EntryServiceImpl::class.java.name)
        private val DEFAULT_CATEGORY_NAME = "Categoria_"
        private val DEFAULT_ENTRY_NAME = "Lançamento_"

        @BeforeClass
        @JvmStatic
        fun init() {
            val entries = EntryRepositoryImpl().findAll()
            var range = 100000
            if (entries.count() < range) {
                range -= entries.size
                generateEntries(range)
            }
        }

        fun generateEntries(range: Int) {
            val repository = EntryRepositoryImpl()
            LOGGER.info("Início da carga de lançamentos financeiros")
            val start = LocalTime.now()

            IntStream.range(0, range).forEach { i ->
                val entry = getEntry(i)

                try {
                    repository.save(entry)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            val end = LocalTime.now()
            localTimeDifference(start, end, "Tempo de execução da carga de $range lançamentos financeiros: ")
            LOGGER.info("Fim da carga de lançamentos financeiros")
        }

        fun getEntry(index: Int?): Entry {
            val categories = HashSet<Category>()

            var categoryCode = randomNumber()
            categories.add(Category(DEFAULT_CATEGORY_NAME + categoryCode, "#FFFFF" + categoryCode))

            categoryCode = randomNumber()
            categories.add(Category(DEFAULT_CATEGORY_NAME + categoryCode, "#00000" + categoryCode))

            return Entry(DEFAULT_ENTRY_NAME + index, Date(),
                    BigDecimal.TEN.multiply(BigDecimal.valueOf(randomNumber().toLong())), categories)
        }

        fun randomNumber(): Int {
            return Random().nextInt(5) + 1
        }

        fun localTimeDifference(start: LocalTime, end: LocalTime, msg: String) {
            val diff = ChronoUnit.MILLIS.between(start, end)
            LOGGER.info("$msg $diff milisegundos, equivalentes à ${diff * 1.0 / 1000} segundos")
        }
    }

//    private val LOGGER = LoggerFactory.getLOGGER(EntryServiceImpl::class.java.name)
//    private val DEFAULT_CATEGORY_NAME = "Categoria_"
//    private val DEFAULT_ENTRY_NAME = "Lançamento_"

    @Autowired
    lateinit var entryService: EntryService

//    @BeforeClass
//    fun init() {
//        val entries = EntryRepositoryImpl().findAll()
//        var range = 100000
//        if (entries.count() < range) {
//            range -= entries.size
//            generateEntries(range)
//        }
//    }

    @Test
    @Repeat(10)
    fun saveTest() {
        var entry = getEntry(99998)

        val start = LocalTime.now()
        LOGGER.info("Início do salvar: " + start)
        entry = entryService.save(entry)

        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo para salvar o lançamento financeiro: ")
        LOGGER.info("Fim do salvar: " + end)

        val newEntry = entryService.findById(entry.id)
        Assert.assertTrue("O salvar falhou", newEntry != null)
    }

    @Test
    @Repeat(10)
    fun updateTest() {
        var entry = entryService.findAll()[0]
        entry.description = "${entry.description} Alteração do JUnit ${LocalTime.now()}"

        val start = LocalTime.now()
        LOGGER.info("Início do atualizar: $start")
        entry = entryService.save(entry)

        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo para atualizar o lançamento financeiro: ")
        LOGGER.info("Fim do atualizar: $end")

        val newEntry = entryService.findById(entry.id)
        Assert.assertTrue("O atualizar falhou", newEntry.description.contains(" Alteração do JUnit "))
    }

    @Test
    @Repeat(10)
    @Throws(BusinessException::class)
    fun findByIdTest() {
        val entry = entryService.findAll()[10]

        val start = LocalTime.now()
        LOGGER.info("Início da consulta por ID: $start")
        val newEntry = entryService.findById(entry.id)

        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo de consulta do lançamento financeiro pelo ID: ")
        LOGGER.info("Fim da consulta por ID: $end")

        Assert.assertTrue("A consulta falhou", newEntry != null)
    }

    @Test
    @Repeat(10)
    fun findAllTest() {
        val start = LocalTime.now()
        LOGGER.info("Início da consulta de todos os lançamentos financeiros: $start")
        val entries = entryService.findAll()

        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo de consulta de todas os lançamentos financeiros: ")
        LOGGER.info("Fim da consulta de todos os lançamentos financeiros: $end")

        Assert.assertTrue("A consulta falhou", !entries.isEmpty())
    }

    @Test(expected = BusinessException::class)
    @Repeat(10)
    @Throws(BusinessException::class)
    fun deleteTest() {
        val entry = entryService.findAll()[0]

        val start = LocalTime.now()
        LOGGER.info("Início da deleção por ID: " + start)
        entryService.delete(entry.id)

        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo para deletar o lançamento financeiro pelo ID: ")
        LOGGER.info("Fim da deleção por ID: " + end)

        try {
            entryService.findById(entry.id)
        } catch (e: BusinessException) {
            Assert.assertTrue("A a deleção falhou", e.message == "Registro não encontrado!")
            throw e
        }

    }

    @Test
    @Repeat(10)
    fun findByCategoryNameTest() {
        val categoryName = DEFAULT_CATEGORY_NAME + "1"

        val start = LocalTime.now()
        LOGGER.info("Início da consulta dos lançamentos financeiros pelo nome da categoria: $start")
        val entries = entryService.findByCategoryName(categoryName)

        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo para consultar e filtrar os lançamento financeiro pelo nome da categoria: ")
        LOGGER.info("Fim da consulta dos lançamentos financeiros pelo nome da categoria: $end")

        Assert.assertTrue("A consulta falhou", !entries.isEmpty())
    }

    @Test
    @Repeat(10)
    @Throws(IOException::class)
    fun sumEntriesByCategoryTest() {
        val categoryName = DEFAULT_CATEGORY_NAME + "1"

        val start = LocalTime.now()
        LOGGER.info("Início da soma de lançamentos financeiros: $start")
        val total = entryService.sumEntriesByCategory(categoryName)

        val end = LocalTime.now()
        localTimeDifference(start, end, "Tempo para somar os lançamento financeiro pelo nome da categoria: ")
        LOGGER.info("Fim da soma de lançamentos financeiros: $end")

        Assert.assertTrue("A soma falhou", total != null && BigDecimal.ZERO != total)
    }



//    fun getEntry(index: Int?): Entry {
//        val categories = HashSet<Category>()
//
//        var categoryCode = randomNumber()
//        categories.add(Category(DEFAULT_CATEGORY_NAME + categoryCode, "#FFFFF" + categoryCode))
//
//        categoryCode = randomNumber()
//        categories.add(Category(DEFAULT_CATEGORY_NAME + categoryCode, "#00000" + categoryCode))
//
//        return Entry(DEFAULT_ENTRY_NAME + index, Date(),
//                BigDecimal.TEN.multiply(BigDecimal.valueOf(randomNumber().toLong())), categories)
//    }

//    fun localTimeDifference(start: LocalTime, end: LocalTime, msg: String) {
//        val diff = ChronoUnit.MILLIS.between(start, end)
//        LOGGER.info("$msg $diff milisegundos, equivalentes à ${diff * 1.0 / 1000} segundos")
//    }

//    fun randomNumber(): Int {
//        return Random().nextInt(5) + 1
//    }

//    fun generateEntries(range: Int) {
//        val repository = EntryRepositoryImpl()
//        LOGGER.info("Início da carga de lançamentos financeiros")
//        val start = LocalTime.now()
//
//        IntStream.range(0, range).forEach { i ->
//            val entry = getEntry(i)
//
//            try {
//                repository.save(entry)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//
//        val end = LocalTime.now()
//        localTimeDifference(start, end, "Tempo de execução da carga de $range lançamentos financeiros: ")
//        LOGGER.info("Fim da carga de lançamentos financeiros")
//    }

}