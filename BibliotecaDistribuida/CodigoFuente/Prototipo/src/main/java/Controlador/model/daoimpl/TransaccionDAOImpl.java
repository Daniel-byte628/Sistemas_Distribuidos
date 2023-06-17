package Controlador.model.daoimpl;

import Controlador.conexion.ConexionBD;
import Controlador.model.Peticion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class TransaccionDAOImpl {

    // TODO Logica de acceso a datos
    private final ConexionBD oracle;

    public TransaccionDAOImpl() {
        this.oracle = new ConexionBD( );
    }

    private static final String INSERT_TRANSACCION_SQL = "INSERT INTO TRANSACCION (id, isbn, solicitud, FECHAPRESTAMO, fechadevolucion) VALUES (?, ?, ?, ?, ?)";


    public boolean actualizarFecha(Peticion p) {
        // Obtener el ISBN de la petición
        int libroId = Integer.parseInt(p.getISBN());

        // Obtener la conexión a la base de datos
        ConexionBD conexionBD = new ConexionBD();
        conexionBD.conectar();
        MongoDatabase database = conexionBD.getDatabase();

        try {
            // Obtener la colección de libros
            MongoCollection<Document> librosCollection = database.getCollection("Libro");
            System.out.println("Obteniendo la colección de libros: " + librosCollection);
            System.out.println("Obteniendo  ");
            // Verificar si el libro existe en la base de datos
            Document libroQuery = new Document("ISBN", libroId);
            Document libro = librosCollection.find(libroQuery).first();

            if (libro != null) {
                // Obtener la colección de transacciones
                MongoCollection<Document> transaccionCollection = database.getCollection("Transaccion");

                // Obtener la fecha actual
                Date fechaActual = new Date();

                // Calcular la nueva fecha de devolución sumando 8 días a la fecha actual
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaActual);
                cal.add(Calendar.DAY_OF_MONTH, 8);
                Date fechaDevolucion = (Date) cal.getTime();

                // Crear el documento de la nueva transacción
                Document transaccionDocument = new Document();
                transaccionDocument.append("ISBN", libroId);
                transaccionDocument.append("solicitud", "RENOVACION");
                transaccionDocument.append("fecharealizacion", fechaActual);
                transaccionDocument.append("fechadevolucion", fechaDevolucion);

                // Insertar la nueva transacción
                transaccionCollection.insertOne(transaccionDocument);

                // La inserción fue exitosa
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión a la base de datos
            conexionBD.desconectar();
        }

        // La inserción no fue exitosa o el libro no existe en la base de datos
        return false;
    }




}

