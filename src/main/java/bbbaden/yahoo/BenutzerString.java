package bbbaden.yahoo;

/**
 * Created by CoFFeeBaker on 04.01.2016.
 */
public class BenutzerString {

    private Benutzer user;
    private boolean template;

    public BenutzerString(Benutzer user, boolean template) {
        this.user = user;
        this.template = template;
    }

    public Benutzer getUser() {

        return user;
    }

    public void setUser(Benutzer user) {
        this.user = user;
    }

    public boolean getTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }
}
