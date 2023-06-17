package logica.fachada;

import logica.actores.ActorD;
import logica.actores.ActorR;
import logica.solicitudes.Devolucion;
import model.Peticion;
import model.bo.LibroBO;
import model.bo.TransaccionBO;

public class Fachada {

    // TODO: Implementar funciones de la fachada
    private LibroBO bo;
    private TransaccionBO bo2;

    public Fachada() {
        bo = new LibroBO();
        bo2 = new TransaccionBO();
    }

    public void enviarDevolucion(Peticion devolucion) {
        bo.procesarDevolucion(devolucion);
    }

    public void realizarRenovacion(Peticion renovacion){
        bo.procesarRenovacion(renovacion);
    }
    public void realizarPrestamo(Peticion prestamo){




    }
}
