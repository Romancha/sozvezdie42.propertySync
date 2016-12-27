package ru.sozvezdie42.iproperty.components;

/**
 * Created by Roman on 12/5/2016.
 */
public class Agent {

    public static final String ID_AGENT = "Агент:";
    public static final String ID_TELEPHONE = "Телефон:";
    public static final String ID_EMAIL = "Эл. почта:";
    public static final String ID_COMPANY = "Агентство:";
    public static final String ID_UPDATE = "Обновлено:";
    public static final String ID_WATCHERS = "Просмотров:";

    private int id;
    private String name;
    private String telephone;
    private String email;
    private int company;
    private String update;
    private int watchers;

    public int getId() {
        switch (this.name) {
            case "Марина Николаевна":
            case "Марина Николаевна Notebook":
                return 1;
            case "Марина Валерьевна":
            case "Марина":
                return 2;
            case "Наталья Алексеевна":
            case "Наталья":
                return 3;
        }
        return 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public void setWatchers(int watchers) {
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

    public int getCompany() {
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
