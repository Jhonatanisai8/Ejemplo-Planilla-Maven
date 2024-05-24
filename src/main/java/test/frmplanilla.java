
package test;

import Conexion.VendedorDao;
import Datos.Vendedor;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public final class frmplanilla extends javax.swing.JFrame {

    //declaramos variables para el boton buscar 
    private TableRowSorter trsFiltro;
    String filtro;
    VendedorDao vendedorDao = new VendedorDao();
    Vendedor vendedor = new Vendedor();
    DefaultTableModel modelo;

    //variables para la grafica
    JFreeChart grafico;
    DefaultCategoryDataset datos = new DefaultCategoryDataset();

    public frmplanilla() {
        initComponents();
        this.InicioSistema();

    }

    private void InicioSistema() {
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");//1
        modelo.addColumn("Nombre");//2
        modelo.addColumn("Sueldo Basico");//3
        modelo.addColumn("Mes"); //4
        modelo.addColumn("Faltas");//5
        modelo.addColumn("Horas Extras");//6
        modelo.addColumn("Desc. AFP");//7
        modelo.addColumn("Desc. Essalud");//8
        modelo.addColumn("Total Desc.");//9
        modelo.addColumn("Pago H.Ex");//10
        modelo.addColumn("Sueldo Neto");//11
        tblDatos.setModel(modelo);//12

        this.setTitle("Bienvenido al Sistema");
        mostrarLista();
        tipoletra();
    }

    private void tipoletra() {
        txtHoras.setFont(Font.decode("Consolas"));
        txtnombre.setFont(Font.decode("Consolas"));
        txtsueldo.setFont(Font.decode("Consolas"));
        cbxMes.setFont(Font.decode("Consolas"));
        spFaltas.setFont(Font.decode("Consolas"));

    }

    void mostrarLista() {
        VendedorDao.vaciarTabla(modelo);
        vendedorDao.mostrarLista(modelo, tblDatos);

        tblDatos.setFont(Font.decode("Cascadia Code"));
        anchoColumnas();
    }

    void cargargrafico() {
        try {
            int hola;
            int columnas = tblDatos.getColumnCount();
            for (int i = 0; i < tblDatos.getRowCount(); i++) {
                datos.addValue(Integer.parseInt(tblDatos.getValueAt(i, 5).toString()),
                        tblDatos.getValueAt(i, 3).toString(),
                        tblDatos.getValueAt(i, 3).toString());
            }
            //mostramos los graficos
            grafico = ChartFactory.createBarChart("Grafico de Faltas por mes",
                    "Mes", "Faltas", datos, PlotOrientation.HORIZONTAL,
                    true, true,
                    false);

            ChartPanel panel = new ChartPanel(grafico);
            this.add(panel);
            panel.setBounds(350, 40, 1000, 800);

        } catch (NumberFormatException e) {
            System.out.println("Error al graficar => " + e.getMessage());
        }
    }

    private void anchoColumnas() {

        //ponemos un ancho a las columnas
        //id
        tblDatos.getColumnModel().getColumn(0).setWidth(1);
        tblDatos.getColumnModel().getColumn(0).setMinWidth(1);
        tblDatos.getColumnModel().getColumn(0).setPreferredWidth(1);

        //Nombre
        tblDatos.getColumnModel().getColumn(1).setWidth(20 * 7);
        tblDatos.getColumnModel().getColumn(1).setMinWidth(20 * 7);
        tblDatos.getColumnModel().getColumn(1).setPreferredWidth(20 * 7);

        //Sueldo Basico
        tblDatos.getColumnModel().getColumn(2).setWidth(6);
        tblDatos.getColumnModel().getColumn(2).setMinWidth(6);
        tblDatos.getColumnModel().getColumn(2).setPreferredWidth(6);

        //Mes
        tblDatos.getColumnModel().getColumn(3).setWidth(3);
        tblDatos.getColumnModel().getColumn(3).setMinWidth(3);
        tblDatos.getColumnModel().getColumn(3).setPreferredWidth(3);

        //Faltas
        tblDatos.getColumnModel().getColumn(4).setWidth(1);
        tblDatos.getColumnModel().getColumn(4).setMinWidth(1);
        tblDatos.getColumnModel().getColumn(4).setPreferredWidth(1);

        //Horas Extras
        tblDatos.getColumnModel().getColumn(5).setWidth(1);
        tblDatos.getColumnModel().getColumn(5).setMinWidth(1);
        tblDatos.getColumnModel().getColumn(5).setPreferredWidth(1);

        //Desc Afp
        tblDatos.getColumnModel().getColumn(6).setWidth(3);
        tblDatos.getColumnModel().getColumn(6).setMinWidth(3);
        tblDatos.getColumnModel().getColumn(6).setPreferredWidth(3);

        //Desc. Essalud
        tblDatos.getColumnModel().getColumn(7).setWidth(5);
        tblDatos.getColumnModel().getColumn(7).setMinWidth(5);
        tblDatos.getColumnModel().getColumn(7).setPreferredWidth(5);

        //Total descs
        tblDatos.getColumnModel().getColumn(8).setWidth(2);
        tblDatos.getColumnModel().getColumn(8).setMinWidth(2);
        tblDatos.getColumnModel().getColumn(8).setPreferredWidth(2);

        //Pago H. extras
        tblDatos.getColumnModel().getColumn(9).setWidth(3);
        tblDatos.getColumnModel().getColumn(9).setMinWidth(3);
        tblDatos.getColumnModel().getColumn(9).setPreferredWidth(3);

        //sueldo neto
        tblDatos.getColumnModel().getColumn(10).setWidth(3);
        tblDatos.getColumnModel().getColumn(10).setMinWidth(3);
        tblDatos.getColumnModel().getColumn(10).setPreferredWidth(3);

    }

    private void insertar() {

        try {
            if (comprarCampos()) {
                JOptionPane.showMessageDialog(rootPane, "Por favor completar los campos");
            } else {
                int faltas,
                        horasExtras;
                String nombre, mes;
                double sueldoBasico;
                nombre = txtnombre.getText();
                sueldoBasico = Double.parseDouble(txtsueldo.getText().trim());
                mes = cbxMes.getSelectedItem().toString();
                faltas = (int) spFaltas.getValue();
                horasExtras = Integer.parseInt(txtHoras.getText().trim());

                vendedor = new Vendedor(nombre, sueldoBasico, mes, faltas, horasExtras);

                vendedorDao.insertar(vendedor);

                JOptionPane.showMessageDialog(rootPane, "Insertado");
                limpiar();
                mostrarLista();
            }
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Error en bOTON insertar => " + e.getMessage());
        }
    }

    private void Modificar() {

        int fila = tblDatos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(rootPane, "Por favor Selecionar una Vendedor a Modificar");
        } else {
            int id;
            int faltas,
                    horasExtras;
            String nombre, mes;
            double sueldoBasico;
            nombre = txtnombre.getText();
            sueldoBasico = Double.parseDouble(txtsueldo.getText().trim());
            mes = cbxMes.getSelectedItem().toString();
            faltas = (int) spFaltas.getValue();
            horasExtras = Integer.parseInt(txtHoras.getText().trim());

            id = Integer.parseInt(tblDatos.getValueAt(fila, 0).toString());
            vendedor = new Vendedor(id, nombre, sueldoBasico, mes, faltas, horasExtras);
            vendedorDao.modificar(vendedor);

            JOptionPane.showMessageDialog(rootPane, "Vendedor Modificado");

            mostrarLista();
            limpiar();
        }

    }

    private void verdatos() {
        try {
            int filaSelct = tblDatos.getSelectedRow();
            if (filaSelct == -1) {
                JOptionPane.showMessageDialog(rootPane, """
                                                    Por favor
                                                     Seleccione una fil""", "Danger", JOptionPane.WARNING_MESSAGE);
            } else {
                String nombre;
                double sueldo;
                String mes;
                int horas;
                int faltas;

                nombre = (String) tblDatos.getValueAt(filaSelct, 1);
                sueldo = Double.parseDouble(tblDatos.getValueAt(filaSelct, 2).toString());
                mes = (String) tblDatos.getValueAt(filaSelct, 3);
                faltas = Integer.parseInt(tblDatos.getValueAt(filaSelct, 4).toString());
                horas = Integer.parseInt(tblDatos.getValueAt(filaSelct, 5).toString());

                //ponemos los datos en los campos de texto
                txtnombre.setText(nombre);
                txtsueldo.setText(String.valueOf(sueldo));
                txtHoras.setText(String.valueOf(horas));
                cbxMes.setSelectedItem(mes);
                spFaltas.setToolTipText(faltas + "");
                spFaltas.setValue(faltas);

            }
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Error en => " + e.getMessage());

        }
    }

    private boolean comprarCampos() {
        return txtnombre.getText().trim().isEmpty()
                || txtHoras.getText().trim().isEmpty()
                || txtsueldo.getText().trim().isEmpty()
                || cbxMes.getSelectedItem().equals("=Seleccionar=");
    }

    private void limpiar() {
        txtHoras.setText(null);
        txtnombre.setText(null);
        txtsueldo.setText(null);
        cbxMes.setSelectedIndex(0);
        spFaltas.setValue(0);

    }

    private void salirsistema() {
        int opcion;
        opcion = JOptionPane.showConfirmDialog(rootPane, "¿Enserio quiere salir?");
        if (opcion == 0) {
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Sabia que ivas a quedar");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtnombre = new javax.swing.JTextField();
        txtsueldo = new javax.swing.JTextField();
        cbxMes = new javax.swing.JComboBox<>();
        spFaltas = new javax.swing.JSpinner();
        txtHoras = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        txtbuscar = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(29, 40, 58));

        jPanel2.setBackground(new java.awt.Color(102, 131, 179));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Datos del Empleado:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cascadia Code", 1, 14))); // NOI18N

        txtnombre.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Nombre y Apellidos:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 0, 14))); // NOI18N

        txtsueldo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Sueldo Basico:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 0, 14))); // NOI18N
        txtsueldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsueldoActionPerformed(evt);
            }
        });

        cbxMes.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Mes:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 0, 14))); // NOI18N

        spFaltas.setModel(new javax.swing.SpinnerNumberModel(0, 0, 20, 1));
        spFaltas.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Faltas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 0, 14))); // NOI18N

        txtHoras.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Horas Extras:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 0, 14))); // NOI18N
        txtHoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHorasActionPerformed(evt);
            }
        });

        jButton6.setText("Ver Grafico");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbxMes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(spFaltas, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxMes, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spFaltas, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtsueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(222, 228, 239));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Operaciones:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cascadia Code", 0, 14))); // NOI18N

        jButton1.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        jButton1.setText("Insertar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        jButton4.setText("Salir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        jButton5.setText("Buscar Empleado:");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        txtbuscar.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        txtbuscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtbuscarCaretUpdate(evt);
            }
        });
        txtbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtbuscarMouseClicked(evt);
            }
        });
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtbuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel4.setBackground(new java.awt.Color(71, 99, 143));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Registros:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cascadia Code", 0, 12))); // NOI18N

        tblDatos.setBackground(new java.awt.Color(203, 213, 228));
        tblDatos.setForeground(new java.awt.Color(60, 63, 65));
        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDatos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtsueldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsueldoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsueldoActionPerformed

    private void txtHorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorasActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        insertar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
