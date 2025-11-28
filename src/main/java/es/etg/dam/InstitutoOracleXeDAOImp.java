package es.etg.dam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstitutoOracleXeDAOImp implements InstitutoDAO {

    private Connection conn;
    private final String URL = "jdbc:oracle:thin:%s/%s@localhost:1521/XEPDB1";
    private final String DATABSE_USER = "SYSTEM";
    private final String DATABSE_PASS = "secret";

    public InstitutoOracleXeDAOImp() throws Exception {
        conn = DriverManager.getConnection(String.format(URL, DATABSE_USER, DATABSE_PASS));
    }

    @Override
    public List<Alumno> listarAlumnos() throws SQLException {
        List<Alumno> instituto = new ArrayList<>();
        instituto.add(new Alumno("Aaron", "Gomez", 20));
        instituto.add(new Alumno("Alejandro", "Rodriguez", 20));
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
