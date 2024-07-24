import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect {
    private static final String URL = "jdbc:mysql://localhost:3306/blooddonate";
    private static final String USER = "root";  // Replace with your DB username
    private static final String PASSWORD = "200211";  // Replace with your DB password
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            System.out.println("Connection successful");
            // Perform any further operations with the connection if needed
            connection.close(); // Close the connection when done
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
}


// javac -cp ..\lib\mysql-connector-j-8.4.0.jar;. connect.java
// java -cp ..\lib\mysql-connector-j-8.4.0.jar;. connect