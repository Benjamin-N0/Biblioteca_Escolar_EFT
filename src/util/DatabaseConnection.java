package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static DatabaseConnection instancia;

    private Connection conexion;

    private DatabaseConnection() {

        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa.");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {

        if (instancia == null) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    public Connection getConnection() {
        return conexion;
    }
}
