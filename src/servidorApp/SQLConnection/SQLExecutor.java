package servidorApp.SQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLExecutor {
    SQLConnector dbConector;
    PreparedStatement statement;
    ResultSet resultSet;

    public SQLExecutor(String user, String password) {
        try{
            dbConector = SQLConnector.getInstance(user, password);
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    //Para modificar la base de datos
    public void prepareStatement(String[] parametros){
        try{

            statement = dbConector.getConnection().prepareStatement(parametros[0]);
            for(int i = 1; i < parametros.length; i++){
                statement.setString(i, parametros[i]);
            }
            statement.executeUpdate(); //Delete, Update, Insert
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    // Al consultar el query se obtiene n cantidad de columnas y filas
    // que representan los registros, o bien, en JAVA resultSets
    public ResultSet ejecutaQuery(String sql){
        try{
            statement = dbConector.getConnection().prepareStatement(sql);
            resultSet = statement.executeQuery(); // SELECT
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }
}
