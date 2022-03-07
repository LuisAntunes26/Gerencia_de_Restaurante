package com.company;

import database.Query;
import database.Row;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Template {
    private User user;
    private final Query query;
    private final Scanner scanner;


    public Template(Scanner scanner, Query query){
        this.query = query;
        this.scanner = scanner;
    }

    public void setUser(User user){
        this.user = user;
    }


    //Apresenta o Template e recolhe a escolha efectuada
    public int choice(String title, ArrayList<String> options){
        boolean fail = false;
        String error = "";
        int response_int = - 1;
        String response;
        int limit = options.size()-1;
        do{
            console_clear();
            System.out.println(title);
            for (String option : options) {
                System.out.println(option);
            }
            if(fail){
                System.out.println(error.toUpperCase(Locale.ROOT));
            }
            System.out.print("Choice: ");
            try {
                response = scanner.nextLine();//String
                response_int = Integer.parseInt(response);//int
                if (response_int > limit){
                    fail = true;
                    error = "The number must be between 0 and " + limit;
                }
            }
            catch (NumberFormatException exeption){
                String emptyErrorCode = "For input string: \"\"";
                if(!exeption.getMessage().equals(emptyErrorCode)) {
                    fail = true;
                    error = "Not a number!";
                }
            }
            catch (Exception exeption){
                fail = true;
                error = "We are having some problems, please try again later";
            }

        }while(response_int < 0 || response_int > limit);
        console_clear();
        return response_int;
    }

    //Template inicial
    public ArrayList<String> start(){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Exit");
        options.add("1 - Login");
        options.add("2 - Register");

        return options;
    }


    //Metodo para "limpar" consola;
    public void console_clear(){
        for(int i = 0; i <30; i++){
            System.out.println('\b');
        }
    }

    //Metodo para intoducao do user
    public String enterUser(){
        console_clear();
        System.out.print("Enter a username: ");
        String username = this.scanner.nextLine();
        console_clear();

        while(username.equals("") || username.length() >= 25) {
            System.out.println("Username empty or with more than 25 character - INVALID");
            System.out.print("Enter a username: ");
            username = this.scanner.nextLine();
            console_clear();
        }
        return username;
    }

    //Metodo para intoducao da password
    public String enterPassword(){
        System.out.print("Enter a Password: ");
        String password = this.scanner.nextLine();
        console_clear();

        while(password.equals("")) {
            System.out.println("Password empty - INVALID");
            System.out.println("Enter a password: ");
            password = this.scanner.nextLine();
            console_clear();
        }
        return password;
    }


    public String createClientUsername(){
        console_clear();
        String username;
        System.out.print("Enter a username: ");
        username = this.scanner.nextLine();
        console_clear();
        return username;
    }

    public String createClientPassword(){
        console_clear();
        String password;
        System.out.print("Enter a password: ");
        password = this.scanner.nextLine();
        console_clear();
        return password;
    }

    //Template MainMenu
    public ArrayList<String> optionsMainMenuUser(){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Exit");
        options.add("1 - Menus");
        options.add("2 - Payment");
        return options;
    }

    public ArrayList<String> optionsMenuUser(){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Return");
        options.add("1 - Add to Cart");
        options.add("2 - Remove From Cart");
        options.add("3 - Show Cart");
        return options;
    }

    public ArrayList<String> optionsMenuTypes(){
        ArrayList<String > types = menuTypes();
        ArrayList<String > options = new ArrayList<>();
        options.add("0 - Return");
        options.add("1 - All Menus");

        for (int i = 0; i < types.size(); i++){
            options.add((i+2) + " - " + types.get(i));
        }
        return options;
    }

    public ArrayList<String> menuTypes(){
        ArrayList<String> types = new ArrayList<>();
        List<Row> rows = query.menuAllTypes();
        for (Row row: rows) {
            if(!types.contains(row.getColumns().get(0))){
                types.add(row.getColumns().get(0));
            }
        }
        return types;
    }

    public ArrayList<String> menusToOptions(List<Row> rows){
        ArrayList<String> options = new ArrayList<>();
        String str;
        options.add("0 - Return");
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            str = (i+1 + "- " + row.getColumns().get(1) + " (" + row.getColumns().get(2) + ")");
            options.add(str);
        }
        return options;
    }

    public ArrayList<String> ordersToOptions(List<Row> rows){
        ArrayList<String> options = new ArrayList<>();
        String str;
        options.add("0 - Return");
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            str = (i+1 + "- Username: " + row.getColumns().get(1) +
                    " | Total: " + row.getColumns().get(2) + "€");
            options.add(str);
        }
        return options;
    }


    public ArrayList<String> showcart(){
        int qtd;
        String str;
        double priceForItems;
        double totalPrice = 0;
        ArrayList<Row> cart = this.user.getCart();
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < cart.size(); i++) {
            qtd = 0;
            Row irow = cart.get(i);
            for (Row jrow : cart) {
                if (Integer.parseInt(jrow.getColumns().get(0)) == Integer.parseInt(irow.getColumns().get(0))) {
                    qtd += 1;
                }
            }
            priceForItems = (qtd * Double.parseDouble(irow.getColumns().get(2)));
            str =  qtd + "x " +irow.getColumns().get(1) + " (Unit. " + irow.getColumns().get(2) +
                    "€)  -  For products = " + String.format("%.2f", priceForItems) + "€";
            if(!results.contains(str)){
                totalPrice += priceForItems;
                results.add(str);
            }
        }
        results.add("Total = " + String.format("%.2f", totalPrice) + "€");
        this.user.setTotalSpent(totalPrice);
        return results;
    }

    public ArrayList<String> optionsMainMenuAdmin(){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Exit");
        options.add("1 - Menus");
        options.add("2 - Access accounting");
        return options;
    }

    public ArrayList<String> optionsMenuAdmin(){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Return");
        options.add("1 - Create Menus");
        options.add("2 - Edit Menus");
        return options;
    }

    public String nameMenu() {
        String name;
        do {
            System.out.println("*-*-*-* Create a Menu *-*-*-*");
            System.out.print("Name: ");
            name = scanner.nextLine();
            console_clear();
        } while (name.length() == 0);
        return name;
    }

    public double priceMenu(String name){
        boolean fail = false;
        String error = "";
        String response;
        double response_double = 0.0;
        do {
            System.out.println("*-*-*-* Create a Menu *-*-*-*");
            System.out.println("Name: " + name);
            System.out.print("Price: ");
            if (fail) {
                System.out.println(error);
            }
            try {
                response = scanner.nextLine();//String
                response_double = Double.parseDouble(response);//int
                console_clear();
                if (response_double < 0) {
                    fail = true;
                    error = "The number must be greater than zero!";
                }
            } catch (NumberFormatException exeption) {
                fail = true;
                error = "Not a Number!";
            } catch (Exception exeption) {
                fail = true;
                error = "We are having some problems, please try again later";
            }
        } while (response_double < 0);
        return response_double;
    }

    public String typeMenu(String name, BigDecimal price){
        String type;
        do {
            System.out.println("*-*-*-* Create a Menu *-*-*-*");
            System.out.println("Name: " + name);
            System.out.println("Price: " + price + '€');
            System.out.print("Type: ");
            type = scanner.nextLine();
            console_clear();
        } while (type.length() == 0);
        return type;
    }



    public ArrayList<String> colunumsToOptions(Row menu){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Return");
        int i;
        for(i = 1; i < menu.getColumns().size(); i++){
            options.add(i +" - " + menu.getColumns().get(i));
        }
        options.add(i + " - Confirm");
        return options;
    }

    public String insertNewName(Row chosenMenu){
        String title = "*-*-*-* Change name *-*-*-*";
        String oldName = chosenMenu.getColumns().get(1);
        console_clear();
        System.out.println(title);
        System.out.println("Old name : " + oldName);
        System.out.print("New name: ");
        String newName = this.scanner.nextLine();
        console_clear();
        while(newName.equals("") || newName.equals(oldName)) {
            System.out.println(title);
            System.out.println("The name is empty or is equal to the old one!");
            System.out.println("Old name: " + oldName);
            System.out.print("New name: ");
            newName = this.scanner.nextLine();
            console_clear();
        }
        return newName;
    }

    public String insertNewPrice(Row chosenMenu){
        String title = "*-*-*-* Change price *-*-*-*";
        String response;
        double responseDouble = 0.0;
        String error = "";
        boolean fail = false;
        boolean sucess = false;
        do {
            System.out.println(title);
            if(fail){
                System.out.println(error);
            }
            System.out.println("Old Price: " + chosenMenu.getColumns().get(2) + "€");
            System.out.print("Choice: ");
            try {
                response = scanner.nextLine();//String
                responseDouble = Double.parseDouble(response);//int
                sucess = true;
            } catch (NumberFormatException exeption) {
                fail = true;
                error = "Not a Number!";
            }
        }while(!sucess);
        return Double.toString(responseDouble);
    }

    public String insertNewType(Row chosenMenu){
        String title = "*-*-*-* Change type *-*-*-*";
        String oldType = chosenMenu.getColumns().get(3);
        console_clear();
        System.out.println(title);
        System.out.println("Old type: " + oldType);
        System.out.print("New type: ");
        String newType = this.scanner.nextLine();
        console_clear();
        while(newType.equals("") || newType.equals(oldType)) {
            System.out.println(title);
            System.out.println("The type is empty or is equal to the old one!");
            System.out.print("New type: ");
            newType = this.scanner.nextLine();
            console_clear();
        }
        return newType;
    }

    public ArrayList<String> optionsCountabilityAdmin(){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Return");
        options.add("1 - See table order");
        return options;
    }
}