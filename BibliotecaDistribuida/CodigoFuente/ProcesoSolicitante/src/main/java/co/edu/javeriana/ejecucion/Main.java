package co.edu.javeriana.ejecucion;

import co.edu.javeriana.ejecucion.LineaComandos;
import co.edu.javeriana.logica.ProcesoSolicitante;

public class Main {
    public static void main(String[] args) {

        LineaComandos lc = new LineaComandos();
        lc.analizarArgumentos(args);

        String archivo = lc.pathRequerimientos + "/requerimientos" + lc.numArchivoRequerimientos + ".txt";

        ProcesoSolicitante procesoSolicitante = new ProcesoSolicitante(lc.ipGestor1, lc.ipGestor2, lc.puertoGC, archivo);
        procesoSolicitante.iniciar();
    }
}
