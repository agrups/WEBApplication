package model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class FinanceManagementSystem implements Serializable {

    private int id;
    private String name;
    private Date dateCreated;
    private String systemVersion;
    private ArrayList<Category> rootCategories = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    public FinanceManagementSystem() {
    }

    public FinanceManagementSystem(int id, String name, String systemVersion, Date dateCreated) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.systemVersion = systemVersion;
    }

    public FinanceManagementSystem(String name, String version) {
        this.name = name;
        this.systemVersion = version;
        this.dateCreated = Date.from(Instant.from(LocalDate.now()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public ArrayList<Category> getRootCategories() {
        return rootCategories;
    }

    public void setRootCategories(ArrayList<Category> rootCategories) {
        this.rootCategories = rootCategories;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void addMainCategory(Category category) {
        rootCategories.add(category);
    }

    public void removeMainCategory(Category category) {
        rootCategories.remove(category);
    }

    public void addUser(User person) {
        users.add(person);
    }

    public void removeUser(User person) {
        users.remove(person);
    }


    public User findUserByLogin(String login) {
        return users.stream().filter(user -> user.getLoginName().equals(login)).findFirst().orElse(null);
    }

    public boolean userLoginPasswordExists(String login, String password) {
        if (users.stream().filter(user -> user.getLoginName().equals(login) && user.getPassword().equals(password)).count() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Id: " + this.id + " ; " +
                "Company/system name: " + this.name + " ; " +
                "Date created: " + this.dateCreated + " ; " +
                "Version: " + this.systemVersion + ".\n";
    }
}




