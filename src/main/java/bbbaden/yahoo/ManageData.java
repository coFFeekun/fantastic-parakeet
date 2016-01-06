package bbbaden.yahoo;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;


/**
 * Created by CoFFeeBaker on 02.12.2015.
 */
public class ManageData {

    public Portfolio[] getPortfolio() {

        Portfolio onePortfolio = new Portfolio();
        ArrayList<Portfolio> atMomentPortfolios = new ArrayList<Portfolio>();
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/boersenspiel", "root", "0411");

            Statement myStmt = myConn.createStatement();

            ResultSet MyRs = myStmt.executeQuery("SELECT * FROM portfolio");

            while (MyRs.next()) {
                onePortfolio.setName(MyRs.getString("name"));
                onePortfolio.setMenge(MyRs.getInt("menge"));
                onePortfolio.setKaufdatum(MyRs.getDate("kaufdatum"));
                onePortfolio.setKaufprice(MyRs.getBigDecimal("kaufpreis"));
                onePortfolio.setBisherigerGewinn(MyRs.getBigDecimal("bisherigerGewinn"));

                atMomentPortfolios.add(onePortfolio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Portfolio[] allPortfolios = atMomentPortfolios.toArray(new Portfolio[atMomentPortfolios.size()]);
        return allPortfolios;
    }



    public BenutzerString checkLogin(String name, String password) {
        Benutzer databaseLogin = new Benutzer("", new BigDecimal(0), "");
        Benutzer login = new Benutzer(name, new BigDecimal(0), password);


        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/boersenspiel", "root", "0411");

            Statement myStmt = myConn.createStatement();

            ResultSet MyRs = myStmt.executeQuery("SELECT * FROM benutzer");

            while (MyRs.next()) {
                databaseLogin.setName(MyRs.getString("benutzername"));
                databaseLogin.setPassword(MyRs.getString("password"));
                databaseLogin.setMoney(MyRs.getBigDecimal("money"));


                if (BCrypt.checkpw(login.getPassword(), databaseLogin.getPassword()) && login.getName().equals(databaseLogin.getName())) {

                    return new BenutzerString(new Benutzer(databaseLogin.getName(),databaseLogin.getMoney(),""),true);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return new BenutzerString(new Benutzer("",null,""),false);
    }



    public void saveUser(String name, String password) {
        try {

            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/boersenspiel", "root", "0411");


            //hash

            String hashedPassword = generateHash(password);

            String sql = "INSERT INTO benutzer (benutzername,password) value (?,?)";
            PreparedStatement preparedStatement = myConn.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String generateHash(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}
