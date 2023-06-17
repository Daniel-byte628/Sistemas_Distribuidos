package Controlador.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {

    private int id;
    private String ISBN;
    private String solcitud;
    private Date fechaPrestamo;
    private Date fechaDevolucion;

    public String toString() {
        return "ID: " + this.id +
                "\nISBN: " + this.ISBN +
                "\nSolicitud: " + this.solcitud +
                "\nFecha de prestamo: " + this.fechaPrestamo +
                "\nFecha de devolucion: " + this.fechaDevolucion;
    }
}
