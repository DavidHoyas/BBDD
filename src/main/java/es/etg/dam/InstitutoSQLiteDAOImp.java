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
        String path;
        if (resource != null) {
            path = new File(resource.toURI()).getAbsolutePath();
        } else {
            path = System.getProperty("user.dir") + File.separator + DATABASE_NAME;
        }
        System.out.println("SQLite DB path: " + path);
        String url = String.format(JDBC_URL, path);
        conn = DriverManager.getConnection(url);
    }

    @Override
    public void crearTablas() throws SQLException {
        // Eliminar tablas existentes con esquema antiguo (si las hay)
        conn.createStatement().execute("DROP TABLE IF EXISTS asignaturas;");
        conn.createStatement().execute("DROP TABLE IF EXISTS instituto;");

        String alumnos = """
            CREATE TABLE IF NOT EXISTS instituto (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                apellido TEXT,
                edad INTEGER
            );
        """;

        String asignaturas = """
            CREATE TABLE IF NOT EXISTS asignaturas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_asignatura TEXT,
                nombre_alumno TEXT
            );
        """;

        conn.createStatement().execute(alumnos);
        conn.createStatement().execute(asignaturas);
    }

    @Override
    public List<Alumno> listarAlumnos() throws SQLException {

        final String query = "SELECT nombre, apellido, edad FROM instituto";
        List<Alumno> alumnos = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int edad = rs.getInt("edad");

                alumnos.add(new Alumno(nombre, apellido, edad));
            }
        }

        return alumnos;
    }

    @Override
    public int insertar(Alumno a) throws SQLException {
        // Comprobar si el alumno ya existe para evitar violación de UNIQUE/PK
        final String checkSql = "SELECT COUNT(*) FROM instituto WHERE nombre=? AND apellido=?";
        try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
            psCheck.setString(1, a.getNombre());
            psCheck.setString(2, a.getApellido());
            try (ResultSet rs = psCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return 0; // no insertar duplicado
                }
            }
        }

        final String sql = "INSERT INTO instituto (nombre, apellido, edad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setInt(3, a.getEdad());
            return ps.executeUpdate();
        }
    }

    @Override
    public int actualizar(Alumno a) throws SQLException {

        final String sql = "UPDATE instituto SET apellido=?, edad=? WHERE nombre=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getApellido());
            ps.setInt(2, a.getEdad());
            ps.setString(3, a.getNombre());

            return ps.executeUpdate();
        }
    }

    @Override
    public int borrar(Alumno a) throws SQLException {

        final String sql = "DELETE FROM instituto WHERE nombre=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            return ps.executeUpdate();
        }
    }

    @Override
    public int insertarAsignatura(Asignatura as) throws SQLException {

        String sql = "INSERT INTO asignaturas(nombre_asignatura, nombre_alumno) VALUES (?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, as.getNombreAsignatura());
            ps.setString(2, as.getNombreAlumno());

            return ps.executeUpdate();
        }
    }

    @Override
    public int actualizarAsignatura(Asignatura as) throws SQLException {

        String sql = "UPDATE asignaturas SET nombre_asignatura=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, as.getNombreAsignatura());
            ps.setInt(2, as.getId());

            return ps.executeUpdate();
        }
    }

    @Override
    public List<Asignatura> listarAsignaturas() throws SQLException {

        List<Asignatura> lista = new ArrayList<>();
        String sql = "SELECT * FROM asignaturas";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Asignatura(
                        rs.getInt("id"),
                        rs.getString("nombre_asignatura"),
                        rs.getString("nombre_alumno")
                ));
            }
        }

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

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n✓ Alumnos con asignaturas:");
            while (rs.next()) {
                System.out.println("  - " + rs.getString("nombre") + " → "
                        + rs.getString("nombre_asignatura"));
            }
        }
    }

    @Override
    public Alumno consultar(String nombre) throws SQLException {

        String sql = "SELECT * FROM instituto WHERE nombre=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Alumno(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getInt("edad")
                    );
                }
            }
        }
        return null;
    }
}
