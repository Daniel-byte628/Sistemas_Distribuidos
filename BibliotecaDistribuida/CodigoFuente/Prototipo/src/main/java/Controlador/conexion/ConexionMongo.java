package Controlador.conexion;

import com.mongodb.MongoException;
import com.mongodb.MongoClient;

import java.util.List;

public class ConexionMongo {

    public MongoClient crearConexion() {
        MongoClient mongo = null;
        String servidor = "10.43.100.140";
        Integer puerto = 27017;

        try {
            mongo = new MongoClient(servidor, puerto);
            List<String> nombresBasesdatos = mongo.getDatabaseNames();
            System.out.println("Se conectó correctamente a la base de datos de Mongo");
        } catch (MongoException e){
            System.out.println("Error en la conexión a la base de datos de Mongo");
        }

        return mongo;
    }
}
