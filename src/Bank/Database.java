package Bank;

import java.sql.*;
import java.util.List;

public interface Database {
    void connect();
    void disconnect();
    void executeQuery(String query);
    void executeUpdate(String query);
    ResultSet executeSelectQuery(String query);
    List<String>  loadall();
}
