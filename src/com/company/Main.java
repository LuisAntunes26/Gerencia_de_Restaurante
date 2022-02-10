package com.company;

import database.JDBConnection;
import database.Query;
import database.Row;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final JDBConnection connection = new JDBConnection("projeto_testes");
    private static final Query query = new Query(connection);
    private static final Template template = new Template(scanner, query);
    private static final User user = new User();


    public static void main(String[] args) {
        start();
    }


    // Iniciar programa
    private static void start() {
        String title = "*-*-*-* Login *-*-*-*";
        ArrayList<String> options = template.start();
        switch (template.choice(title, options)) {
            case 0 -> System.exit(0);
            case 1 -> login();
            case 2 -> register();
            default -> System.out.println("Erro!");
        }
    }

    //Login
    private static void login() {
        String type;

            user.setUsername(template.enterUser());
            user.setPassword(template.enterPassword());
            type = query.testLogin(user);
        if (!type.equals("Error")) {
            user.setType(type);
            template.setUser(user);
            if (user.getType().equals("user"))
                mainMenuUser();
            else
                mainMenuAdmin();
        }else {
            start();
        }
    }

    private static void register() {
        String username = template.createClientUsername();
        try {
            if (query.checkUsername(username).equals("Exist")) {
                System.out.println("Username already exist, try again please!");
                System.out.print("Press ENTER to go back ...");
                System.in.read();
                start();
            }
        }catch (Exception e){
            System.out.println("Error");
        }
        String password = template.createClientPassword();
        byte choice;
        System.out.println("*-*-*-* Data Confirmation *-*-*-*");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.print("Confirmation(1/0): ");
        choice = scanner.nextByte();

        if (choice == 1) {
            query.register(username, password);
        }
        start();
    }

    private static void mainMenuUser() {
        String title = "*-*-*-* Main Menu *-*-*-*";
        ArrayList<String> options = template.optionsMainMenuUser();
        switch (template.choice(title, options)) {
            case 0 -> System.exit(0);
            case 1 -> menuUser();
            case 2 -> paymentUser();
            default -> System.out.println("Erro!");
        }
    }

    private static void menuUser() {
        String title = "*-*-*-* Menus *-*-*-*";
        ArrayList<String> options = template.optionsMenuUser();
        switch (template.choice(title, options)) {
            case 0 -> mainMenuUser();
            case 1 -> menuUserAdd();
            case 2 -> menuUserRemove();
            case 3 -> menuShowCart();
            default -> System.out.println("Erro!");
        }
    }

    private static void menuUserAdd() {
        String title = "*-*-*-* Add Menus *-*-*-*";
        ArrayList<String> options = template.optionsMenuTypes();
        int choice = template.choice(title, options);
        addMenus(choice);
    }

    private static void addMenus(int x) {
        if (x == 1) {
            menuUserAddAll();
        }
        if (x > 1) {
            String type = template.menuTypes().get(x - 2);
            String title = "*-*-*-* " + type + " *-*-*-*";
            List<Row> outputBD = query.menuSee(type);
            ArrayList<String> options = template.menusToOptions(outputBD);
            int choice = template.choice(title, options);
            menuUserAddQtd(outputBD, options, choice);
        } else {
            menuUser();
        }
    }

    private static void menuUserAddQtd(List<Row> outputBD, ArrayList<String> options, int choice) {
        if (choice > 0) {
            System.out.println("*-*-*-* " + options.get(choice).substring(3) + " *-*-*-*");
            System.out.print("Quantity: ");
            int qtd = scanner.nextInt();
            for (int i = 0; i < qtd; i++) {
                user.addCart(outputBD.get(choice - 1));
            }
        }
        menuUserAdd();
    }

    private static void menuUserAddAll() {
        String title = "*-*-*-* All *-*-*-*";
        List<Row> outputBD = query.menuSeeAll();
        ArrayList<String> options = template.menusToOptions(outputBD);
        int choice = template.choice(title, options);
        menuUserAddQtd(outputBD, options, choice);
    }

    private static void menuUserRemove() {
        String title = "*-*-*-* Remove *-*-*-*";
        ArrayList<String> options = template.menusToOptions(user.getCart());
        int choice = template.choice(title, options);
        if (choice > 0) {
            user.removeCart(choice - 1);
            menuUserRemove();
        }
        menuUser();
    }

    private static void menuShowCart() {
        System.out.println("*-*-*-* Cart *-*-*-*");
        ArrayList<String> viewCart = template.showcart();
        for (String item : viewCart) {
            System.out.println(item);
        }
        System.out.print("Press ENTER to go back...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        menuUser();
    }

    private static void mainMenuAdmin() {
        String title = "*-*-*-* Main Menu (Admin)*-*-*-*";
        ArrayList<String> options = template.optionsMainMenuAdmin();
        switch (template.choice(title, options)) {
            case 0 -> System.exit(0);
            case 1 -> menuAdmin();
            case 2 -> countabilityAdmin();
            default -> System.out.println("Erro!");
        }
    }

    private static void menuAdmin() {
        String title = "*-*-*-* Menus *-*-*-*";
        ArrayList<String> options = template.optionsMenuAdmin();
        switch (template.choice(title, options)) {
            case 0 -> mainMenuAdmin();
            case 1 -> menuAdminCreate();
            case 2 -> menuAdminEdit();
            default -> System.out.println("Erro!");
        }
    }

    private static void menuAdminCreate() {
        String name = template.nameMenu();
        BigDecimal price = BigDecimal.valueOf(template.priceMenu(name));
        String type = template.typeMenu(name, price);
        byte choice;
        System.out.println("*-*-*-* New product *-*-*-*");
        System.out.println("Name: " + name);
        System.out.println("Price: " + price + '€');
        System.out.println("Type: " + type);
        System.out.print("Confirmation(1/0): ");
        choice = scanner.nextByte();
        if (choice == 1) {
            query.addMenu(name, price, type);
        }
        menuAdmin();

    }


    private static void menuAdminEdit() {
        String title = "*-*-*-* Edit Menus *-*-*-*";
        ArrayList<String> options = template.optionsMenuTypes();
        int choice = template.choice(title, options);
        editMenuAdminExecute(choice);
    }


    private static void countabilityAdmin() {
        template.console_clear();
        String title = "*-*-*-* Countability *-*-*-*";

        ArrayList<String> options = template.optionsCountabilityAdmin();
        int choice = template.choice(title, options);


        switch(choice){
            case 0: mainMenuAdmin();
            case 1: chooseFromAllOrders();
        }
    }

    private static void chooseFromAllOrders(){
        String title = "*-*-*-* All Orders *-*-*-*";
        List<Row> outputDB = query.seeAllOrders();
        ArrayList<String> options = template.ordersToOptions(outputDB);
        int choice = template.choice(title, options);

        if (choice > 0){
            int id = Integer.parseInt(outputDB.get(choice-1).getColumns().get(0));
            chooseFromOrderID(id);
        }else {
            countabilityAdmin();
        }
    }

    private static void chooseFromOrderID(int choice){
        List<Row> rows = query.seeOrderFromID(choice);
        String clientName = rows.get(0).getColumns().get(1);
        System.out.println("Client username: " + clientName);
        for (Row row : rows) {
            System.out.println("Menu: " + row.getColumns().get(3) +
                    " | Price: " + row.getColumns().get(4) + "€ | Type: " + row.getColumns().get(5) + " | Qtd.: " +
                    row.getColumns().get(6));
        }
        System.out.print("Press ENTER to go back...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        chooseFromAllOrders();
    }

    private static void editMenuAdminExecute(int x) {
        Row chosenMenu;
        if (x == 1) {
            chosenMenu = menuAdminEditAll();
            changeMenu(chosenMenu);
        }
        if (x > 1) {
            String type = template.menuTypes().get(x - 2);
            String title = "*-*-*-* " + type + " *-*-*-*";
            List<Row> outputBD = query.menuSee(type);
            ArrayList<String> options = template.menusToOptions(outputBD);
            int chosenMenuIndex = template.choice(title, options);
            if(chosenMenuIndex != 0){
                chosenMenu = outputBD.get(chosenMenuIndex - 1);
                changeMenu(chosenMenu);
            }
        }
        menuAdmin();

    }

    private static Row menuAdminEditAll(){
        String title = "*-*-*-* All *-*-*-*";
        List<Row> outputBD = query.menuSeeAll();
        ArrayList<String> options = template.menusToOptions(outputBD);
        int chosenMenuIndex = template.choice(title, options)-1;
        return outputBD.get(chosenMenuIndex);
    }

    public static void changeMenu(Row chosenMenu){
        int id =Integer.parseInt(chosenMenu.getColumns().get(0));
        int columnChoiceIndex;
        do {
            String title = "*-*-*-*- Choose Column *-*-*-*-";
            columnChoiceIndex = template.choice(title, template.colunumsToOptions(chosenMenu));
            switch (columnChoiceIndex) {
                case 0 -> menuAdminEdit();
                case 1 -> chosenMenu.setElement(1, template.insertNewName(chosenMenu));
                case 2 -> chosenMenu.setElement(2, template.insertNewPrice(chosenMenu));
                case 3 -> chosenMenu.setElement(3, template.insertNewType(chosenMenu));
                case 4 -> System.out.println("Execute query");
                default -> throw new IllegalStateException("Unexpected value: " + columnChoiceIndex);
            }
        }while(columnChoiceIndex != 4);
        String newName = chosenMenu.getColumns().get(1);
        double newPrice = Double.parseDouble(chosenMenu.getColumns().get(2));
        String newType = chosenMenu.getColumns().get(3);
        query.editMenu(id, newName, newPrice, newType);
    }

    public static void paymentUser(){
        String title = "*-*-*-*-*-*-*-* Payment *-*-*-*-*-*-*-*" ;
        System.out.println(title);
        for (String str: template.paymentCart()) {
            System.out.println(str);
        }
        System.out.print("Do you want to finish your purchase?(1/0): ");
        byte choice = scanner.nextByte();
        if (choice == 1){
        query.createPedido(user);
        template.console_clear();
        System.out.println("Thanks for buying with us!!");
        System.exit(0);
        }else {
            mainMenuUser();
        }
    }
}
