package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private final String host;
    private final String port;
    private final String user;
    private final String pass;
    private final String database;

    private Connection connection;

    public ConexionBD(){
        this.host = "localhost";
        this.port = "3306";
        this.user = "root";
        this.pass = "BrotherStrong2817";
        this.database = "sys";
        this.connection = null;
    }


    public void conectar() {
        try {
            String path = "jdbc:mysql://" + host + ":" + port + "/" + database;
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(path, this.user, this.pass);
            System.out.println("Conexión establecida correctamente");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No se conectó");
            System.out.println("Exception: "+ ex.getMessage());
            this.connection=null;
        }
    }
    public void desconectar(){
        try{
            this.connection.close();
            System.out.println("Se desconectó");
        }catch (SQLException ex){
            System.out.println("No se desconectó");
            System.out.println("SQLException" + ex.getMessage());
            System.out.println("SQLState: "+ ex.getSQLState());
            System.out.println("VendoError: "+ ex.getErrorCode());
        }
        this.connection=null;
    }


    public Connection getConnection(){
        if (this.connection == null) {
            System.out.println("La conexión es nula");
        }
        return this.connection;
    }


}
