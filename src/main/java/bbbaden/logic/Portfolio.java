package bbbaden.logic;

import java.math.BigDecimal;

/**
 * Model Klasse für ein Portfolioeintrag
 *
 * Created by CoFFeeBaker on 02.12.2015.
 */
public class Portfolio {
    private int key;
    private String name;
    private int anzahl;
    private BigDecimal preis;

    public BigDecimal getAllPreis() {
        return allPreis;
    }

    public void setAllPreis(BigDecimal allPreis) {
        this.allPreis = allPreis;
    }

    private BigDecimal allPreis;

    public Portfolio() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }



    public BigDecimal getPreis() {
        return preis;
    }

    public void setPreis(BigDecimal preis) {
        this.preis = preis;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Portfolio(int key, String name, int anzahl, BigDecimal preis, BigDecimal allPreis) {
        this.key = key;
        this.name = name;
        this.anzahl = anzahl;
        this.preis = preis;
        this.allPreis = allPreis;
    }
}