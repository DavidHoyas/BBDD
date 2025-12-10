package es.etg.dam;

import java.sql.SQLException;
import java.util.List;

public interface InstitutoDAO {

    void crearTablas() throws SQLException;

    List<Alumno> listarAlumnos() throws SQLException;

    int insertar(Alumno a) throws SQLException;

    int actualizar(Alumno a) throws SQLException;

    int borrar(Alumno a) throws SQLException;

    // ---- NUEVA TABLA ----
    int insertarAsignatura(Asignatura as) throws SQLException;

    int actualizarAsignatura(Asignatura as) throws SQLException;

    List<Asignatura> listarAsignaturas() throws SQLException;

    // Relación
    void listarAlumnosConAsignaturas() throws SQLException;

    // Consulta con parámetro
    Alumno consultar(String nombre) throws SQLException;
}
