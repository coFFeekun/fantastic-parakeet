package bbbaden.yahoo;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

/**
 * Created by CoFFeeBaker on 07.12.2015.
 */
public class ManageStocks {

    public static OneStock getOneStock(String name) {

        Stock oneStock = null;
        try {
            oneStock = YahooFinance.get(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new OneStock(oneStock.getName(), oneStock.getQuote().getPrice(), oneStock.getQuote().getAsk(), oneStock.getQuote().getBid());
    }


}