//        cbxMes.addItem("=Seleccionar=");
//        cbxMes.addItem("Enero");
//        cbxMes.addItem("Febrero");
//        cbxMes.addItem("Marzo");
        Object mes[] = {"=Seleccionar=", "Enero", "Febrero", "Marzo", "Abril",
            "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
            "Diciembre"};
        for (Object me : mes) {
            cbxMes.addItem(me + "");
        }
    }//GEN-LAST:event_formWindowOpened

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Modificar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        // TODO add your handling code here:
        verdatos();
    }//GEN-LAST:event_tblDatosMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        salirsistema();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
//        if (txtbuscar.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Por favor Ingresa el nombre a buscar", "WARNIG", JOptionPane.WARNING_MESSAGE);
//            txtbuscar.requestFocus();
//        } else {
//            buscar();
//        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtbuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyTyped
        // TODO add your handling code here:
        trsFiltro = new TableRowSorter(tblDatos.getModel());
        tblDatos.setRowSorter(trsFiltro);
    }//GEN-LAST:event_txtbuscarKeyTyped

    private void txtbuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtbuscarCaretUpdate
        // TODO add your handling code here:
        // buscardato();
    }//GEN-LAST:event_txtbuscarCaretUpdate

    private void txtbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtbuscarMouseClicked
        // TODO add your handling code here:
        this.buscar();
    }//GEN-LAST:event_txtbuscarMouseClicked


    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        // frmgrafica o = new frmgrafica();
        cargargrafico();
        // o.setVisible(true);

        //  this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton3ActionPerformed

    public void filtro() {//creamos el metodo filtro
        if (txtbuscar == null) {

        } else {
            try {
                filtro = txtbuscar.getText();
                //va a buscar en la colunna 1 y buscar lo que esta almacenado en la caja de texto
                trsFiltro.setRowFilter(RowFilter.regexFilter(filtro, 1));
            } catch (Exception e) {
                System.out.println("buscar: " + e.toString());
            }
        }
    }

   private void buscar() {
    txtbuscar.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            String cadena = txtbuscar.getText();
            txtbuscar.setText(cadena);
            repaint();
            
            // Verificar si trsFiltro está inicializado antes de llamar a filtro()
            if (trsFiltro != null) {
                filtro();
            }
        }
    });
}


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmplanilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmplanilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmplanilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmplanilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmplanilla().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxMes;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spFaltas;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtHoras;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtsueldo;
    // End of variables declaration//GEN-END:variables
}
