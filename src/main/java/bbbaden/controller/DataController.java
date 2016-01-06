package bbbaden.controller;

import bbbaden.yahoo.ManageData;
import bbbaden.yahoo.ManageStocks;
import bbbaden.yahoo.OneStock;
import bbbaden.yahoo.Portfolio;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by CoFFeeBaker on 07.12.2015.
 */
@RestController
public class DataController {


    @RequestMapping("/onestock")
    public OneStock getOneStockToJson(@RequestParam(value = "name", defaultValue = "not Avaiable") String name) {
        return new ManageStocks().getOneStock(name);

    }

    @RequestMapping("/port")
    public Portfolio[] yourPortfolio() {
        return new ManageData().getPortfolio();
    }

}

