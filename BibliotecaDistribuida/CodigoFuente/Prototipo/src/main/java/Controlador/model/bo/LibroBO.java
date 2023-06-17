package Controlador.model.bo;

import Controlador.model.Peticion;
import Controlador.model.dto.*;
import Controlador.model.daoimpl.*;

public class LibroBO {
    LibroDAOImpl libroDAO=new LibroDAOImpl();

    public void procesarDevolucion(Peticion devolucion) {
        if (libroDAO.devolverCopia(devolucion)) {
            // Si la devolución se realizó correctamente, imprimir un mensaje de éxito
            System.out.println("Se realizó la devolución del libro con ISBN " + devolucion.getISBN());
        } else {
            // Si la devolución falló, imprimir un mensaje de error
            System.out.println("No se pudo realizar la devolución del libro con ISBN " + devolucion.getISBN());
        }
    }


    public boolean procesarPrestamo(Peticion prestamo) {
        if (libroDAO.realizarPrestamo(prestamo)) {
           return true;
        } else {
           return false;
        }
    }
}