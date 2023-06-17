package Controlador.conexion;


import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ConexionBD {
    private final String host;
    private final int port;
    private final String database;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public ConexionBD() {
        this.host = "10.43.100.142";
        this.port = 27017;
        this.database = "replicatesing";
        this.mongoClient = null;
        this.mongoDatabase = null;
    }

    public void conectar() {
        try {
            String connectionString = "mongodb://" + host + ":" + port;
            this.mongoClient = MongoClients.create(connectionString);
            this.mongoDatabase = mongoClient.getDatabase(database);
            System.out.println("Conexión establecida correctamente");
        } catch (Exception ex) {
            System.out.println("No se conectó");
            System.out.println("Exception: " + ex.getMessage());
            this.mongoClient = null;
            this.mongoDatabase = null;
        }
    }

    public void desconectar() {
        try {
            this.mongoClient.close();
            System.out.println("Se desconectó");
        } catch (Exception ex) {
            System.out.println("No se desconectó");
            System.out.println("Exception: " + ex.getMessage());
        }
        this.mongoClient = null;
        this.mongoDatabase = null;
    }

    public MongoDatabase getDatabase() {
        if (this.mongoDatabase == null) {
            System.out.println("La conexión es nula");
        }
        return this.mongoDatabase;
    }
}


