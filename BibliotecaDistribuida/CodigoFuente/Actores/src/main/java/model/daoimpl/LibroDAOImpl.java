package model.daoimpl;

import conexion.ConexionBD;
import model.Peticion;
import model.dao.LibroDAO;
import model.dto.Libro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

public class LibroDAOImpl implements LibroDAO {
    private final ConexionBD oracle;

    public LibroDAOImpl() {
        this.oracle = new ConexionBD();
    }
    public boolean devolverCopia(Peticion devolucion) {

        // Obtener el ISBN del libro a devolver
        String isbn = devolucion.getISBN();

        try {
            // Validar si el ISBN es nulo o vacío
            if (isbn == null || isbn.isEmpty()) {
                System.out.println("Error: ISBN es un campo requerido.");
                return false; // Salir de la función
            }

            // Obtener una conexión a la base de datos
            this.oracle.conectar();

            // Verificar si el libro existe y obtener el valor actual de COPIAS_DISPONIBLES
            String sqlSelect = "SELECT COPIASDISPONIBLES FROM LIBRO WHERE ISBN = ?";
            PreparedStatement stmtSelect = this.oracle.getConnection().prepareStatement(sqlSelect);
            stmtSelect.setString(1, isbn);
            ResultSet rs = stmtSelect.executeQuery();

            int copiasDisponibles = 0;
            int copiasTotales = 0;

            if (rs.next()) {
                copiasDisponibles = rs.getInt("COPIASDISPONIBLES");

                // Verificar si ya no hay copias disponibles para devolver
                if (copiasDisponibles <= 0) {
                    System.out.println("Error: No hay copias disponibles para devolver.");
                    return false; // Salir de la función
                }

                // Obtener el valor actual de COPIASTOTALES
                String sqlSelectTotal = "SELECT COPIASTOTALES FROM LIBRO WHERE ISBN = ?";
                PreparedStatement stmtSelectTotal = this.oracle.getConnection().prepareStatement(sqlSelectTotal);
                stmtSelectTotal.setString(1, isbn);
                ResultSet rsTotal = stmtSelectTotal.executeQuery();

                if (rsTotal.next()) {
                    copiasTotales = rsTotal.getInt("COPIASTOTALES");

                    // Verificar si el número de copias disponibles después de la devolución excede el límite de copias totales
                    if (copiasDisponibles + 1 > copiasTotales) {
                        System.out.println("Error: El número de copias disponibles después de la devolución excede el límite de copias totales.");
                        return false; // Salir de la función
                    }
                }

                rsTotal.close();
                stmtSelectTotal.close();

                // Actualizar las copias disponibles en la tabla LIBROS
                String sqlUpdate = "UPDATE LIBRO SET COPIASDISPONIBLES = COPIASDISPONIBLES + 1 WHERE ISBN = ?";
                PreparedStatement stmtUpdate = this.oracle.getConnection().prepareStatement(sqlUpdate);
                stmtUpdate.setString(1, isbn);
                int filasActualizadas = stmtUpdate.executeUpdate();
                stmtUpdate.close();

                if (filasActualizadas > 0) {
                    System.out.println("Devolución registrada exitosamente.");
                    return true;
                } else {
                    System.out.println("Error al registrar la devolución.");
                    return false;
                }
            } else {
                System.out.println("Error: No se encontró el libro con el ISBN proporcionado.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos: " + e.getMessage());
            return false;
        }
    }



    public boolean actualizarFechaDevolucion(Peticion p) {
        try {
            String isbn=p.getISBN();
            this.oracle.conectar();
            String selectQuery = "SELECT FECHADEVOLUCION FROM TRANSACCION WHERE ISBN = ?";
            PreparedStatement selectPstmt = this.oracle.getConnection().prepareStatement(selectQuery);
            selectPstmt.setString(1, isbn);
            ResultSet rs = selectPstmt.executeQuery();

            if (rs.next()) {
                Date fechaActual = rs.getDate("FECHADEVOLUCION");

                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaActual);
                cal.add(Calendar.DATE, 8);
                Date fechaRenovacion = cal.getTime();
                p.setFechaRenovacion(fechaRenovacion);

                String updateQuery = "UPDATE TRANSACCION SET FECHADEVOLUCION = ? WHERE ISBN = ?";
                PreparedStatement updatePstmt = this.oracle.getConnection().prepareStatement(updateQuery);
                updatePstmt.setDate(1, new java.sql.Date(fechaRenovacion.getTime()));
                updatePstmt.setString(2, isbn);

                int rowsUpdated = updatePstmt.executeUpdate();

                updatePstmt.close();
                rs.close();
                selectPstmt.close();
                this.oracle.desconectar();

                if (rowsUpdated > 0) {
                    System.out.println("Fecha de renovacion: " + fechaRenovacion);
                    System.out.println("Se actualizó la fecha de devolución del libro con ISBN " + isbn);
                    return true;
                } else {
                    System.out.println("No se pudo actualizar la fecha de devolución del libro con ISBN " + isbn);
                    return false;
                }
            } else {
                System.out.println("No se encontró el libro con ISBN " + isbn);
                rs.close();
                selectPstmt.close();
                this.oracle.desconectar();
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }








    public Libro finById(String iduser) {
        Libro lib = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            this.oracle.conectar();
            String query = "SELECT * FROM LIBRO WHERE ISBN = ?";
            pstmt = this.oracle.getConnection().prepareStatement(query);
            pstmt.setString(1, iduser);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                lib = new Libro(rs.getString("ISBN"),
                        rs.getString("TITULO"),
                        rs.getString("AUTOR"),
                        rs.getInt("ANIO"),
                        rs.getInt("COPIASDISPONIBLES"),
                        rs.getInt("COPIASTOTALES"));
                System.out.println("Se obtuvo el libro");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LibroDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.oracle.desconectar();
        }
        return lib;
    }


}
