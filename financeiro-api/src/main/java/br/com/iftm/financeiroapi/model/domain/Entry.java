package br.com.iftm.financeiroapi.model.domain;

import br.com.iftm.financeiroapi.model.utils.IdentifierUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class Entry implements Serializable {

    private static final long serialVersionUID = 770094988982430952L;
    private String id;
    private String description;
    private LocalDate date;
    private BigDecimal value;
    private Set<Category> categories;

    public Entry() {
        this.id = IdentifierUtil.generateUUID();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
