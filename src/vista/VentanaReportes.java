package vista;

import controlador.ControladorEstudiante;
import controlador.ControladorReporte;
import modelo.Estudiante;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class VentanaReportes extends JFrame{
    private JPanel panelPrincipal;
    private JLabel lblEstudiante;
    private JComboBox cbEstudiante;
    private JButton btnLibrosMasPrestados;
    private JButton btnHistorial;
    private JButton btnLibrosEnPrestamo;
    private JButton btnLimpiar;
    private JTable tablaReportes;

    private ControladorReporte controladorReporte;
    private ControladorEstudiante controladorEstudiante;
    private Usuario usuario;

    public VentanaReportes(Usuario usuario) {

        this.usuario = usuario;
        controladorReporte = new ControladorReporte();
        controladorEstudiante = new ControladorEstudiante();

        setTitle("Reportes");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        cargarEstudiantes();

        btnLibrosMasPrestados.addActionListener(e -> mostrarLibrosMasPrestados());
        btnHistorial.addActionListener(e -> mostrarHistorial());
        btnLibrosEnPrestamo.addActionListener(e -> mostrarLibrosEnPrestamo());
        btnLimpiar.addActionListener(e -> limpiar());
    }

    // Carga los estudiantes desde la base de datos

    private void cargarEstudiantes() {

        cbEstudiante.removeAllItems();

        List<Estudiante> estudiantes = controladorEstudiante.listar();

        if (usuario.getRol().equals("bibliotecario")) {

            // El bibliotecario puede consultar a todos los estudiantes

            for (Estudiante estudiante : estudiantes) {
                cbEstudiante.addItem(estudiante);
            }

        } else if (usuario.getRol().equals("estudiante")) {

            // El estudiante solamente puede consultar su propio historial

            for (Estudiante estudiante : estudiantes) {

                if (estudiante.getRut().equals(usuario.getRut())) {
                    cbEstudiante.addItem(estudiante);
                    break;
                }
            }
        }
    }

    // Muestra los libros mas prestados

    private void mostrarLibrosMasPrestados() {

        List<String[]> lista = controladorReporte.librosMasPrestados();

        String[] columnas = {
                "Libro",
                "Cantidad de prestamos"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (String[] fila : lista) {

            modelo.addRow(fila);
        }

        tablaReportes.setModel(modelo);
    }

    // Muestra el historial del estudiante seleccionado

    private void mostrarHistorial() {

        Estudiante estudiante = (Estudiante) cbEstudiante.getSelectedItem();

        if (estudiante == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un estudiante."
            );
            return;
        }

        List<String[]> lista =
                controladorReporte.historialEstudiante(
                        estudiante.getId()
                );

        String[] columnas = {
                "Libro",
                "Fecha prestamo",
                "Fecha devolucion",
                "Devuelto"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (String[] fila : lista) {

            modelo.addRow(fila);
        }
        tablaReportes.setModel(modelo);
    }

    // Muestra los libros que actualmente estan prestados
    private void mostrarLibrosEnPrestamo() {

        List<String[]> lista = controladorReporte.librosEnPrestamo();

        String[] columnas = {
                "Estudiante",
                "Libro",
                "Fecha prestamo",
                "Fecha devolucion"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (String[] fila : lista) {
            modelo.addRow(fila);
        }

        tablaReportes.setModel(modelo);
    }

    private void limpiar() {

        tablaReportes.setModel(new DefaultTableModel()
        );

        cargarEstudiantes();
    }
}
