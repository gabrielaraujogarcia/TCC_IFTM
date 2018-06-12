package br.com.iftm.financeiroapi.model.service;

import br.com.iftm.financeiroapi.model.domain.Entry;
import br.com.iftm.financeiroapi.model.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface EntryService {

    Entry save(Entry entry) throws IOException, BusinessException;

    void delete(String id) throws IOException;

    List<Entry> findAll() throws IOException, BusinessException;

    Entry findById(String id) throws IOException, BusinessException;

}
