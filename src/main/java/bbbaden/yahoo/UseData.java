package bbbaden.yahoo;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by CoFFeeBaker on 24.11.2015.
 */
public class UseData {

    public void  getData(String ticker) {
        Stock stock = null;
        try {
            stock = YahooFinance.get(ticker);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stock.print();

    }



}
