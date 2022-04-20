package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    private Connection connection;
    public JDBCUtils(){

    }
    public Connection getConnection() {

        try {
            // Load the JDBC driver
            String driverName = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driverName);

            // Create a connection to the database
            String serverName = "127.0.0.1";
            String portNumber = "1521";
            String sid = "xe";
            String url = null;
            url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
            String username = "system";
            String password = "19120644@Tam";

            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return connection;
    }

}
