package es.etg.dam;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstitutoHibernateDAOImp implements InstitutoDAO {

    @Override
    public void crearTablas() throws SQLException {
        System.out.println("Hibernate: Tablas creadas automáticamente");
    }

    @Override
    public List<Alumno> listarAlumnos() throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            List<AlumnoHibernate> alumnosHib = session
                    .createQuery("FROM AlumnoHibernate", AlumnoHibernate.class)
                    .list();

            List<Alumno> resultado = new ArrayList<Alumno>();
            for (AlumnoHibernate ah : alumnosHib) {
                resultado.add(new Alumno(ah.getNombre(), ah.getApellido(), ah.getEdad()));
            }
            return resultado;
        } catch (Exception e) {
            System.err.println("Error listarAlumnos: " + e.getMessage());
            return new ArrayList<Alumno>();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public int insertar(Alumno a) throws SQLException {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            AlumnoHibernate alumnoHib = new AlumnoHibernate(
                    a.getNombre(),
                    a.getApellido(),
                    a.getEdad()
            );

            session.save(alumnoHib);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Error insertar: " + e.getMessage());
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public int actualizar(Alumno a) throws SQLException {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            AlumnoHibernate existente = session.createQuery(
                    "FROM AlumnoHibernate WHERE nombre = :nombre",
                    AlumnoHibernate.class)
                    .setParameter("nombre", a.getNombre())
                    .uniqueResult();

            if (existente == null) {
                System.err.println("Alumno no encontrado: " + a.getNombre());
                return 0;
            }

            existente.setApellido(a.getApellido());
            existente.setEdad(a.getEdad());

            session.update(existente);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Error actualizar: " + e.getMessage());
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public int borrar(Alumno a) throws SQLException {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            AlumnoHibernate existente = session.createQuery(
                    "FROM AlumnoHibernate WHERE nombre = :nombre",
                    AlumnoHibernate.class)
                    .setParameter("nombre", a.getNombre())
                    .uniqueResult();

            if (existente == null) {
                System.err.println("Alumno no encontrado: " + a.getNombre());
                return 0;
            }

            session.delete(existente);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Error borrar: " + e.getMessage());
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public int insertarAsignatura(Asignatura as) throws SQLException {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            AsignaturaHibernate asHib = new AsignaturaHibernate(
                    0,
                    as.getNombreAsignatura(),
                    as.getNombreAlumno()
            );

            session.save(asHib);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Error insertarAsignatura: " + e.getMessage());
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Asignatura> listarAsignaturas() throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            List<AsignaturaHibernate> asignaturasHib = session
                    .createQuery("FROM AsignaturaHibernate", AsignaturaHibernate.class)
                    .list();

            List<Asignatura> resultado = new ArrayList<Asignatura>();
            for (AsignaturaHibernate ah : asignaturasHib) {
                resultado.add(new Asignatura(ah.getId(), ah.getNombreAsignatura(), ah.getNombreAlumno()));
            }
            return resultado;
        } catch (Exception e) {
            System.err.println("Error listarAsignaturas: " + e.getMessage());
            return new ArrayList<Asignatura>();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public int actualizarAsignatura(Asignatura as) throws SQLException {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            AsignaturaHibernate existente = session.get(AsignaturaHibernate.class, as.getId());
            if (existente == null) {
                System.err.println("Asignatura no encontrada ID: " + as.getId());
                return 0;
            }

            existente.setNombreAsignatura(as.getNombreAsignatura());
            existente.setNombreAlumno(as.getNombreAlumno());

            session.update(existente);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Error actualizarAsignatura: " + e.getMessage());
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void listarAlumnosConAsignaturas() throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            List<Object[]> resultados = session.createQuery(
                    "SELECT a.nombre, a.apellido, a.edad, s.nombreAsignatura "
                    + "FROM AlumnoHibernate a, AsignaturaHibernate s "
                    + "WHERE a.nombre = s.nombreAlumno",
                    Object[].class)
                    .list();

            System.out.println("\nALUMNOS CON ASIGNATURAS (HQL JOIN):");
            if (resultados.isEmpty()) {
                System.out.println("  → No hay alumnos con asignaturas");
            } else {
                for (Object[] fila : resultados) {
                    System.out.printf("-> %s %s (%d años) → %s%n",
                            fila[0], fila[1], fila[2], fila[3]);
                }
            }
        } catch (Exception e) {
            System.err.println("Error listarAlumnosConAsignaturas: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Alumno consultar(String nombre) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            AlumnoHibernate resultado = session.createQuery(
                    "FROM AlumnoHibernate WHERE nombre = :nombre",
                    AlumnoHibernate.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();

            if (resultado != null) {
                return new Alumno(resultado.getNombre(), resultado.getApellido(), resultado.getEdad());
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error consultar: " + e.getMessage());
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
