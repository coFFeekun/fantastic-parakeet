package bbbaden.logic;

import java.math.BigDecimal;

/**
 *  Model Klasse für eine Aktie
 *
 * Created by CoFFeeBaker on 02.12.2015.
 */
public class OneStock {
    private String name;
    private BigDecimal price;


    public OneStock() {
    }

    public OneStock(String name, BigDecimal price) {
        this.name = name;
        this.price = price;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
