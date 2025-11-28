package es.etg.dam;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            InstitutoDAO dao = new InstitutoSQLiteDAOImp();

            Alumno a = new Alumno("Luismi", "Lopez", 25);

            int filasActualizadas = dao.actualizar(a);

            if (filasActualizadas == 0) {
                dao.insertar(a);
                System.out.println("Alumno insertado");
            } else {
                System.out.println("Alumno actualizado");
            }

            int filasBorradas = dao.borrar(a);
            System.out.println("Filas borradas: " + filasBorradas);

            List<Alumno> alumnos = dao.listarAlumnos();

            for (Alumno al : alumnos) {
                System.out.println(
                        "Nombre: " + al.getNombre()
                        + ", Apellido: " + al.getApellido()
                        + ", Edad: " + al.getEdad()
                );
            }

        } catch (Exception e) {
            System.err.println("Error al ejecutar el programa:");
            e.printStackTrace();
        }
    }
}
