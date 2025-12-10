package es.etg.dam;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void crearTablas() throws SQLException {

        String alumnos = """
            CREATE TABLE IF NOT EXISTS instituto (
                nombre TEXT PRIMARY KEY,
                apellido TEXT,
                edad INTEGER
            );
        """;

        String asignaturas = """
            CREATE TABLE IF NOT EXISTS asignaturas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_asignatura TEXT,
                nombre_alumno TEXT,
                FOREIGN KEY (nombre_alumno) REFERENCES instituto(nombre)
            );
        """;

        conn.createStatement().execute(alumnos);
        conn.createStatement().execute(asignaturas);
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

            alumnos.add(new Alumno(nombre, apellido, edad));
        }

        rs.close();
        ps.close();

        return alumnos;
    }

    @Override
    public int insertar(Alumno a) throws SQLException {

        final String sql = "INSERT INTO instituto (nombre, apellido, edad) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, a.getNombre());
        ps.setString(2, a.getApellido());
        ps.setInt(3, a.getEdad());

        int filas = ps.executeUpdate();
        ps.close();

        return filas;
    }

    @Override
    public int actualizar(Alumno a) throws SQLException {

        final String sql = "UPDATE instituto SET apellido=?, edad=? WHERE nombre=?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, a.getApellido());
        ps.setInt(2, a.getEdad());
        ps.setString(3, a.getNombre());

        int filas = ps.executeUpdate();
        ps.close();

        return filas;
    }

    @Override
    public int borrar(Alumno a) throws SQLException {

        final String sql = "DELETE FROM instituto WHERE nombre=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, a.getNombre());

        int filas = ps.executeUpdate();
        ps.close();

        return filas;
    }

    @Override
    public int insertarAsignatura(Asignatura as) throws SQLException {

        String sql = "INSERT INTO asignaturas(nombre_asignatura, nombre_alumno) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, as.getNombreAsignatura());
        ps.setString(2, as.getNombreAlumno());

        int filas = ps.executeUpdate();
        ps.close();

        return filas;
    }

    @Override
    public int actualizarAsignatura(Asignatura as) throws SQLException {

        String sql = "UPDATE asignaturas SET nombre_asignatura=? WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, as.getNombreAsignatura());
        ps.setInt(2, as.getId());

        int filas = ps.executeUpdate();
        ps.close();

        return filas;
    }

    @Override
    public List<Asignatura> listarAsignaturas() throws SQLException {

        List<Asignatura> lista = new ArrayList<>();
        String sql = "SELECT * FROM asignaturas";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            lista.add(new Asignatura(
                    rs.getInt("id"),
                    rs.getString("nombre_asignatura"),
                    rs.getString("nombre_alumno")
            ));
        }

        rs.close();
        ps.close();

        return lista;
    }

    @Override
    public void listarAlumnosConAsignaturas() throws SQLException {

        String sql = """
            SELECT i.nombre, a.nombre_asignatura
            FROM instituto i
            JOIN asignaturas a
            ON i.nombre = a.nombre_alumno
        """;

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            System.out.println(
                    rs.getString("nombre") + " → "
                    + rs.getString("nombre_asignatura")
            );
        }

        rs.close();
        st.close();
    }

    @Override
    public Alumno consultar(String nombre) throws SQLException {

        String sql = "SELECT * FROM instituto WHERE nombre=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nombre);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Alumno a = new Alumno(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("edad")
            );
            rs.close();
            ps.close();
            return a;
        }

        rs.close();
        ps.close();
        return null;
    }
}
