package bbbaden.controller;


import bbbaden.logic.Benutzer;
import bbbaden.logic.BenutzerString;
import bbbaden.logic.ManageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;


/**
 * Created by CoFFeeBaker on 02.12.2015.
 */
@Controller
public class AuthorizationController {
    /**
     * Login Request Mapping
     * @param session
     * @param name
     * @param password
     * @return richtiges Template
     */
    @RequestMapping("/login")
    public String checkLogin(HttpSession session,@RequestParam(name = "benutzername", defaultValue = "")String name,@RequestParam(name = "password",defaultValue = "")String password){
        BenutzerString ultimate = new ManageData().checkLogin(name, password);
        Benutzer user = ultimate.getUser();
        if (ultimate.getTemplate()){
            session.setAttribute("benutzer", user.getName());
            session.setAttribute("money", user.getMoney());
            return "portfolio";
        }
    return "index";
    }

    /**
     * Registriert einen neuen Benutzer
     * @param session
     * @param benutzername
     * @param password
     * @param kapital
     * @return
     */
    @RequestMapping("/register")
    public String register(HttpSession session, @RequestParam(name = "benutzername")String benutzername,@RequestParam(name = "password")String password,@RequestParam(name = "kapital")int kapital){
        new ManageData().saveUser(benutzername,password,new BigDecimal(kapital));
        session.setAttribute("benutzer", benutzername);
        session.setAttribute("money", new BigDecimal(kapital));
        return  "portfolio";
    }

    /**
     * Gibt das Template für eingeloggte zurück, wenn eine Session besteht
     * @param session
     * @return
     */
    @RequestMapping("/userui")
    public String logedIn(HttpSession session){
        if (session.getAttribute("benutzer") != null){
            return "portfolio";
        }
        return "index";
    }

    /**
     * Hilfsklasse
     * @param session
     * @return
     */
    @RequestMapping("/initiate")
    public String initiate(HttpSession session) {
        session.setAttribute("benutzer", "Hans");
        session.setAttribute("money", new BigDecimal(800));
        return "portfolio";
    }

    /**
     * Loggt den momentanen Benutzer aus
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("benutzer");
        session.removeAttribute("money");
        return "index";
    }
}
