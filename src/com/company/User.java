package com.company;

import database.Row;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String type;
    private final ArrayList<Row> cart = new ArrayList<>();
    private double totalSpent;

    public User(){

    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void addCart(Row row){
        this.cart.add(row);
    }
    public void removeCart(int i){
        this.cart.remove(i);
    }
    public ArrayList<Row> getCart(){
        return this.cart;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }
    public double getTotalSpent() {
        return totalSpent;
    }
}
