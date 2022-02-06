package database;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.company.User;

public class Query {
    private final JDBConnection db;

    public Query(JDBConnection db){
        this.db = db;
    }

    public String testLogin(User user){
        boolean connectionIsOpen = this.db.openMySQL();
        if (connectionIsOpen){
            List<Row> rows = this.db.executeQuery("SELECT * FROM login ");
            for(Row row: rows){
                if(user.getUsername().equals(row.getColumns().get(0)) && user.getPassword().equals(row.getColumns().get(1))){
                    return row.getColumns().get(2);
                }
            }
            this.db.closeMySQL();
        }
        return "Error";
    }

    public List<Row> menuAllTypes(){
        boolean connectionIsOpen = this.db.openMySQL();
        List<Row> rows =new ArrayList<>();
        if (connectionIsOpen){
            rows = this.db.executeQuery("SELECT tipo FROM produtos ");
            this.db.closeMySQL();
            return rows;
        }
        return rows;
    }

    public List<Row> menuSee(String type){
        boolean connectionIsOpen = this.db.openMySQL();
        List<Row> rows =new ArrayList<>();
        if (connectionIsOpen){//                                                           UNICA CENA A MUDAR ENTRE AS QUERYS
            rows = this.db.executeQuery("SELECT * FROM produtos WHERE produtos.tipo = \"" + type + "\"");
            this.db.closeMySQL();
            return rows;
        }
        return rows;
    }

    public List<Row> menuSeeAll(){
        boolean connectionIsOpen = this.db.openMySQL();
        List<Row> rows =new ArrayList<>();
        if (connectionIsOpen){
            rows = this.db.executeQuery("SELECT * FROM produtos ");
            this.db.closeMySQL();
            return rows;
        }
        return rows; 
    }

    public void addMenu(String name, BigDecimal price, String type){
        boolean connectionIsOpen = this.db.openMySQL();
        String SQL_INSERT_PRODUCTS = "CALL sp_insert_products(?, ?, ?);";
        if (connectionIsOpen){
            try {
                this.db.setPreparedStatement(SQL_INSERT_PRODUCTS);
                PreparedStatement preparedStatement = this.db.getPreparedStatement();
                preparedStatement.setString(1, name);
                preparedStatement.setBigDecimal(2, price);
                preparedStatement.setString(3, type);
                preparedStatement.execute();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            this.db.closePSMySQL();
        }
    }

    public void editMenu(int id, String name, Double price, String type){
        boolean connectionIsOpen = this.db.openMySQL();
        String SQL_INSERT_PRODUCTS = "CALL sp_update_menu(?, ?, ?, ?)";
        if (connectionIsOpen){
            try {
                this.db.setPreparedStatement(SQL_INSERT_PRODUCTS);
                PreparedStatement preparedStatement = this.db.getPreparedStatement();
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setBigDecimal(3, BigDecimal.valueOf(price));
                preparedStatement.setString(4, type);
                System.out.println("Executing query : " + preparedStatement);
                preparedStatement.execute();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            this.db.closePSMySQL();
        }
    }

    public void register(String username , String password){
        boolean connectionIsOpen = this.db.openMySQL();
        String SQL_INSERT_LOGIN = "INSERT INTO login (username, password) VALUES(?,?)";
        if (connectionIsOpen){
            try {
                this.db.setPreparedStatement(SQL_INSERT_LOGIN);
                PreparedStatement preparedStatement = this.db.getPreparedStatement();
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                System.out.println("Executing query : " + preparedStatement);
                preparedStatement.execute();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            this.db.closePSMySQL();
        }
    }

    public void createPedido(User user){
        boolean connectionIsOpen = this.db.openMySQL();
        String SQL_INSERT_PEDIDO = "INSERT INTO pedido (username, total) VALUES(?,?)";
        if (connectionIsOpen){
            try {
                this.db.setPreparedStatement(SQL_INSERT_PEDIDO);
                PreparedStatement preparedStatement = this.db.getPreparedStatement();
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setBigDecimal(2, BigDecimal.valueOf(user.getTotalSpent()));

                System.out.println("Executing query : " + preparedStatement);
                preparedStatement.execute();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            this.db.closePSMySQL();
        }
        int pedidoID = lastIdFromPedido(user);
        addCartToPedido(user, pedidoID);
    }

    public int lastIdFromPedido(User user) {
        ResultSet resultSet;
        int idPedido = 0;
        boolean connectionIsOpen = this.db.openMySQL();
        String SQL_INSERT_PRODUCTS_PEDIDO = "CALL sp_last_pedido_ID_from_user(?)";
        if (connectionIsOpen){
            try {
                this.db.setPreparedStatement(SQL_INSERT_PRODUCTS_PEDIDO);
                PreparedStatement preparedStatement = this.db.getPreparedStatement();
                preparedStatement.setString(1, user.getUsername());

                System.out.println("Executing query : " + preparedStatement);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    idPedido = resultSet.getInt(1);
                }
                //idPedido = resultSet.getInt(0);
            }catch (SQLException e) {
                e.printStackTrace();
            }
            this.db.closePSMySQL();
        }

        return idPedido;
    }

    public void addCartToPedido(User user, int idOrder) {
        ArrayList<Row> cart = user.getCart();
        ArrayList<Row> checked = new ArrayList<>();
        int qtd;
        for (int i = 0; i < cart.size(); i++) {
            qtd = 0;
            Row iRow = cart.get(i);
            for (Row jRow : cart) {
                if (Integer.parseInt(jRow.getColumns().get(0)) == Integer.parseInt(iRow.getColumns().get(0))) {
                    qtd += 1;
                }
            }
            if (!checked.contains(iRow)) {
                checked.add(iRow);
                int idProduct = Integer.parseInt(iRow.getColumns().get(0));
                addProductToPedido(idOrder, idProduct, qtd);
            }
        }
    }

    public void addProductToPedido(int idOrder, int idProduct, int qtd){
        boolean connectionIsOpen = this.db.openMySQL();
        String SQL_INSERT_PRODUCTS_PEDIDO = "INSERT INTO produtospedido (id_pedido, id_produto, quantidade) VALUES(?,?,?)";
        if (connectionIsOpen){
            try {
                this.db.setPreparedStatement(SQL_INSERT_PRODUCTS_PEDIDO);
                PreparedStatement preparedStatement = this.db.getPreparedStatement();
                preparedStatement.setInt(1, idOrder);
                preparedStatement.setInt(2, idProduct);
                preparedStatement.setInt(3, qtd);

                System.out.println("Executing query : " + preparedStatement);
                preparedStatement.execute();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            this.db.closePSMySQL();
        }
    }
}
