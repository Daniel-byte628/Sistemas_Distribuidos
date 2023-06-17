package co.edu.javeriana.logica;

import com.google.gson.Gson;
import org.zeromq.ZMQException;
import org.zeromq.ZMQ.Poller;

import co.edu.javeriana.model.Peticion;
import org.zeromq.ZMQ;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.zeromq.ZMQ;
import org.zeromq.ZMQException;
import org.zeromq.ZMQ.Poller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProcesoSolicitante {
    private ArrayList<Peticion> peticiones = new ArrayList<>();
    private String ip_conexion;
    private String IP_GESTOR1;
    private String IP_GESTOR2;
    private int PUERTO_GESTOR;
    private String archivo;

    public ProcesoSolicitante(String ipGestor1, String ipGestor2, int puertoGC, String archivo) {
        this.IP_GESTOR1 = ipGestor1;
        this.IP_GESTOR2 = ipGestor2;
        this.PUERTO_GESTOR = puertoGC;
        this.archivo = archivo;
    }

    private void setIp_conexion() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String primeraLinea = br.readLine(); // Leer solo la primera línea
            br.close();

            // Realizar las operaciones necesarias con la primera línea leída

            if (primeraLinea.equals("Sede 1")) {
                ip_conexion = IP_GESTOR1;
            } else {
                ip_conexion = IP_GESTOR2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarRequerimientos() {
        LecturaRequerimientos lecturaRequerimientos = new LecturaRequerimientos();
        peticiones = lecturaRequerimientos.leerArchivo(archivo);
        this.setIp_conexion();
    }

   public void enviarRequerimientos() {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            String connectionString = String.format("tcp://%s:%d", IP_GESTOR1, PUERTO_GESTOR);
            socket.connect(connectionString);

            // Configurar poller para realizar la espera con timeout
            ZMQ.Poller poller = context.createPoller(1);
            poller.register(socket, ZMQ.Poller.POLLIN);

            int i = 0;
            for (Peticion peticion : peticiones) {
                i++;
                String json = new Gson().toJson(peticion);
                System.out.println("Enviando petición de " + peticion.getRequerimiento() + " al Gestor.");
                socket.send(json.getBytes(), 0);

                // Esperar por la respuesta con timeout de 5 segundos (5000 ms)
                if (poller.poll(5000) > 0) {
                    byte[] reply = socket.recv(0);
                    System.out.println(i + ") Respuesta del Gestor: " + new String(reply, ZMQ.CHARSET) + "\n");
                } else {
                    System.out.println("Timeout: No se recibió respuesta del Gestor.");
                    // Cerrar el socket y reconectarlo al Gestor 2
                    socket.close();
                    socket = context.createSocket(SocketType.REQ);
                    connectionString = String.format("tcp://%s:%d", IP_GESTOR2, PUERTO_GESTOR);
                    socket.connect(connectionString);
                    poller.unregister(socket);
                    poller.close(); // Cerrar el poller actual
                    poller = context.createPoller(1); // Crear un nuevo poller
                    poller.register(socket, ZMQ.Poller.POLLIN);
                    i--;
                    continue;
                }

            }

            socket.close();
        } catch (ZMQException e) {
            e.printStackTrace();
        }
    }

    public void iniciar() {
        cargarRequerimientos();
        enviarRequerimientos();
    }
}

