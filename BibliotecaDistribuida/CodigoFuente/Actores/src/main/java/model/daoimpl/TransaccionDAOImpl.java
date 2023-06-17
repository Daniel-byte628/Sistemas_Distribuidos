package model.daoimpl;

import conexion.ConexionBD;
import model.Peticion;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransaccionDAOImpl {

    // TODO Logica de acceso a datos
    private final ConexionBD oracle;

    public TransaccionDAOImpl() {
        this.oracle = new ConexionBD();
    }

    private static final String INSERT_TRANSACCION_SQL = "INSERT INTO TRANSACCION (id, isbn, solicitud, FECHAPRESTAMO, fechadevolucion) VALUES (?, ?, ?, ?, ?)";

    public boolean agregarTransaccion(Peticion peticion) {

        return false;
    }


}
