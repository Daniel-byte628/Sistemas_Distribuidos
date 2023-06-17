package Controlador.logica.actores;

import Controlador.logica.fachada.Fachada;
import com.google.gson.Gson;
import Controlador.model.Peticion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

@Setter
@Getter
@NoArgsConstructor
public class ActorP implements Runnable {

    private static String DIRECCION_IP_GESTOR_CARGA;
    private static int PUERTO;
    private Fachada fachada=new Fachada();;
    public ActorP(String direccionIpGestorCarga, int puerto) {
        DIRECCION_IP_GESTOR_CARGA = direccionIpGestorCarga;
        PUERTO = puerto;
    }

    public void run() {


        // Conexión con el tópico correspondiente del Gestor de Carga
        try (ZContext context = new ZContext()) {

            // Crear socket de reply para recibir peticiones de los procesos solicitantes
            ZMQ.Socket socket = context.createSocket(SocketType.REP);

            String connectionString = String.format("tcp://%s:%d", DIRECCION_IP_GESTOR_CARGA, PUERTO);
            socket.bind(connectionString);

            System.out.println("Iniciando Actor de Prestamo ...");


            // While infinito (O hasta que se interrumpa el hilo)
            while (!Thread.currentThread().isInterrupted()) {
                byte[] mensajeBytes = socket.recv(0);
                String mensaje = new String(mensajeBytes);
                Peticion peticion = new Gson().fromJson(mensaje, Peticion.class);
                System.out.println("PETICIÓN DE PRÉSTAMO");
                //   System.out.println("mensaje.toString() = " + peticion.toString());

                // TODO Procesar mensaje y buscar/actualizar libro en la BD y réplica correspondiente
                if(fachada.realizarPrestamo(peticion)) {
                    socket.send("OK".getBytes(ZMQ.CHARSET), 0);
                }else {
                    socket.send("NO SE PUDO REALIZAR".getBytes(ZMQ.CHARSET), 0);
                }
            }
        }


    }
}
