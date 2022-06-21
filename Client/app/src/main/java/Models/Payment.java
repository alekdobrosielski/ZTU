package Models;

import java.util.ArrayList;
import java.util.Date;

public class Payment {
    private int id;
    private int ownerID;
    private String title;
    private String description;
    private double value;
    private String date;
    private ArrayList<User> debtors;

    public Payment(int ownerID, String title, String description, double value, String date) {
        this.ownerID = ownerID;
        this.title = title;
        this.description = description;
        this.value = value;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<User> getDebtors() {
        return debtors;
    }

    public void setDebtors(ArrayList<User> debtors) {
        this.debtors = debtors;
    }

    public Payment(){}

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
