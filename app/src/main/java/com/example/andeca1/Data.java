package com.example.andeca1;

public class Data {
    String item,date,id,description;
    int amount,month;
    public Data(){

    }

    public Data(String item, String date, String id, String description, int amount, int month) {
        this.item = item;
        this.date = date;
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.month = month;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmounnt(int amounnt) {
        this.amount = amounnt;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
