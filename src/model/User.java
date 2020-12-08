package model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private boolean individual;
    private String loginName;
    private String password;
    private FinanceManagementSystem fms;

    public User() {
    }

    public User(String login, String password) {
        this.loginName = login;
        this.password = password;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name, String surname, String email, String phoneNumber, boolean individual, String loginName, String password, FinanceManagementSystem fms) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.individual = individual;
        this.loginName = loginName;
        this.password = password;
        this.fms = fms;
    }

    public User(int id, String name, String surname, String email, String phoneNumber, boolean individual, String loginName, String password, FinanceManagementSystem fms) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.individual = individual;
        this.loginName = loginName;
        this.password = password;
        this.fms = fms;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isIndividual() {
        return individual;
    }

    public void setIndividual(boolean individual) {
        this.individual = individual;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FinanceManagementSystem getFms() {
        return fms;
    }

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    @Override
    public String toString() {
/*        return "User" + this.id + " ; " +
                "User name: " + this.name + " ; " +
                "User surname: " + this.surname + "; " +
                "Phone number: " + this.phoneNumber + " ; " +
                "individual: " + this.individual + " ; " +
                "Email: " + this.email + " .\n";*/
        return "ID: " + id + " " +  name + " " + surname;
    }

}

