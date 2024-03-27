/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import Datos.Vendedor;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User create table empleados( id integer unsigned auto_increment,
 * nombre varchar (50) not null, sueldobasico decimal (10,2) unsigned not null,
 * mes varchar (15)not null, faltas integer unsigned null, des_afp decimal
 * (10,2) unsigned not null, des_essalud decimal(10,2)unsigned not null,
 * total_descuento decimal (10,2) unsigned not null, horas_extras integer
 * unsigned not null, pago_horasextras decimal (10,2) unsigned not null,
 * sueldo_neto decimal(10,2) unsigned not null, primary key (id) );
 */
public class VendedorDao {

    Conexion instanciasql = Conexion.getInstancia();
    private static final String SQL_SELECT = "SELECT id, nombre, sueldobasico, mes, faltas, horas_extras, des_afp, des_essalud, total_descuento, pago_horasextras, sueldo_neto FROM empleados;";
    private static final String SQL_INSERT
            = "INSERT INTO empleados (nombre, sueldobasico, mes, faltas, horas_extras, des_afp, des_essalud, total_descuento, pago_horasextras, sueldo_neto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "update empleados set nombre = ?,sueldobasico = ?,mes = ?,faltas = ?,horas_extras = ?,des_afp = ?,des_essalud = ?,total_descuento = ?,pago_horasextras = ?,sueldo_neto = ? where id = ?";
    private static final String SQL_DELETE = "delete from clientes where id = ? ";

    public List<Vendedor> listar() {
        Connection conexion = null;
        PreparedStatement consultaPreparada = null;
        ResultSet resultado = null;
        List<Vendedor> vendedores = new ArrayList<>();
        Vendedor vendedor;
        try {
            conexion = instanciasql.ConectarBD();
            consultaPreparada = conexion.prepareStatement(SQL_SELECT);
            resultado = consultaPreparada.executeQuery();
            int id,
                    faltas,
                    horasExtras;
            String nombre, mes;
            double sueldoBasico;

            while (resultado.next()) {
                id = resultado.getInt("id");
                nombre = resultado.getString("nombre");
                sueldoBasico = resultado.getDouble("sueldobasico");
                mes = resultado.getString("mes");
                faltas = resultado.getInt("faltas");
                horasExtras = resultado.getInt("horas_extras");

                vendedor = new Vendedor(id, nombre, sueldoBasico, mes, faltas, horasExtras);
                double descafp = vendedor.calcularAFP();
                double desEssalud = vendedor.calcularEssalud();
                double totalDesc = vendedor.totadescuento();
                double pagoHoras = vendedor.pagoHorasExtras();
                double sueldoNeto = vendedor.sueldoNeto();
                descafp = resultado.getDouble("des_afp");
                desEssalud = resultado.getDouble("des_essalud");
                totalDesc = resultado.getDouble("total_descuento");
                pagoHoras = resultado.getDouble("pago_horasextras");
                sueldoNeto = resultado.getDouble("sueldo_neto");

                vendedores.add(vendedor);

            }
        } catch (SQLException e) {
        }

        return vendedores;
    }

    public void listarEnTabla(DefaultTableModel defaultTableModel, JTable jTable) {
        Connection conexion = null;
        PreparedStatement consultaPreparada = null;
        ResultSet resultado = null;

        try {
            conexion = instanciasql.ConectarBD();
            consultaPreparada = conexion.prepareStatement(SQL_SELECT);
            resultado = consultaPreparada.executeQuery();
            int columnas = resultado.getMetaData().getColumnCount();
            Object[] filas = new Object[columnas];
            int numeracion = 1;

            while (resultado.next()) {
                filas[0] = numeracion++;
                for (int i = 1; i < columnas; i++) {
                    filas[i] = resultado.getObject(i + 1);
                }
                defaultTableModel.addRow(filas);
            }
            jTable.updateUI();

        } catch (SQLException e) {
            System.out.println("error en listar tablas => " + e.getMessage());

        } finally { //finalmente cerramos las variables
            instanciasql.cerrarResultado(resultado);
            instanciasql.cerrarStatement(consultaPreparada);
            instanciasql.DesconectarBD(conexion);
        }
    }

