package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    private String ISBN;
    private String titulo;
    private String autor;
    private int anio;
    private int copiasTotales;
    private int copiasDisponibles;


    public String toString() {
        return "ISBN: " + this.ISBN +
                "\nTitulo: " + this.titulo +
                "\nAutor: " + this.autor +
                "\nAño: " + this.anio +
                "\nCopias totales: " + this.copiasTotales +
                "\nCopias disponibles: " + this.copiasDisponibles;
    }

}
