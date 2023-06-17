package Controlador.model.bo;

import Controlador.model.Peticion;
import Controlador.model.daoimpl.TransaccionDAOImpl;

public class TransaccionBO {


    TransaccionDAOImpl transaccionDAO= new TransaccionDAOImpl();

    public void procesarRenovacion(Peticion renovacion) {
        if (transaccionDAO.actualizarFecha(renovacion)) {
            System.out.println("Renovación exitosa para el libro con ISBN " + renovacion.getISBN());
        } else {
            System.out.println("Renovación fallida para el libro con ISBN " + renovacion.getISBN());
        }
    }
}
