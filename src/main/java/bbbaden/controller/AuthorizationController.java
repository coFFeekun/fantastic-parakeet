package bbbaden.controller;


import bbbaden.yahoo.Benutzer;
import bbbaden.yahoo.BenutzerString;
import bbbaden.yahoo.ManageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


/**
 * Created by CoFFeeBaker on 02.12.2015.
 */
@Controller
public class AuthorizationController {

    @RequestMapping("/login")
    public String checkLogin(HttpSession session,@RequestParam(name = "name", defaultValue = "")String name,@RequestParam(name = "password",defaultValue = "")String password){
        BenutzerString ultimate = new ManageData().checkLogin(name, password);
        Benutzer user = ultimate.getUser();
        if (ultimate.getTemplate()){
            session.setAttribute("name", name);
            session.setAttribute("money", user.getMoney());
            return "portfolio";
        }
    return "index";
    }


    @RequestMapping("/hund")
    public String vergiss(){

        return "portfolio";
    }
}
