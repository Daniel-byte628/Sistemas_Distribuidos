package model.bo;

import logica.solicitudes.Devolucion;
import model.Peticion;
import model.dto.*;
import model.daoimpl.*;

public class LibroBO {
    LibroDAOImpl libroDAO=new LibroDAOImpl();
    TransaccionDAOImpl transaccionDAO= new TransaccionDAOImpl();

    public void procesarDevolucion(Peticion devolucion) {
        if (libroDAO.devolverCopia(devolucion)) {
            // Si la devolución se realizó correctamente, imprimir un mensaje de éxito
            transaccionDAO.agregarTransaccion(devolucion);
            System.out.println("Se realizó la devolución del libro con ISBN " + devolucion.getISBN());
        } else {
            // Si la devolución falló, imprimir un mensaje de error
            System.out.println("No se pudo realizar la devolución del libro con ISBN " + devolucion.getISBN());
        }
    }

    public void procesarRenovacion(Peticion renovacion) {
        if (libroDAO.actualizarFechaDevolucion(renovacion)) {

            System.out.println("Renovación exitosa para el libro con ISBN " + renovacion.getISBN());
        } else {
            System.out.println("Renovación fallida para el libro con ISBN " + renovacion.getISBN());
        }
    }

}
