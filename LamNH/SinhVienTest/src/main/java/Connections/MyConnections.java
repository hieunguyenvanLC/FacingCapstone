package Connections;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnections implements Serializable {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/sinhvien?useUnicode=yes&characterEncoding=UTF-8";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "123456";
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void init() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        System.out.println("Connecting to DB");
        conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
    }
}
