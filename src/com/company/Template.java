package com.company;
import database.Query;
import database.Row;

import java.math.BigDecimal;
import java.util.*;

import java.io.Console;

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
            System.out.print("Escolha: ");
            try {
                response = scanner.nextLine();//String
                response_int = Integer.parseInt(response);//int
                if (response_int > limit){
                    fail = true;
                    error = "O valor inserido tem que ser entre 0 e " + limit;
                }
            }
            catch (NumberFormatException exeption){
                fail = true;
                error = "O valor tem de ser um Int";
            }
            catch (Exception exeption){
                fail = true;
                error = "Estamos a ter problemas, Tente mais tarde!";
            }

        }while(response_int < 0 || response_int > limit);
        console_clear();
        return response_int;
    }

    //Template inicial
    public ArrayList<String> start(){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Fechar");
        options.add("1 - Efectuar Login");
        options.add("2 - Resgistar");
        options.add("3 - Login user");
        options.add("4 - Login admin");

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
        System.out.print("Introduza o username: ");
        String username = this.scanner.next();
        console_clear();
        while(username.equals("") || username.length() >= 25) {
            System.out.println("Username vazio ou com mais de 25 caracters - INVALIDO");
            System.out.print("Introduza o username: ");
            username = this.scanner.next();
            console_clear();
        }
        return username;
    }

    //Metodo para intoducao da password
    public String enterPassword(){
        String password = pass();
        console_clear();
        while(password.equals("")) {
            System.out.println("Password vazia - INVALIDO");
            password = pass();
           console_clear();
        }
        return password;
    }

    private String pass(){
        String password = "";
        char[] charPassword;
        Console console;
        if((console = System.console())!= null){
            charPassword = console.readPassword("Introduza password");
            password = Arrays.toString(charPassword);
        }else{
            System.out.println("No console found");
        }
        return password;
    }
    //Template MainMenu
    public ArrayList<String> optionsMainMenuUser(){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Sair");
        options.add("1 - Menus");
        options.add("2 - Pagamento");
        options.add("3 - Classificao");
        options.add("4 - Livro de Reclamacoes");
        return options;
    }

    public ArrayList<String> optionsMenuUser(){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Voltar");
        options.add("1 - Adicionar produtos");
        options.add("2 - Remover produtos");
        options.add("3 - Ver Carrinho");
        return options;
    }

    public ArrayList<String> optionsMenuTypes(){
        ArrayList<String > types = menuTypes();
        ArrayList<String > options = new ArrayList<>();
        options.add("0 - Voltar");
        options.add("1 - Todos");

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
        options.add("0 - Voltar");
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            str = (i+1 + "- " + row.getColumns().get(1) + " (" + row.getColumns().get(2) + ")");
            options.add(str);
        }
        return options;
    }

    public ArrayList<String> showcart(){
        int qtd;
        String str;
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
            str =  qtd + "x " +irow.getColumns().get(1) + " (" + irow.getColumns().get(2) + ") ";
            if(!results.contains(str)){
                results.add(str);
            }
        }
        return results;
    }

    public ArrayList<String> optionsMainMenuAdmin(){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Sair");
        options.add("1 - Menus");
        options.add("2 - Acessar Contabilidade");
        options.add("3 - Classificoes");
        options.add("4 - Reclamacoes");
        return options;
    }

    public ArrayList<String> optionsMenuAdmin(){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Voltar");
        options.add("1 - Criar Menus");
        options.add("2 - Editar Menus");
        return options;
    }

    public String nameMenu() {
        String name;
        do {
            System.out.println("*-*-*-* Criar um menu *-*-*-*");
            System.out.print("Nome: ");
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
            System.out.println("*-*-*-* Criar um menu *-*-*-*");
            System.out.println("Nome: " + name);
            System.out.print("Preco: ");
            if (fail) {
                System.out.println(error);
            }
            try {
                response = scanner.nextLine();//String
                response_double = Double.parseDouble(response);//int
                console_clear();
                if (response_double < 0) {
                    fail = true;
                    error = "O valor inserido tem que ser entre maior que 0";
                }
            } catch (NumberFormatException exeption) {
                fail = true;
                error = "O valor tem de ser um Int";
            } catch (Exception exeption) {
                fail = true;
                error = "Estamos a ter problemas, Tente mais tarde!";
            }
        } while (response_double < 0);
        return response_double;
    }

    public String typeMenu(String name, BigDecimal price){
        String type;
        do {
            System.out.println("*-*-*-* Criar um menu *-*-*-*");
            System.out.println("Nome: " + name);
            System.out.println("Preco: " + price + '€');
            System.out.print("Tipo: ");
            type = scanner.nextLine();
            console_clear();
        } while (type.length() == 0);
        return type;
    }



    public ArrayList<String> colunumsToOptions(Row menu){
        ArrayList<String> options = new ArrayList<>();
        options.add("0 - Voltar");
        int i;
        for(i = 1; i < menu.getColumns().size(); i++){
            options.add(i +" - " + menu.getColumns().get(i));
        }
        options.add(i + " - Confirmar");
        return options;
    }

    public String insertNewName(Row chosenMenu){
        String title = "*-*-*-* Introduzir nome *-*-*-*";
        String oldName = chosenMenu.getColumns().get(1);
        console_clear();
        System.out.println(title);
        System.out.println("Nome a alterar: " + oldName);
        System.out.print("Novo nome: ");
        String newName = this.scanner.nextLine();
        console_clear();
        while(newName.equals("") || newName.equals(oldName)) {
            System.out.println(title);
            System.out.println("Novo nome vazio ou igual ao antigo!!");
            System.out.println("Nome a alterar: " + oldName);
            System.out.print("Novo nome: ");
            newName = this.scanner.nextLine();
            console_clear();
        }
        return newName;
    }

    public String insertNewPrice(Row chosenMenu){
        String title = "*-*-*-* Introduzir preco *-*-*-*";
        String response;
        Double responseDouble = 0.0;
        String error = "";
        Boolean fail = false;
        Boolean sucess = false;
        do {
            if(fail){
                System.out.println(error);
            }
            System.out.println("Preco antigo: " + chosenMenu.getColumns().get(2) + "€");
            System.out.print("Escolha: ");
            try {
                response = scanner.nextLine();//String
                responseDouble = Double.parseDouble(response);//int
                sucess = true;
            } catch (NumberFormatException exeption) {
                fail = true;
                error = "O valor tem de ser um Int";
            }
        }while(!sucess);
        return responseDouble.toString();
    }

    public String insertNewType(Row chosenMenu){
        String oldType = chosenMenu.getColumns().get(3);
        console_clear();
        System.out.println("Tipo a alterar: " + oldType);
        System.out.print("Novo tipo: ");
        String newType = this.scanner.nextLine();
        console_clear();
        while(newType.equals("") || newType.equals(oldType)) {
            System.out.println("Novo tipo vazio ou igual ao antigo!!");
            System.out.print("Novo tipo: ");
            newType = this.scanner.nextLine();
            console_clear();
        }
        return newType;
    }

}

