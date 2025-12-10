package es.etg.dam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstitutoOracleXeDAOImp implements InstitutoDAO {

    private Connection conn;
    private final String URL = "jdbc:oracle:thin:%s/%s@localhost:1521/XEPDB1";
    private final String DATABASE_USER = "SYSTEM";
    private final String DATABASE_PASS = "secret";

    public InstitutoOracleXeDAOImp() throws Exception {
        conn = DriverManager.getConnection(
                String.format(URL, DATABASE_USER, DATABASE_PASS)
        );
    }

    @Override
    public void crearTablas() throws SQLException {
        System.out.println("Creación de tablas en Oracle no implementada actualmente.");
    }

    @Override
    public List<Alumno> listarAlumnos() throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public int insertar(Alumno a) throws SQLException {
        return 0;
    }

    @Override
    public int actualizar(Alumno a) throws SQLException {
        return 0;
    }

    @Override
    public int borrar(Alumno a) throws SQLException {
        return 0;
    }

    @Override
    public int insertarAsignatura(Asignatura as) throws SQLException {
        return 0;
    }

    @Override
    public int actualizarAsignatura(Asignatura as) throws SQLException {
        return 0;
    }

    @Override
    public List<Asignatura> listarAsignaturas() throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public void listarAlumnosConAsignaturas() throws SQLException {
        System.out.println("Creación de tablas en Oracle no implementada actualmente.");
    }

    @Override
    public Alumno consultar(String nombre) throws SQLException {
        return null;
    }
}
