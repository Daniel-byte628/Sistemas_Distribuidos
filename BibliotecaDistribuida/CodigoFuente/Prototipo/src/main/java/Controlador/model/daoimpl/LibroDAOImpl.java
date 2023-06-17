package Controlador.model.daoimpl;

import Controlador.conexion.ConexionBD;
import Controlador.model.Peticion;
import Controlador.model.dao.LibroDAO;
import Controlador.model.dto.Libro;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;

import java.util.Calendar;
import java.util.Date;


public class LibroDAOImpl implements LibroDAO {
    private final ConexionBD oracle;

    public LibroDAOImpl() {
        this.oracle = new ConexionBD();
    }

    public boolean devolverCopia(Peticion devolucion) {
        int libroId = Integer.parseInt(devolucion.getISBN());

        ConexionBD conexionBD = new ConexionBD();
        conexionBD.conectar();
        MongoDatabase database = conexionBD.getDatabase();
        try {
            MongoCollection<Document> librosCollection = database.getCollection("Libro");
            Document libroQuery = new Document("ISBN", libroId);

            Document libroDocument = librosCollection.find(libroQuery).first();
            if (libroDocument != null) {
                int copiasDisponibles = libroDocument.getInteger("copiasDisponibles");
                int copiasTotales = libroDocument.getInteger("copiasTotales");

                if (copiasDisponibles < copiasTotales) {
                    Document updatedLibro = librosCollection.findOneAndUpdate(
                            libroQuery,
                            new Document("$inc", new Document("copiasDisponibles", 1)),
                            new FindOneAndUpdateOptions().projection(Projections.include("copiasTotales", "copiasDisponibles"))
                    );

                    if (updatedLibro != null) {
                        insertartransaccion(devolucion, "DEVOLUCION");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexionBD.desconectar();
        }

        return false;
    }

    public boolean realizarPrestamo(Peticion p) {
        int libroId = Integer.parseInt(p.getISBN());

        // Obtener la conexión a la base de datos
        ConexionBD conexionBD = new ConexionBD();
        conexionBD.conectar();
        MongoDatabase database = conexionBD.getDatabase();

        try {
            // Obtener la colección de libros
            MongoCollection<Document> librosCollection = database.getCollection("Libro");

            // Obtener el documento del libro por su ID
            Document libroQuery = new Document("ISBN", libroId);
            Document libroDocument = librosCollection.find(libroQuery).first();

            if (libroDocument != null) {
                int copiasDisponibles = libroDocument.getInteger("copiasDisponibles");
                // Verificar si hay copias disponibles para restar
                if (copiasDisponibles > 0) {
                    System.out.println("paso");
                    // Actualizar la cantidad de copias disponibles en el documento del libro
                    libroDocument.put("copiasDisponibles", copiasDisponibles - 1);

                    // Actualizar el documento del libro en la colección
                    librosCollection.updateOne(libroQuery, new Document("$set", libroDocument));

                    // El préstamo se realizó exitosamente
                    insertartransaccion(p, "PRESTAMO");
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión a la base de datos
            conexionBD.desconectar();
        }

        // El préstamo no se pudo realizar
        return false;
    }


    void insertartransaccion(Peticion p, String solicitud) {
        ConexionBD conexionBD = new ConexionBD();
        conexionBD.conectar();
        MongoDatabase database = conexionBD.getDatabase();

        try {
            MongoCollection<Document> transaccionCollection = database.getCollection("Transaccion");

            Document transaccionDocument = new Document();
            transaccionDocument.append("ISBN", p.getISBN());
            transaccionDocument.append("solicitud", solicitud);
            transaccionDocument.append("fecharealizacion", new Date());

            if (solicitud.equals("PRESTAMO")) {
                // Calcular la fecha de devolución sumando 8 días a la fecha actual
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, 8);
                Date fechaDevolucion = cal.getTime();
                transaccionDocument.append("fechadevolucion", fechaDevolucion);
            } else {
                transaccionDocument.append("fechadevolucion", null);
            }

            transaccionCollection.insertOne(transaccionDocument);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexionBD.desconectar();
        }
    }

}
