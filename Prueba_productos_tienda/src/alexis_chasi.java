import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class alexis_chasi {
    private JButton ingresarButton;
    private JButton buscarButton;
    private JButton actualizarButton;
    private JButton borrarButton;
    private JTextField ingresarID;
    private JTextField ingresarNombreProducto;
    private JTextField ingresarDetProducto;
    private JComboBox infocomboBox1;
    private JTextField ingresarCostoProducto;
    private JTextField ingresarDirTienda;
    private JTextField ingresarCiuTienda;
    private JComboBox infocomboBox2;
    private JPanel alexis_chasi;

    Statement st;
    PreparedStatement prepare;

    public alexis_chasi(){



        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarID.setEnabled(true);
                ingresarNombreProducto.setEnabled(true);
                ingresarDetProducto.setEnabled(true);
                ingresarCostoProducto.setEnabled(true);
                ingresarDirTienda.setEnabled(true);
                ingresarCiuTienda.setEnabled(true);
                Connection con;
                try {
                    con = getConection();
                    st= con.createStatement();
                    ResultSet rs;
                    rs=st.executeQuery("select * from bdregistro.tienda where idtienda= " + ingresarID.getText()+";");
                    DefaultComboBoxModel model = new DefaultComboBoxModel();
                    while (rs.next()){
                        infocomboBox1.addItem(rs.getString("idtienda"));
                        infocomboBox1.addItem(rs.getString("nombre_producto"));
                        infocomboBox1.addItem(rs.getString("detalle_producto"));
                        infocomboBox2.addItem(rs.getString("costo_producto"));
                        infocomboBox2.addItem(rs.getString("direccion_tienda"));
                        infocomboBox2.addItem(rs.getString("ciudad_tienda"));
                    }
                    int res = prepare.executeUpdate();

                    if(res > 0){
                        JOptionPane.showMessageDialog(null, "articulo encontrado!! ");
                    }else{
                        JOptionPane.showMessageDialog(null, "articulo no encontrado!!");
                    }
                    con.close();
                }catch (HeadlessException | SQLException f){
                    System.err.println(f);
                }

            }
        });
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;
                try {
                    con = getConection();
                    prepare = con.prepareStatement("INSERT INTO tienda (idtienda, nombre_producto, detalle_producto, costo_producto,direccion_tienda,ciudad_tienda) VALUES(?,?,?,?,?,?) ");
                    prepare.setString(1, ingresarID.getText());
                    prepare.setString(2, ingresarNombreProducto.getText());
                    prepare.setString(3, ingresarDetProducto.getText());
                    prepare.setString(4, ingresarCostoProducto.getText());
                    prepare.setString(5, ingresarDirTienda.getText());
                    prepare.setString(6, ingresarCiuTienda.getText());



                    int res = prepare.executeUpdate();
                    if (res > 0) {
                        JOptionPane.showMessageDialog(null, "ariculo guardado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al Guardar el articulo");
                    }

                    con.close();

                } catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;

                try{
                    con = getConection();
                    prepare = con.prepareStatement("UPDATE tienda SET nombre_producto = ?, detalle_producto = ?, costo_producto = ?,direccion_tienda=?,ciudad_tienda=? WHERE idtienda ="+ingresarID.getText() );


                    prepare.setString(1, ingresarNombreProducto.getText());
                    prepare.setString(2, ingresarDetProducto.getText());
                    prepare.setString(3, ingresarCostoProducto.getText());
                    prepare.setString(4, ingresarDirTienda.getText());
                    prepare.setString(5, ingresarCiuTienda.getText());

                    System.out.println(prepare);
                    int res = prepare.executeUpdate();

                    if(res > 0){
                        JOptionPane.showMessageDialog(null, "articulo modificado correctamente");
                    }else{
                        JOptionPane.showMessageDialog(null, "articulo no modificado");
                    }
                    con.close();

                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = null;
                PreparedStatement prepare = null;
                try {
                    con = getConection();
                    prepare = con.prepareStatement("DELETE FROM bdregistro.tienda WHERE idtienda = ?");
                    prepare.setInt(1, Integer.parseInt(ingresarID.getText()));

                    int res = prepare.executeUpdate();
                    if (res > 0) {
                        JOptionPane.showMessageDialog(null, "articulo eliminada de la BD");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar articulo de la BD");
                    }
                    con.close();
                } catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });
    }

    public static void main(String[] arg) {
        JFrame tienda = new JFrame("alexis_chasi");
        alexis_chasi mi_tienda = new alexis_chasi();
        tienda.setContentPane(mi_tienda.alexis_chasi);
        tienda.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        tienda.pack();
        tienda.setVisible(true);
    }

    public static Connection getConection() {
        Connection con = null;
        String base = "bdregistro"; //Nombre de la base de datos
        String url = "jdbc:mysql://localhost:3306/" + base; //Direccion, puerto y nombre de la Base de Datos
        String user = "root"; //Usuario de Acceso a MySQL
        String password = "alexis16"; //Password del usuario

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        return con;
    }
}
