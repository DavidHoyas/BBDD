package es.etg.dam;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {

            System.out.println("MENÚ 1 – Tipo de conexión");
            System.out.println("1 - MOCK");
            System.out.println("2 - SQLITE");
            System.out.println("3 - ORACLE");

            int tipo = sc.nextInt();

            InstitutoDAO dao = switch (tipo) {
                case 1 ->
                    InstitutoDAOFactory.obtenerDAO(Modo.MOCK);
                case 2 ->
                    InstitutoDAOFactory.obtenerDAO(Modo.SQLITE);
                case 3 ->
                    InstitutoDAOFactory.obtenerDAO(Modo.ORACLE);
                default ->
                    throw new IllegalArgumentException("Opción inválida");
            };

            char opcion;
            do {
                System.out.println("""
                a) Crear tablas
                b) Insertar alumno
                c) Insertar asignatura
                d) Actualizar alumno
                e) Actualizar asignatura
                f) Listar alumnos
                g) Listar alumnos con asignaturas
                h) Buscar alumno por nombre
                0) Salir
                """);

                opcion = sc.next().charAt(0);

                switch (opcion) {

                    case 'a' ->
                        dao.crearTablas();

                    case 'b' ->
                        dao.insertar(
                                new Alumno("Luis", "Gomez", 22)
                        );

                    case 'c' ->
                        dao.insertarAsignatura(
                                new Asignatura(0, "Programación", "Luis")
                        );

                    case 'd' ->
                        dao.actualizar(
                                new Alumno("Luis", "Gomez", 25)
                        );

                    case 'e' ->
                        dao.actualizarAsignatura(
                                new Asignatura(1, "BasesDeDatos", "Luis")
                        );

                    case 'f' ->
                        dao.listarAlumnos()
                                .forEach(a
                                        -> System.out.println(a.getNombre() + " " + a.getApellido())
                                );

                    case 'g' ->
                        dao.listarAlumnosConAsignaturas();

                    case 'h' -> {
                        Alumno a = dao.consultar("Luis");
                        System.out.println(a != null ? a.getNombre() : "No encontrado");
                    }
                }

            } while (opcion != '0');

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
