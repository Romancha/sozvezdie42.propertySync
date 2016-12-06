package ru.sozvezdie42.iproperty.components;

/**
 * Created by Roman on 12/5/2016.
 */
public class Contacts {

    public static final String ID_AGENT = "Агент:";
    public static final String ID_TELEPHONE = "Телефон:";
    public static final String ID_EMAIL = "Эл. почта:";
    public static final String ID_COMPANY = "Агентство:";
    public static final String ID_UPDATE = "Обновлено:";
    public static final String ID_WATCHERS = "Просмотров:";

    private String name;
    private String telephone;
    private String email;
    private String company;
    private String update;
    private int watchers;

    public Contacts(String name, String telephone, String email, String company, String update, int watchers) {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.company = company;
        this.update = update;
        this.watchers = watchers;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public String getUpdate() {
        return update;
    }

    public int getWatchers() {
        return watchers;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", update='" + update + '\'' +
                ", watchers=" + watchers +
                '}';
    }
}
