package br.com.iftm.financeiroapi.model.domain;



import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = -8634160690880965387L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    public Category() {

    }

    public Category(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
