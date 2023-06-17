package Logica;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Peticion;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Gestor {
    private static int PUERTO_PS;
    private static int PUERTO_RD;
    private static int PUERTO_P;

    public Gestor(int puertoSP, int puertoRD, int puertoP) {
        PUERTO_PS = puertoSP;
        PUERTO_RD = puertoRD;
        PUERTO_P = puertoP;
    }

    public void iniciar() {
        try (ZContext context = new ZContext()) {

            String respuestaProcesoSolicitante = "";
            String peticionJson = "";
            // Crear socket de reply para recibir peticiones de los procesos solicitantes
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://*:" + PUERTO_PS);

            // Crear sockets de publicación para devolución y renovación
            ZMQ.Socket renovacionDevolucion = context.createSocket(SocketType.PUB);
            renovacionDevolucion.bind("tcp://*:" + PUERTO_RD);

            System.out.println("Iniciando Gestor de Carga ...");

            // While infinito (O hasta que se interrumpa el hilo)
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Esperando mensaje de los procesos solicitantes...");
                byte[] mensajeBytes = socket.recv(0);
                String mensaje = new String(mensajeBytes);
                Peticion peticion = new Gson().fromJson(mensaje, Peticion.class);

                System.out.println("Libro recibido: " + peticion.getISBN());
                System.out.println("Petición recibida: " + peticion.getRequerimiento());


                peticion.setFechaActual();

                if (!peticion.getRequerimiento().equals("DEVOLUCION"))
                    peticion.setFechaRenovacion();
                else
                    peticion.setFechaDevolucion();
                peticionJson = new Gson().toJson(peticion);

                switch (peticion.getRequerimiento()) {

                    case "PRESTAMO":
                        System.out.println("Enviando petición de préstamo al Actor de Préstamo");
                        socket.send(consultarLibro(peticion).getBytes(ZMQ.CHARSET), 0);


                        break;

                    case "RENOVACION":
                        System.out.println("Enviando petición de renovacion al Actor de Renovación");
                        renovacionDevolucion.sendMore(peticion.getRequerimiento().getBytes(ZMQ.CHARSET));
                        renovacionDevolucion.send(peticionJson.getBytes(ZMQ.CHARSET), 0);

                        respuestaProcesoSolicitante = "Libro con ISBN " + peticion.getISBN() + ". Enviando petición de renovacion al Actor de Renovación";
                        socket.send(respuestaProcesoSolicitante.getBytes(ZMQ.CHARSET), 0);


                        break;

                    case "DEVOLUCION":

                        //TODO: Quitar luego
                        System.out.println("Fecha: " + peticion);

                        respuestaProcesoSolicitante = "Libro con ISBN "+ peticion.getISBN() + ". Enviando petición de devolución al Actor de Devolución";
                        socket.send(respuestaProcesoSolicitante.getBytes(ZMQ.CHARSET), 0);

                        System.out.println("Enviando petición de devolución al Actor de Devolución");
                        renovacionDevolucion.sendMore(peticion.getRequerimiento().getBytes(ZMQ.CHARSET));
                        renovacionDevolucion.send(peticionJson.getBytes(ZMQ.CHARSET), 0);
                        break;

                    default:
                        respuestaProcesoSolicitante = "Petición no válida";
                        socket.send(respuestaProcesoSolicitante.getBytes(ZMQ.CHARSET), 0);
                        break;
                }
            }
            socket.close();
            renovacionDevolucion.close();
        }
    }

    private String consultarLibro(Peticion peticion) {
        String mensaje = "No se pudo establecer conexión con el Actor de Préstamo";
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(ZMQ.REQ);
            socket.connect("tcp://localhost:" + PUERTO_P);
            socket.send(new Gson().toJson(peticion).getBytes(ZMQ.CHARSET), 0);
            byte[] mensajeBytes = socket.recv(0);
            mensaje = new String(mensajeBytes);
            System.out.println("Respuesta del Actor de Préstamo: " + mensaje);
            socket.close();
        }
        return mensaje;
    }
}
