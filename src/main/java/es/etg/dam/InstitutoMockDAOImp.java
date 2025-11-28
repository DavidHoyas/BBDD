package es.etg.dam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstitutoMockDAOImp implements InstitutoDAO {

    @Override
    public List<Alumno> listarAlumnos() throws SQLException {
        List<Alumno> instituto = new ArrayList<>();
        instituto.add(new Alumno("David", "Oliva", 19));
        instituto.add(new Alumno("Sergio", "Simon", 19));
        return instituto;
    }

    @Override
    public int insertar(Alumno a) throws SQLException {
        return 1;
    }

    @Override
    public int actualizar(Alumno a) throws SQLException {
        return 1;
    }

    @Override
    public int borrar(Alumno a) throws SQLException {
        return 1;
    }
}
