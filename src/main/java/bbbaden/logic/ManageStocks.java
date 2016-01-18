package bbbaden.logic;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by CoFFeeBaker on 07.12.2015.
 */
public class ManageStocks {
    /**
     * Hohlt eine Aktien von der Yahoo Finance API
     * @param name
     * @return
     */
    public static OneStock getOneStock(String name) {

        Stock oneStock = null;
        try {
            oneStock = YahooFinance.get(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new OneStock(oneStock.getName(), oneStock.getQuote().getPrice());
    }

    public String saveStock(String name, int anzahl, BigDecimal kapital,String benutzer){
        ManageData manager = new ManageData();
        OneStock wish = getOneStock(name);
        manager.saveTransaktion(makeStockToTrans(wish, Transaktionen.Action.KAUFEN,kapital,anzahl),benutzer);

        return "";
    }

    /**
     * Verwandelt eine Aktien zu einer Transaktiono
     * @param wish
     * @param action
     * @param kapital
     * @param anzahl
     * @return
     */
    public Transaktionen makeStockToTrans(OneStock wish, Transaktionen.Action action,BigDecimal kapital,int anzahl){
        return new Transaktionen(wish.getName(),action,new Date(),anzahl,kapital);
    }

    /**
     * hohlt den Preis für eine bestimmten Aktie
     * @param name
     * @return
     */
    public BigDecimal getAPrice(String name){
        try {
            Stock priceStock = YahooFinance.get(name);
            return priceStock.getQuote().getPrice();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
