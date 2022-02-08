package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDBConnection {
    private final String dbName;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public JDBConnection(String dbName){
        this.dbName = dbName;
    }

    public void setPreparedStatement(String query){
        try {
            this.preparedStatement = this.connection.prepareStatement(query);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public boolean openMySQL(){
        try{
            //Class.forName("com.mysql.jdbc.driver"); --Drivers antigas
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + this.dbName, "root", "");
            this.connection.setAutoCommit(true);
        }catch(Exception e){
            System.err.println("Expeption on open -- " + e.getClass().getName()+ ": " + e.getMessage());
            return false;
        }
        return true;
    }

    public void closeMySQL(){
        try{
            this.statement.close();
            this.connection.close();
        }catch (Exception e){
            System.err.println("Expeption on open -- " + e.getClass().getName()+ ": " + e.getMessage());
        }
    }

    public void closePSMySQL(){
        try{
            this.preparedStatement.close();
            this.connection.close();
        }catch (Exception e){
            System.err.println("Expeption on open -- " + e.getClass().getName()+ ": " + e.getMessage());
        }
    }

    public ResultSet execute(final String query){
        ResultSet resultset = null;
        if(this.connection != null){
            try{
                this.statement = this.connection.createStatement();
                resultset = statement.executeQuery(query);
            }catch(Exception e){
                System.err.println("Exeption on open -- " + e.getClass().getName()+ ": " + e.getMessage());
            }
        }
        return resultset;
    }

    public List<Row> executeQuery(final String query){
        List<Row> resultsList = new ArrayList<>();
        ResultSet resultSet = this.execute(query);
        try{
            while (resultSet.next()){
                //Creates empty Row object
                Row row = new Row();
                int nColumns = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= nColumns; i++){
                    String element = resultSet.getString(i);
                    row.add(element);
                }
                resultsList.add(row);
            }
        } catch (Exception e){
            System.err.println("Execption on executeQuery -- " + e.getClass().getName() +
                    ": " + e.getMessage());
        }
        return resultsList;

    }

}
