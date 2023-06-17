package ejecucion;

import Logica.Gestor;
import ejecucion.LineaComandos;

public class Main {
    public static void main(String[] args) {
        // Lanza el gestor

        LineaComandos lc = new LineaComandos();
        lc.analizarArgumentos(args);

        Gestor gestor = new Gestor(lc.puertoPS, lc.puertoRD, lc.puertoP);
        gestor.iniciar();

    }
}
