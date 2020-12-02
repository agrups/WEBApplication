package model;

import java.io.Serializable;
import java.util.Date;

public class Expenses implements Serializable {

    private int id;
    private int price;
    private String name;
    private Date dateCreated;
    private Category category;


    public Expenses() {
    }

    public Expenses(int id, int price, String name, Date dateCreated, Category category) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.dateCreated = dateCreated;
        this.category = category;
    }

    public Expenses(String name, int price, Date dateCreated, Category category) {
        this.price = price;
        this.name = name;
        this.dateCreated = dateCreated;
        this.category = category;
    }

    public Expenses(String name, int price, Category category) {
        this.price = price;
        this.name = name;
        this.dateCreated = new Date();
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Expense: " + name +
                "\n\tvalue=" + price +
                "\n\tdate=" + dateCreated;
    }
}
