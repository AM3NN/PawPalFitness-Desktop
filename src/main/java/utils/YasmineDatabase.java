package utils;
import java.sql.*;


public class YasmineDatabase {
    final String URL = "jdbc:mysql://localhost:3306/pawpalfitness" ;
    final String USER = "root";
    final String PASS ="";

    private Connection connection;
    private static YasmineDatabase instance;
    private YasmineDatabase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

        public static YasmineDatabase getInstance(){
            if (instance == null)
                instance=new YasmineDatabase();
            return instance;
        }

        public Connection getConnection(){
            return connection;
        }
}
