package co.edu.javeriana.test;
import co.edu.javeriana.logica.ProcesoSolicitante;

public class ProcesoSolicitanteTest {
    public void iniciar(long numeroHilo, String ipGestor1, String ipGestor2, int puertoGC, String pathArchivosRequerimientos) {
        String archivo = pathArchivosRequerimientos + "/requerimientos" + numeroHilo + ".txt";
        ProcesoSolicitante procesoSolicitante = new ProcesoSolicitante(ipGestor1, ipGestor2, puertoGC, archivo);
        procesoSolicitante.iniciar();
    }
}
