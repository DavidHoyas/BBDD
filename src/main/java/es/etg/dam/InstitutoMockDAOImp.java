package es.etg.dam;

import java.util.ArrayList;
import java.util.List;

public class InstitutoMockDAOImp implements InstitutoDAO {

    private List<Alumno> alumnos = new ArrayList<>();
    private List<Asignatura> asignaturas = new ArrayList<>();

    public void crearTablas() {
    }

    public List<Alumno> listarAlumnos() {
        alumnos.add(new Alumno("David", "Oliva", 19));
        alumnos.add(new Alumno("Sergio", "Simon", 19));
        return alumnos;
    }

    public int insertar(Alumno a) {
        alumnos.add(a);
        return 1;
    }

    public int actualizar(Alumno a) {
        return 1;
    }

    public int borrar(Alumno a) {
        return 1;
    }

    public int insertarAsignatura(Asignatura as) {
        asignaturas.add(as);
        return 1;
    }

    public int actualizarAsignatura(Asignatura as) {
        return 1;
    }

    public List<Asignatura> listarAsignaturas() {
        return asignaturas;
    }

    public void listarAlumnosConAsignaturas() {
        for (Alumno a : alumnos) {
            System.out.println(a.getNombre());
            for (Asignatura as : asignaturas) {
                if (as.getNombreAlumno().equals(a.getNombre())) {
                    System.out.println("  → " + as.getNombreAsignatura());
                }
            }
        }
    }

    public Alumno consultar(String nombre) {
        for (Alumno a : alumnos) {
            if (a.getNombre().equals(nombre)) {
                return a;
            }
        }
        return null;
    }
}
