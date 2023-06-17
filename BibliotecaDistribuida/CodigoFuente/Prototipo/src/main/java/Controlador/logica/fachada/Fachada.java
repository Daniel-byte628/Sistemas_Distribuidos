package Controlador.logica.fachada;

import Controlador.logica.actores.ActorD;
import Controlador.logica.actores.ActorR;
import Controlador.model.Peticion;
import Controlador.model.bo.LibroBO;
import Controlador.model.bo.TransaccionBO;

public class Fachada {

    // TODO: Implementar funciones de la fachada
    private LibroBO bo;
    private TransaccionBO bo2;

    public Fachada() {
        bo = new LibroBO();
        bo2 = new TransaccionBO();
    }
    //libro
    public void enviarDevolucion(Peticion devolucion) {
        bo.procesarDevolucion(devolucion);
    }

    public void realizarRenovacion(Peticion renovacion){
        bo2.procesarRenovacion(renovacion);
    }
    public boolean realizarPrestamo(Peticion prestamo){return bo.procesarPrestamo(prestamo);}
}
