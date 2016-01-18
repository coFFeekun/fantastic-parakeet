package bbbaden.logic;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Model Klasse für eine Transaktion
 *
 * Created by CoFFeeBaker on 08.01.2016.
 */
public class Transaktionen {

    public enum Action{
        VERKAUFEN,KAUFEN
    }

    private String name;
    private Action aktion;
    private Date datum;
    private int anzahl;
    private BigDecimal kapital;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Action getAktion() {
        return aktion;
    }

    public void setAktion(String aktion) {
        this.aktion = Action.valueOf(aktion);
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public BigDecimal getKapital() {
        return kapital;
    }

    public void setKapital(BigDecimal kapital) {
        this.kapital = kapital;
    }

    public Transaktionen(String name, Action aktion, Date datum, int anzahl, BigDecimal kapital) {
        this.name = name;
        this.aktion = aktion;
        this.datum = datum;
        this.anzahl = anzahl;
        this.kapital = kapital;
    }

    public Transaktionen() {
    }
}
