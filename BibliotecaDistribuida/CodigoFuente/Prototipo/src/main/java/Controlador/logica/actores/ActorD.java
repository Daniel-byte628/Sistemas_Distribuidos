package Controlador.logica.actores;
import Controlador.conexion.ConexionMongo;
import com.google.gson.Gson;

import Controlador.logica.fachada.Fachada;
import Controlador.model.Peticion;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ActorD implements Runnable {

    private static String DIRECCION_IP_GESTOR_CARGA;
    private static int PUERTO;
    private static final String TOPICO_DEVOLUCION = "DEVOLUCION";
    private Fachada fachada=new Fachada();

    public ActorD(String direccionIpGestorCarga, int puerto) {
        DIRECCION_IP_GESTOR_CARGA = direccionIpGestorCarga;
        PUERTO = puerto;

    }

    public void run() {

        // Conexión con el tópico correspondiente del Gestor de Carga
        try (ZContext context = new ZContext()) {

            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            String connectionString = String.format("tcp://%s:%d", DIRECCION_IP_GESTOR_CARGA, PUERTO);
            subscriber.connect(connectionString);

            subscriber.subscribe(TOPICO_DEVOLUCION.getBytes(ZMQ.CHARSET));

            System.out.println("Iniciando Actor de Devolución ...");

            // While infinito (O hasta que se interrumpa el hilo)
            while (!Thread.currentThread().isInterrupted()) {
                byte[] topicoBytes = subscriber.recv(0);
                String topico = new String(topicoBytes, StandardCharsets.UTF_8);
                byte[] mensajeBytes = subscriber.recv(0);
                String mensaje = new String(mensajeBytes);
                Peticion peticion = new Gson().fromJson(mensaje, Peticion.class);

                System.out.println("TÓPICO DE DEVOLUCIÓN");
                System.out.println("mensaje.toString() = " + peticion.toString());

                // TODO Procesar mensaje y actualizar BD y réplica correspondiente

                fachada.enviarDevolucion(peticion);
                //ConexionMongo objetoConexion = new ConexionMongo();
                //objetoConexion.crearConexion();

                //fachada.enviarDevolucion(peticion);



            }
            subscriber.close();
        }

    }
}
