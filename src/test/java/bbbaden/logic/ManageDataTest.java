package bbbaden.logic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.results.PrintableResult;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.math.BigDecimal;
import java.sql.Date;

import static org.junit.Assert.*;

/**
 * Created by CoFFeeBaker on 17.01.2016.
 */
public class ManageDataTest {

    @Test
    public void testGetPortfolio() throws Exception {
        final String benutzer = "test";
        final ManageData manager = new ManageData();
        Portfolio[] test = new Portfolio[1];
        test[0] = new Portfolio(21, "ABB", 4, new ManageStocks().getAPrice("ABB"), new BigDecimal(4).multiply(new ManageStocks().getAPrice("ABB")));

        Portfolio[] all = manager.getPortfolio(benutzer);


        assertEquals("", test[0].getName(), all[0].getName());
    }

    @Test
    public void testGetTransaktionen() throws Exception {
        final String benutzer = "test";
        final ManageData manager = new ManageData();
        Transaktionen[] test = new Transaktionen[1];
        test[0] = new Transaktionen("ABB", Transaktionen.Action.KAUFEN, new Date(5, 5, 5), 60, new BigDecimal(50));

        Transaktionen[] all = manager.getTransaktionen(benutzer);


        assertEquals("", test[0].getName(), all[0].getName());
    }


    @Test
    public void testCheckLogin() throws Exception {
        final Benutzer benutzer = new Benutzer("test", new BigDecimal(60), "");
        final String username = "test";
        final BenutzerString hue = new BenutzerString(benutzer,true);
        BenutzerString testing = new ManageData().checkLogin("test", "123");

        assertEquals(hue.getUser().getName(),testing.getUser().getName());




    }

}