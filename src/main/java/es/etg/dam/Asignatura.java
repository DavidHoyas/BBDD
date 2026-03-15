package es.etg.dam;

public class Asignatura {

    private final int id;
    private final String nombreAsignatura;
    private final String nombreAlumno;

    public Asignatura(int id, String nombreAsignatura, String nombreAlumno) {
        this.id = id;
        this.nombreAsignatura = nombreAsignatura;
        this.nombreAlumno = nombreAlumno;
    }

    public int getId() {
        return id;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

}
