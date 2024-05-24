
package Conexion;

/**
 *
 * @author JHONATAN
 */
import java.sql.*;

public class Conexion {

    private Conexion() {

    }

    private static final String URL = "jdbc:mysql://localhost/controlplanilla";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static Conexion instancia;

    public Connection ConectarBD() throws SQLException {
        System.out.println("Conexion abierta");
        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

    public void DesconectarBD(Connection conexion) {
        try {
            conexion.close();
            System.out.println("Conexion Cerrada");
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexion => " + e.getMessage());
        }
    }

    public void cerrarResultado(ResultSet resultado) {
        try {
            resultado.close();

        } catch (SQLException e) {
            System.out.println("Error al cerar resultset => " + e.getMessage());
        }

    }

    public void cerrarStatement(PreparedStatement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar Statemet => " + e.getMessage());
        }
    }

    //patron singleton
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }

        return instancia;
    }

}
