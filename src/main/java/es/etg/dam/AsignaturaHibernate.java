package es.etg.dam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "asignatura")
public class AsignaturaHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre_asignatura", nullable = false, length = 200)
    private String nombreAsignatura;

    @Column(name = "nombre_alumno", nullable = false, length = 100)
    private String nombreAlumno;

    public AsignaturaHibernate() {
    }

    public AsignaturaHibernate(int id, String nombreAsignatura, String nombreAlumno) {
        this.id = id;
        this.nombreAsignatura = nombreAsignatura;
        this.nombreAlumno = nombreAlumno;
    }

    // Getters y setters COMPLETOS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }
}