    public void busacarRegistros(DefaultTableModel defaultTableModel, JTable jTable, JTextField txtbuscar) {
        Connection conexion = null;
        PreparedStatement consultaPreparada = null;
        ResultSet resultado = null;
        String nombre = txtbuscar.getText().trim();
        String sql = "select * from empleados where nombre = '" + nombre + "'";

        try {
            conexion = instanciasql.ConectarBD();
            consultaPreparada = conexion.prepareStatement(sql);
            resultado = consultaPreparada.executeQuery();
            int columnas = resultado.getMetaData().getColumnCount();
            Object[] filas = new Object[columnas];
            int numeracion = 1;

            while (resultado.next()) {
                filas[0] = numeracion++;
                for (int i = 1; i < columnas; i++) {
                    filas[i] = resultado.getObject(i + 1);
                }
                defaultTableModel.addRow(filas);
            }
            jTable.updateUI();

        } catch (SQLException e) {
            System.out.println("error en listar tablas => " + e.getMessage());

        } finally { //finalmente cerramos las variables
            instanciasql.cerrarResultado(resultado);
            instanciasql.cerrarStatement(consultaPreparada);
            instanciasql.DesconectarBD(conexion);
        }
    }

    public void mostrarLista(DefaultTableModel modelo, JTable tabla) {
        this.listarEnTabla(modelo, tabla);
    }

    //metodo para vaciar  la tabla 
    public static void vaciarTabla(DefaultTableModel modelo) {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
    }

    public int insertar(Vendedor vendedor) {
        Connection conexion = null;
        PreparedStatement consultaprepada = null;
        int registros = 0;
        try {
            conexion = instanciasql.ConectarBD();
            consultaprepada = conexion.prepareStatement(SQL_INSERT);
            // Establecer los parámetros utilizando nombres de columnas
            consultaprepada.setString(1, vendedor.getNombre());
            consultaprepada.setDouble(2, vendedor.getSueldoBasico());
            consultaprepada.setString(3, vendedor.getMes());
            consultaprepada.setInt(4, vendedor.getFaltas());
            consultaprepada.setInt(5, vendedor.getHorasExtras());
            consultaprepada.setDouble(6, vendedor.calcularAFP());
            consultaprepada.setDouble(7, vendedor.calcularEssalud());
            consultaprepada.setDouble(8, vendedor.totadescuento());
            consultaprepada.setDouble(9, vendedor.pagoHorasExtras());
            consultaprepada.setDouble(10, vendedor.sueldoNeto());

            registros = consultaprepada.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error en Insertar => " + e.getMessage());
        } finally {
            instanciasql.DesconectarBD(conexion);
            instanciasql.cerrarStatement(consultaprepada);

        }
        return registros;
    }

    public int modificar(Vendedor vendedor) {
        Connection conexion = null;
        PreparedStatement consultaprepada = null;
        int registros = 0;
        try {
            conexion = instanciasql.ConectarBD();

            consultaprepada = conexion.prepareStatement(SQL_UPDATE);

            consultaprepada.setString(1, vendedor.getNombre());
            consultaprepada.setDouble(2, vendedor.getSueldoBasico());
            consultaprepada.setString(3, vendedor.getMes());
            consultaprepada.setInt(4, vendedor.getFaltas());
            consultaprepada.setInt(5, vendedor.getHorasExtras());
            consultaprepada.setDouble(6, vendedor.calcularAFP());
            consultaprepada.setDouble(7, vendedor.calcularEssalud());
            consultaprepada.setDouble(8, vendedor.totadescuento());
            consultaprepada.setDouble(9, vendedor.pagoHorasExtras());
            consultaprepada.setDouble(10, vendedor.sueldoNeto());
            consultaprepada.setInt(11, vendedor.getId());

            registros = consultaprepada.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en Modificar: => " + e.getMessage());
        } finally {
            instanciasql.cerrarStatement(consultaprepada);
            instanciasql.DesconectarBD(conexion);

        }
        return registros;
    }

    public int eliminar(Vendedor vendedor) {
        Connection conexion = null;
        PreparedStatement consultaprepada = null;
        int registros = 0;

        try {
            conexion = instanciasql.ConectarBD();

            consultaprepada = conexion.prepareStatement(SQL_DELETE);

            consultaprepada.setInt(1, vendedor.getId());

            registros = consultaprepada.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error en Eliminar => " + e.getMessage());
        } finally {
            instanciasql.cerrarStatement(consultaprepada);
            instanciasql.DesconectarBD(conexion);

        }

        return registros;
    }
}
