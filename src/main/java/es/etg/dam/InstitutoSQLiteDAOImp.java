package es.etg.dam;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstitutoSQLiteDAOImp implements InstitutoDAO {

    private static final String DATABASE_NAME = "mibasedatos.db";
    private static final String JDBC_URL = "jdbc:sqlite:%s";

    private Connection conn;

    public InstitutoSQLiteDAOImp() throws Exception {
        URL resource = InstitutoSQLiteDAOImp.class.getResource(DATABASE_NAME);
        String path = new File(resource.toURI()).getAbsolutePath();
        String url = String.format(JDBC_URL, path);
        conn = DriverManager.getConnection(url);
    }

    @Override
    public List<Alumno> listarAlumnos() throws SQLException {
        final String query = "SELECT nombre, apellido, edad FROM instituto";

        List<Alumno> alumnos = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            int edad = rs.getInt("edad");

            Alumno a = new Alumno(nombre, apellido, edad);
            alumnos.add(a);
        }

        rs.close();
        ps.close();

        return alumnos;

    }

    public int insertar(Alumno a) throws SQLException {
        int numRegistrosActualizados = 0;
        final String sql = "INSERT INTO instituto (nombre, apellido, edad) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, a.getNombre());
        ps.setString(2, a.getApellido());
        ps.setInt(3, a.getEdad());

        numRegistrosActualizados = ps.executeUpdate();
        ps.close();

        return numRegistrosActualizados;
    }

    public int actualizar(Alumno a) throws SQLException {
        int numRegistrosActualizados = 0;
        final String sql = "UPDATE instituto SET edad = ? where nombre = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, a.getEdad());
        ps.setString(2, a.getNombre());

        numRegistrosActualizados = ps.executeUpdate();

        ps.close();
        return numRegistrosActualizados;
    }

    @Override
    public int borrar(Alumno a) throws SQLException {
        int numRegistrosActualizados = 0;
        final String sql = "DELETE FROM instituto where nombre = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, a.getNombre());

        numRegistrosActualizados = ps.executeUpdate();

        ps.close();
        return numRegistrosActualizados;
    }

}
