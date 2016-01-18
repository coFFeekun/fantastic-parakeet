package bbbaden.logic;

import org.junit.Test;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by CoFFeeBaker on 17.01.2016.
 */
public class ManageStocksTest {

    @Test
    public void testGetOneStock() throws Exception {
        final String symbol = "ABB";

        final Stock stock = YahooFinance.get(symbol);

        assertEquals(symbol,stock.getSymbol());

    }


    @Test
    public void testGetAPrice() throws Exception {
        final BigDecimal preis = YahooFinance.get("ABB").getQuote().getPrice();

        final BigDecimal mpreis = new ManageStocks().getAPrice("ABB");

        assertEquals(preis, mpreis);
    }
}