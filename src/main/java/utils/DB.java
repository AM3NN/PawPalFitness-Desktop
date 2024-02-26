package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    final String URL="jdbc:mysql://localhost:3306/pawpalfitness";
    final String USER="root";
    final String PWD="";
    Connection connection;
    private static DB instance;

    //CNX BD
    private DB(){
        try {
            connection = DriverManager.getConnection(URL,USER,PWD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static DB getInstance(){
        if (instance == null)
            instance = new DB();
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }
}
