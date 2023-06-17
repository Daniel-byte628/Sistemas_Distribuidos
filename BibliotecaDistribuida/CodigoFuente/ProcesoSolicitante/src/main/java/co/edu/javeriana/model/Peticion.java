package co.edu.javeriana.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class Peticion implements Serializable {
    private String ISBN;
    private String requerimiento;
    private Date fechaActual;
    private Date fechaRenovacion;

    public Peticion(String ISBN, String requerimiento) {
        this.ISBN = ISBN;
        this.requerimiento = requerimiento;
        this.fechaActual = null;
        this.fechaRenovacion = null;
    }

    public String toString() {
        return "ISBN de la petición: " + this.ISBN +
                "\nRequerimiento de la petición: " + this.requerimiento +
                "\nFecha de la petición: " + this.fechaActual +
                "\nFecha de renovación de la petición: " + this.fechaRenovacion;
    }

}