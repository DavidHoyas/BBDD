package es.etg.dam;

public class InstitutoDAOFactory {

    public static InstitutoDAO obtenerDAO(Modo modo) throws Exception {
        return switch (modo) {
            case MOCK ->
                new InstitutoMockDAOImp();
            case SQLITE ->
                new InstitutoSQLiteDAOImp();
            case ORACLE ->
                new InstitutoOracleXeDAOImp();
            case HIBERNATE ->
                new InstitutoHibernateDAOImp();
            default ->
                throw new IllegalArgumentException("Modo no soportado: " + modo);
        };
    }
}
