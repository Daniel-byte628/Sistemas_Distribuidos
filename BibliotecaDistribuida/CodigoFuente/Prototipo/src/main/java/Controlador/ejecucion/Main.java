package Controlador.ejecucion;


import Controlador.conexion.ConexionMongo;
import Controlador.logica.actores.ActorD;
import Controlador.logica.actores.ActorP;
import Controlador.logica.actores.ActorR;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) {
// Lanza los hilos de los actores

/*
        ConexionBD conexion = new ConexionBD();

        // Conectar a la base de datos
        conexion.conectar();

        // Verificar si la conexión se estableció correctamente
        if (conexion.getDatabase() != null) {
            // Realizar operaciones en la base de datos
            MongoDatabase database = conexion.getDatabase();
            // ...

            // Desconectar de la base de datos
            conexion.desconectar();
        }*/

        LineaComandos lc = new LineaComandos();
        lc.analizarArgumentos(args);

        Runnable actorR = new ActorR(lc.ipGestor, lc.puertoRD);
        Runnable actorD = new ActorD(lc.ipGestor, lc.puertoRD);
        Runnable actorP = new ActorP(lc.ipGestor, lc.puertoP);

        Thread hiloActorRD = new Thread(actorR);
        Thread hiloActorD = new Thread(actorD);
        Thread hiloActorP = new Thread(actorP);

        hiloActorRD.start();
        hiloActorD.start();
        hiloActorP.start();







    }
}