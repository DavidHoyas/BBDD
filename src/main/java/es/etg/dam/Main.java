package es.etg.dam;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {

            System.out.println("MENÚ – Tipo de conexión");
            System.out.println("1 - MOCK");
            System.out.println("2 - SQLITE");
            System.out.println("3 - ORACLE");
            System.out.print("Seleccione opción (1-3): ");

            int tipo = Integer.parseInt(sc.nextLine().trim());

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

            String opcion;
            do {
                System.out.println();
                System.out.println("Elija una acción:");
                System.out.println("a) Crear tablas");
                System.out.println("b) Insertar alumno");
                System.out.println("c) Insertar asignatura");
                System.out.println("d) Actualizar alumno");
                System.out.println("e) Actualizar asignatura");
                System.out.println("f) Listar alumnos");
                System.out.println("g) Listar alumnos con asignaturas");
                System.out.println("h) Buscar alumno por nombre");
                System.out.println("0) Salir");
                System.out.print("Opción: ");

                opcion = sc.nextLine().trim();

                switch (opcion) {

                    case "a" -> {
                        try {
                            dao.crearTablas();
                            System.out.println("✓ Tablas creadas (o recreadas) correctamente.");
                        } catch (Exception e) {
                            System.out.println("✗ Error al crear tablas: " + e.getMessage());
                        }
                    }

                    case "b" -> {
                        try {
                            System.out.print("Nombre: ");
                            String nombre = sc.nextLine().trim();
                            System.out.print("Apellidos: ");
                            String apellidos = sc.nextLine().trim();
                            System.out.print("Edad: ");
                            int edad = Integer.parseInt(sc.nextLine().trim());

                            int filas = dao.insertar(new Alumno(nombre, apellidos, edad));
                            System.out.println(filas > 0 ? "✓ Alumno insertado." : "✗ Alumno ya existe o no insertado.");
                        } catch (NumberFormatException e) {
                            System.out.println("✗ Error: la edad debe ser un número.");
                        } catch (SQLException e) {
                            System.out.println("✗ Error de BD: " + e.getMessage());
                        }
                    }

                    case "c" -> {
                        try {
                            System.out.print("Nombre asignatura: ");
                            String nombreAsig = sc.nextLine().trim();
                            System.out.print("Nombre alumno (exacto): ");
                            String nombreAlumno = sc.nextLine().trim();

                            int filas = dao.insertarAsignatura(new Asignatura(0, nombreAsig, nombreAlumno));
                            System.out.println(filas > 0 ? "✓ Asignatura insertada." : "✗ No se insertó la asignatura.");
                        } catch (SQLException e) {
                            System.out.println("✗ Error de BD: " + e.getMessage());
                        }
                    }

                    case "d" -> {
                        try {
                            System.out.print("Nombre del alumno a actualizar (exacto): ");
                            String nombre = sc.nextLine().trim();
                            System.out.print("Nuevos apellidos: ");
                            String apellidos = sc.nextLine().trim();
                            System.out.print("Nueva edad: ");
                            int edad = Integer.parseInt(sc.nextLine().trim());

                            int filas = dao.actualizar(new Alumno(nombre, apellidos, edad));
                            System.out.println(filas > 0 ? "✓ Alumno actualizado." : "✗ No se encontró el alumno.");
                        } catch (NumberFormatException e) {
                            System.out.println("✗ Error: la edad debe ser un número.");
                        } catch (SQLException e) {
                            System.out.println("✗ Error de BD: " + e.getMessage());
                        }
                    }

                    case "e" -> {
                        try {
                            System.out.print("ID de la asignatura a actualizar: ");
                            int id = Integer.parseInt(sc.nextLine().trim());
                            System.out.print("Nuevo nombre asignatura: ");
                            String nombreAsig = sc.nextLine().trim();

                            int filas = dao.actualizarAsignatura(new Asignatura(id, nombreAsig, ""));
                            System.out.println(filas > 0 ? "✓ Asignatura actualizada." : "✗ No se encontró la asignatura.");
                        } catch (NumberFormatException e) {
                            System.out.println("✗ Error: el ID debe ser un número.");
                        } catch (SQLException e) {
                            System.out.println("✗ Error de BD: " + e.getMessage());
                        }
                    }

                    case "f" -> {
                        try {
                            var alumnos = dao.listarAlumnos();
                            if (alumnos.isEmpty()) {
                                System.out.println("No hay alumnos registrados.");
                            } else {
                                System.out.println("\n✓ Listado de alumnos:");
                                alumnos.forEach(a -> System.out.println("  - " + a));
                            }
                        } catch (SQLException e) {
                            System.out.println("✗ Error de BD: " + e.getMessage());
                        }
                    }

                    case "g" -> {
                        try {
                            dao.listarAlumnosConAsignaturas();
                        } catch (SQLException e) {
                            System.out.println("✗ Error de BD: " + e.getMessage());
                        }
                    }

                    case "h" -> {
                        try {
                            System.out.print("Nombre a buscar: ");
                            String nombre = sc.nextLine().trim();
                            Alumno a = dao.consultar(nombre);
                            if (a != null) {
                                System.out.println("✓ Encontrado: " + a);
                            } else {
                                System.out.println("✗ No encontrado");
                            }
                        } catch (SQLException e) {
                            System.out.println("✗ Error de BD: " + e.getMessage());
                        }
                    }

                    case "0" ->
                        System.out.println("Saliendo...");

                    default ->
                        System.out.println("Opción no reconocida.");
                }

            } while (!opcion.equals("0"));

        } catch (Exception e) {
            System.err.println("Error fatal: " + e.getMessage());
        }
    }
}
