package br.com.iftm.financeiroapi.model.domain;



import br.com.iftm.financeiroapi.model.utils.IdentifierUtil;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Category implements Serializable {

    private static final long serialVersionUID = -8634160690880965387L;

    @ApiModelProperty(hidden = true)
    private String id;

    private String name;

    private String color;

    public Category() {
        this.id = IdentifierUtil.generateUUID();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
