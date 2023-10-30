package Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Oracledb implements Database {
    private Connection connection;

    public void connect() {
        try {
        	String username = "Ahmad", password="123";
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/Bank", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String query) {
    	try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String query) {
    	 try (Statement statement = connection.createStatement()) {
             statement.executeUpdate(query);
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }

    public ResultSet executeSelectQuery(String query) {
    	 try (Statement statement = connection.createStatement()) {
             return statement.executeQuery(query);
         } catch (SQLException e) {
             e.printStackTrace();
             return null;
         }
    }
    
    public List<String> loadall(){
    	return null;
    }

}
