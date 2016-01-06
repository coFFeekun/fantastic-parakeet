package bbbaden.yahoo;

import java.math.BigDecimal;

/**
 * Created by CoFFeeBaker on 02.12.2015.
 */
public class OneStock {
    private String name;
    private BigDecimal price;
    private BigDecimal ask;
    private BigDecimal Bid;

    public OneStock() {
    }

    public OneStock(String name, BigDecimal price, BigDecimal ask, BigDecimal bid) {
        this.name = name;
        this.price = price;
        this.ask = ask;
        Bid = bid;
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

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getBid() {
        return Bid;
    }

    public void setBid(BigDecimal bid) {
        Bid = bid;
    }
}
