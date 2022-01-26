package database;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
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
        String SQL_INSERT_PRODUCTS = "INSERT INTO projeto_testes.produtos (nome, preco, tipo) VALUES (?, ?, ?);";
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

}
