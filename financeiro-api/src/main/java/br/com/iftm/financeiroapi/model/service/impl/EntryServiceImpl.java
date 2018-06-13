package br.com.iftm.financeiroapi.model.service.impl;

import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.exceptions.BusinessException;
import br.com.iftm.financeiroapi.model.repository.EntryRepository;
import br.com.iftm.financeiroapi.model.service.EntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class EntryServiceImpl implements EntryService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EntryRepository entryRepository;

    @Override
    public Entry save(Entry entry) throws IOException {

        try {

            Entry aux = entryRepository.findById(entry.getId());
            if(aux != null) {
                entryRepository.update(entry);
            } else {
                entryRepository.save(entry);
            }

        } catch (IOException e) {
            log.error("Erro inesperado ao salvar: " + e.getMessage());
            throw e;
        }

        return entry;
    }

    @Override
    public Entry findById(String id) throws IOException, BusinessException {

        try {

            Entry entry = entryRepository.findById(id);
            if(entry == null) {
                throw new BusinessException("Registro não encontrado!");
            }

            return entry;

        } catch (IOException e) {
            log.error("Erro inesperado ao consultar por ID: " + e.getMessage());
            throw e;
        }

    }

    @Override
    public List<Entry> findAll() throws IOException {
        return entryRepository.findAll();
    }

    @Override
    public void delete(String id) throws IOException {
        entryRepository.delete(id);
    }

}
