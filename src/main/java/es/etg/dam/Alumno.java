package es.etg.dam;

public class Alumno {

    private final String nombre;
    private final String apellido;
    private final int edad;

    public Alumno(String nombre, String apellido, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + edad + " años)";
    }

}
