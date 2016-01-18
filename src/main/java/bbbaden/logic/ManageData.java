package bbbaden.logic;


import org.springframework.security.crypto.bcrypt.BCrypt;


import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;


/**
 * Created by CoFFeeBaker on 02.12.2015.
 */
public class ManageData {

    private Portfolio currentPortfolio;
    private Benutzer currentBentutzer;
    private Portfolio currentDatabasePortfolio;



    /**
     * Liest das Portfolio aus der Datenbank für den aktuellen Benutzer
     * @param benutzer
     * @return
     */
    public Portfolio[] getPortfolio(String benutzer) {


        ArrayList<Portfolio> atMomentPortfolios = new ArrayList<Portfolio>();
        ManageStocks hue = new ManageStocks();
        try {
            String query = "SELECT * FROM portfolio WHERE benutzerFK = ?";

            PreparedStatement st = getConnection().prepareStatement(query);
            st.setString(1, benutzer);
            ResultSet MyRs = st.executeQuery();

            while (MyRs.next()) {
                atMomentPortfolios.add(new Portfolio(MyRs.getInt("idportfolio"), MyRs.getString("name"), MyRs.getInt("anzahl"), hue.getAPrice(MyRs.getString("name")),hue.getAPrice(MyRs.getString("name")).multiply(new BigDecimal(MyRs.getInt("anzahl")))));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atMomentPortfolios.toArray(new Portfolio[atMomentPortfolios.size()]);
    }

    /**
     * Liest die Transaktionnen aus der Datenbank
     * @param benutzer
     * @return
     */
    public Transaktionen[] getTransaktionen(String benutzer) {

        Transaktionen transaktionen = new Transaktionen();
        ArrayList<Transaktionen> transList = new ArrayList<Transaktionen>();


        try {
            String query = "SELECT * FROM transaktionen WHERE benutzerFK = ?";
            PreparedStatement st = getConnection().prepareStatement(query);

            st.setString(1, benutzer);
            ResultSet MyRs = st.executeQuery();

            while (MyRs.next()) {
                transList.add(new Transaktionen(MyRs.getString("name"), Transaktionen.Action.valueOf(MyRs.getString("typ")), MyRs.getDate("datum"), MyRs.getInt("anzahl"), MyRs.getBigDecimal("kapital")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transList.toArray(new Transaktionen[transList.size()]);
    }

    /**
     * Speichert eine Transaktion in der Datenbank
     * @param trans
     * @param benutzerFK
     */
    public void saveTransaktion(Transaktionen trans, String benutzerFK) {
        try {
            String query = "INSERT INTO transaktionen (name,datum,anzahl,kapital,benutzerFK,typ) VALUES (?,?,?,?,?,?)";
            PreparedStatement st = getConnection().prepareStatement(query);
            st.setString(1, trans.getName());
            st.setDate(2, getCurrentDate());
            st.setInt(3, trans.getAnzahl());
            st.setBigDecimal(4, trans.getKapital());
            st.setString(5, benutzerFK);
            st.setString(6, trans.getAktion().toString());
            st.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * überprüft ob ein Benutzer existiert in der Datenbank
     * @param name
     * @param password
     * @return
     */
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

                    return new BenutzerString(new Benutzer(databaseLogin.getName(), databaseLogin.getMoney(), ""), true);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return new BenutzerString(new Benutzer("", null, ""), false);
    }

    /**
     * Speichert einen Nutzer in der Datenbank
     * @param name
     * @param password
     * @param money
     */
    public void saveUser(String name, String password, BigDecimal money) {
        try {

            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/boersenspiel", "root", "0411");


            //hash

            String hashedPassword = generateHash(password);

            String sql = "INSERT INTO benutzer (benutzername,password,money) VALUES (?,?,?)";
            PreparedStatement preparedStatement = myConn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setBigDecimal(3, money);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *  Fügt Aktien zum Portfolioo hinzu oder nimmt sie weg, je nach action
     *
     * @param port
     * @param benutzer
     * @param action
     * @return
     */
    public String saveOrUpdatePortfolio(Portfolio port, Benutzer benutzer, Transaktionen.Action action) {

        currentPortfolio = port;
        currentBentutzer = benutzer;
        currentDatabasePortfolio = getAnExistingOne();

        if (action.toString().equals("VERKAUFEN")) {
            return takeAway();
        }
        if (action.toString().equals("KAUFEN")) {
            return putOn();
        }


        return "Keine valide Aktion";
    }

    /**
     * Wird von saveOrUpdatePortfolio aufgerufen, falls man Akiten kauft
     * @return
     */
    private String putOn() {
        try {

            if (currentDatabasePortfolio != null) {
                if (currentDatabasePortfolio.getName().equals(currentPortfolio.getName())) {
                    String query = "UPDATE portfolio SET anzahl = ? WHERE name = ?";
                    PreparedStatement st = getConnection().prepareStatement(query);
                    int newAnzahl = currentDatabasePortfolio.getAnzahl() + currentPortfolio.getAnzahl();
                    st.setInt(1, newAnzahl);
                    st.setString(2, currentPortfolio.getName());
                    st.execute();
                    st.close();
                    return "Erfolgreich dazugekauft";
                }
            } else {
                String query = "INSERT INTO portfolio (name,preis,anzahl,benutzerFK) VALUES (?,?,?,?)";
                PreparedStatement st = getConnection().prepareStatement(query);
                st.setString(1, currentPortfolio.getName());
                st.setBigDecimal(2, currentPortfolio.getPreis());
                st.setInt(3, currentPortfolio.getAnzahl());
                st.setString(4, currentBentutzer.getName());
                st.execute();
                st.close();
                return "Erfolgreich neu gekauft";
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Nichts wurde geändert";
    }

    /**
     * Wird von saveOrUpdatePortfolio aufgerufen, falls man Aktien verkauft
     * @return
     */
    private String takeAway() {
        try {

            if (currentDatabasePortfolio != null) {
                if (currentDatabasePortfolio.getAnzahl() == currentPortfolio.getAnzahl()) {
                    String query = "DELETE FROM portfolio WHERE idportfolio = ?";
                    PreparedStatement st = getConnection().prepareStatement(query);
                    st.setInt(1, currentPortfolio.getKey());
                    st.execute();
                    return "Erfolgreich die Ganzen Aktien dieses Verkäufers gelöscht";
                } else {
                    String query = "UPDATE portfolio SET anzahl = ? WHERE idportfolio = ?";
                    PreparedStatement st = getConnection().prepareStatement(query);
                    st.setInt(1, currentDatabasePortfolio.getAnzahl() - currentPortfolio.getAnzahl());
                    st.setInt(2, currentPortfolio.getKey());
                    st.execute();
                    st.close();
                    return "Erfolgeich ein Teil verkauft";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Nichts wurde geändert";

    }

    /**
     * Sucht eine Spezifischen Portfolio eintrag
     * @return
     */
    private Portfolio getAnExistingOne() {
        Portfolio justGot = null;
        try {
            ManageStocks hue = new ManageStocks();
            String query = "SELECT * FROM portfolio WHERE benutzerFK = ? AND name= ?";

            PreparedStatement st = null;
            st = getConnection().prepareStatement(query);
            st.setString(1, currentBentutzer.getName());
            st.setString(2, currentPortfolio.getName());
            ResultSet result = st.executeQuery();
            if (result.next()) {
            justGot = new Portfolio(result.getInt("idportfolio"), result.getString("name"), result.getInt("anzahl"), hue.getAPrice(result.getString("name")),hue.getAPrice(result.getString("name")).multiply(new BigDecimal(result.getInt("anzahl"))));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return justGot;
    }

    /**
     * generiert ein gehashed Passwort
     * @param password
     * @return Hashed Passwort
     */
    public String generateHash(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Macht eine Verbindung zu der Datenbank
     * @return Connection
     */
    private Connection getConnection() {
        try {

            return DriverManager.getConnection("jdbc:mysql://localhost:3306/boersenspiel", "root", "0411");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getCurrentDate() {
        java.util.Date currentDate = new java.util.Date();
        return new Date(currentDate.getTime());
    }

    /**
     * Speichert en neuen Kapitalswert für einen Nutzer
     * @param action
     * @param money
     * @param price
     * @param anzahl
     * @return
     */
    public BigDecimal updateKapital(Transaktionen.Action action, BigDecimal money, BigDecimal price, int anzahl) {
        if (action.toString().equals("VERKAUFEN")) {
            money = money.add(new BigDecimal(anzahl).multiply(price));
            return money;
        }
        if (action.toString().equals("KAUFEN")) {
            money = money.subtract(new BigDecimal(anzahl).multiply(price));
            return money;
        }
        return null;
    }


}
