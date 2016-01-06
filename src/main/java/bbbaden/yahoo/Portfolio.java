package bbbaden.yahoo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by CoFFeeBaker on 02.12.2015.
 */
public class Portfolio {
    private String name;
    private int menge;
    private BigDecimal kaufprice;
    private Date kaufdatum;
    private BigDecimal bisherigerGewinn;

    public Portfolio() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public BigDecimal getKaufprice() {
        return kaufprice;
    }

    public void setKaufprice(BigDecimal kaufprice) {
        this.kaufprice = kaufprice;
    }

    public Date getKaufdatum() {
        return kaufdatum;
    }

    public void setKaufdatum(Date kaufdatum) {
        this.kaufdatum = kaufdatum;
    }

    public BigDecimal getBisherigerGewinn() {
        return bisherigerGewinn;
    }

    public void setBisherigerGewinn(BigDecimal bisherigerGewinn) {
        this.bisherigerGewinn = bisherigerGewinn;
    }
}
