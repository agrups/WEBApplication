package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Category implements Serializable {

    private int id;
    private String name;
    private String description;
    private Date dateCreated;
    private Date dateModified;
    private Category parentCategory;
    private int parentCategoryId;
    private ArrayList<Category> subCategories = new ArrayList<Category>();
    private ArrayList<Expenses> expenses = new ArrayList<Expenses>();
    private ArrayList<User> responsibleUsers = new ArrayList<User>();
    private ArrayList<Income> income = new ArrayList<Income>();

    private FinanceManagementSystem fms;
    private int systemId;

    public Category() {
    }

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Category(int id, String name, String description, Date dateCreated, Date dateModified/*, int systemId, int parentCategoryId*/) {   //sita vieta
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
/*        this.systemId = systemId;
        this.parentCategoryId = parentCategoryId;*/
    }

    public Category(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.responsibleUsers.add(user);
        this.dateCreated = new Date(System.currentTimeMillis());
        this.dateModified = new Date(System.currentTimeMillis());
        this.parentCategory = null;
    }

    public Category(String name, String description, User user, Category parentCategory) {
        this.name = name;
        this.description = description;
        this.responsibleUsers.add(user);
        this.dateCreated = new Date(System.currentTimeMillis());
        this.dateModified = new Date(System.currentTimeMillis());
        this.parentCategory = parentCategory;
    }

    public Category(String name, String description, FinanceManagementSystem fms) {
        this.name = name;
        this.description = description;
        this.fms = fms;
    }

    public Category(String name, String description, User user, Category parentCategory, FinanceManagementSystem fms) {  //naudojau
        this.name = name;
        this.description = description;
        this.responsibleUsers.add(user);
        this.dateCreated = new Date();
        this.parentCategory = parentCategory;
        this.fms = fms;
    }

    public Category(int id, String name, String description, Date dateCategoryCreated, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCategoryCreated;
        this.parentCategory = parentCategory;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public ArrayList<Category> getSubcategories() {
        return subCategories;
    }

    public void setSubcategories(ArrayList<Category> subcategories) {
        this.subCategories = subcategories;
    }

    public ArrayList<Expenses> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expenses> expenses) {
        this.expenses = expenses;
    }

    public ArrayList<User> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(ArrayList<User> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public ArrayList<Income> getIncome() {
        return income;
    }

    public void setIncome(ArrayList<Income> income) {
        this.income = income;
    }

    public FinanceManagementSystem getFms() {
        return fms;
    }

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    @Override
    public String toString() {
        return "Category name: " + this.name + " ;\n" +
                "Description: " + this.description + " ;\n" +
                "Date created: " + this.dateCreated + " ;\n" +
                "Date last modified: " + this.dateModified + " ;\n";
    }
}
