package model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
public class Peticion implements Serializable {
    @Setter
    @Getter
    private String ISBN;
    @Setter
    @Getter
    private String requerimiento;
    @Getter
    private Date fechaActual;
    @Getter
    private Date fechaRenovacion;

    public Peticion(String ISBN, String requerimiento) {
        this.ISBN = ISBN;
        this.requerimiento = requerimiento;
        this.fechaActual = null;
        this.fechaRenovacion = null;
    }

    public void setFechaActual() {
        Date fechaActual = new Date();
        this.fechaActual = fechaActual;
    }

    public void setFechaRenovacion() {
        Date fecha = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        fecha = calendar.getTime();
        this.fechaRenovacion = fecha;
    }

    public void setFechaDevolucion() {
        Date fechaDevolucion = new Date();
        this.fechaRenovacion = fechaDevolucion;
    }

    public String toString() {
        return "ISBN de la petición: " + this.ISBN +
                "\nRequerimiento de la petición: " + this.requerimiento +
                "\nFecha de la petición: " + this.fechaActual +
                "\nFecha de renovación de la petición: " + this.fechaRenovacion;
    }

}