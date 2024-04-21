package com.project.layer.Persistence.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/FourParksDatabase?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "fpuser";
    private static final String PASSWORD = "fppassword";

    private Connection connection;

    public ConnectionService() {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            System.out.println("¡Conexión exitosa a la base de datos!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver de MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ConnectionService connectionService = new ConnectionService();
        Connection connection = connectionService.getConnection();

        // Aquí puedes utilizar 'connection' para realizar consultas u operaciones con la base de datos
        
        // No olvides cerrar la conexión al terminar
        connectionService.closeConnection();
    }
}
