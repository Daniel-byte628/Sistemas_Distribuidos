package ejecucion;

import conexion.ConexionBD;
import logica.actores.ActorD;
import logica.actores.ActorP;
import logica.actores.ActorR;

public class Main {

    public static void main(String[] args) {
        // Lanza los hilos de los actores

        System.out.println("Iniciando el sistema de préstamos ...");

        ConexionBD conexion = new ConexionBD();
        conexion.conectar();
        conexion.desconectar();

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
