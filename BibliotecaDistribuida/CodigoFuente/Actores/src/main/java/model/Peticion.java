package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Peticion implements Serializable {
    private String ISBN;
    private String requerimiento;
    private Date fechaActual;
    private Date fechaRenovacion;

    public String toString() {
        return "ISBN de la petición: " + this.ISBN +
                "\nRequerimiento de la petición: " + this.requerimiento +
                "\nFecha de la petición: " + this.fechaActual +
                "\nFecha de renovación de la petición: " + this.fechaRenovacion;
    }

}