package bbbaden.logic;

import java.math.BigDecimal;

/**
 * Created by CoFFeeBaker on 02.12.2015.
 */
public class Benutzer {
    private String name;
    private String password;
    private BigDecimal money;

    public Benutzer(String name, BigDecimal money, String password) {
        this.name = name;
        this.money = money;
        this.password = password;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
