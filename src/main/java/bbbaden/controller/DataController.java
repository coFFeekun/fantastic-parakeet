package bbbaden.controller;

import bbbaden.logic.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by CoFFeeBaker on 07.12.2015.
 */
@RestController
public class DataController {

    /**
     *  Gibt eine Aktie als JSON zurück
     *
     * @param name
     * @return
     */

    @RequestMapping("/onestock")
    public OneStock getOneStockToJson(@RequestParam(value = "name", defaultValue = "not Avaiable") String name) {
        return new ManageStocks().getOneStock(name);

    }

    /**
     *  gibt das Portfolio als JSON Array zurück für den akutellen benutzer
     * @param session
     * @return
     */
    @RequestMapping("/port")
    public Portfolio[] yourPortfolio(HttpSession session) {

        return new ManageData().getPortfolio((String) session.getAttribute("benutzer"));
    }

    /**
     * Gibt die Transaktionen für den aktuellen Benutzer zurück als JSON Array
     * @param session
     * @return
     */
    @RequestMapping("/trans")
    public Transaktionen[] yourTransaktionen(HttpSession session) {
        return new ManageData().getTransaktionen((String) session.getAttribute("benutzer"));
    }

    /**
     *
     *  Akutalisisert das Portfolio
     *
     * @param session
     * @param id
     * @param name
     * @param anzahl
     * @param action
     * @return
     */
    @RequestMapping("/updatePortfolio")
    public String kaufen(HttpSession session, @RequestParam(name = "id") int id, @RequestParam(name = "aktienname") String name, @RequestParam(name = "anzahl") int anzahl, @RequestParam(name = "typ") String action) {
        if (!new ManageStocks().getAPrice(name).equals(new BigDecimal(0))) {
            BigDecimal kapital = (BigDecimal) session.getAttribute("money");
            BigDecimal price = new ManageStocks().getAPrice(name).multiply(new BigDecimal(anzahl));
            if (kapital.compareTo(price) == -1 && action.equals("KAUFEN")) {
                return "Sie haben zu wenig Geld";
            }
            ManageData manager = new ManageData();
            session.setAttribute("money", manager.updateKapital(Transaktionen.Action.valueOf(action), (BigDecimal) session.getAttribute("money"), new ManageStocks().getAPrice(name), anzahl));
            return manager.saveOrUpdatePortfolio(new Portfolio(id, name, anzahl, new ManageStocks().getAPrice(name),null), new Benutzer((String) session.getAttribute("benutzer"), (BigDecimal) session.getAttribute("money"), ""), Transaktionen.Action.valueOf(action));

        }
        return "Diese Aktien gibt es nicht";
    }

    /**
     * speichert eine Transaktion für den aktuellen Bentutzer
     * @param session
     * @param name
     * @param anzahl
     * @param action
     */
    @RequestMapping("/savetrans")
    public void save(HttpSession session, @RequestParam(name = "aktienname") String name, @RequestParam(name = "anzahl") int anzahl, @RequestParam(name = "typ") String action) {
        new ManageData().saveTransaktion(new Transaktionen(name, Transaktionen.Action.valueOf(action), new Date(), anzahl, (BigDecimal) session.getAttribute("money")), (String) session.getAttribute("benutzer"));
    }

}

