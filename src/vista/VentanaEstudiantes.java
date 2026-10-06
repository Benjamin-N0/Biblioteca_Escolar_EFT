package vista;

import controlador.ControladorEstudiante;
import modelo.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class VentanaEstudiantes extends JFrame{
    private JPanel panelPrincipal;
    private JTextField txtId;
    private JButton btnBuscar;
    private JButton btnGuardar;
    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCurso;
    private JTextField txtCorreo;
    private JButton btnLimpiar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JTable tablaEstudiantes;
    private JLabel lblId;
    private JLabel lblNombre;
    private JLabel lblRut;
    private JLabel lblCurso;
    private JLabel lblCorreo;

    private ControladorEstudiante controladorEstudiante;

    public VentanaEstudiantes() {

        controladorEstudiante =
                new ControladorEstudiante();

        setTitle("Gestion de Estudiantes");
        setContentPane(panelPrincipal);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setSize(900, 550);
        setLocationRelativeTo(null);

        cargarTabla();

        btnGuardar.addActionListener(
                e -> guardarEstudiante()
        );

        btnBuscar.addActionListener(
                e -> buscarEstudiante()
        );

        btnActualizar.addActionListener(
                e -> actualizarEstudiante()
        );

        btnEliminar.addActionListener(
                e -> eliminarEstudiante()
        );

        btnLimpiar.addActionListener(
                e -> limpiarCampos()
        );
    }

    private void cargarTabla() {

        DefaultTableModel modelo =
                new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("RUT");
        modelo.addColumn("Curso");
        modelo.addColumn("Correo");

        List<Estudiante> estudiantes =
                controladorEstudiante.listar();

        for (Estudiante estudiante : estudiantes) {

            modelo.addRow(new Object[]{
                    estudiante.getId(),
                    estudiante.getNombre(),
                    estudiante.getRut(),
                    estudiante.getCurso(),
                    estudiante.getCorreo()
            });
        }

        tablaEstudiantes.setModel(modelo);
    }

    private void guardarEstudiante() {

        String nombre =
                txtNombre.getText().trim();

        String rut =
                txtRut.getText().trim();

        String curso =
                txtCurso.getText().trim();

        String correo =
                txtCorreo.getText().trim();

        if (nombre.isEmpty() ||
                rut.isEmpty() ||
                curso.isEmpty() ||
                correo.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos."
            );

            return;
        }

        Estudiante estudiante =
                new Estudiante(
                        0,
                        nombre,
                        rut,
                        curso,
                        correo
                );

        controladorEstudiante.guardar(
                estudiante
        );

        JOptionPane.showMessageDialog(
                this,
                "Estudiante guardado correctamente."
        );

        cargarTabla();
        limpiarCampos();
    }

    private void buscarEstudiante() {

        try {

            String idTexto =
                    txtId.getText().trim();

            if (idTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese el ID del estudiante."
                );

                return;
            }

            int id =
                    Integer.parseInt(idTexto);

            Estudiante estudiante =
                    controladorEstudiante.buscarPorId(id);

            if (estudiante != null) {

                txtNombre.setText(
                        estudiante.getNombre()
                );

                txtRut.setText(
                        estudiante.getRut()
                );

                txtCurso.setText(
                        estudiante.getCurso()
                );

                txtCorreo.setText(
                        estudiante.getCorreo()
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontro el estudiante."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un numero."
            );
        }
    }

    private void actualizarEstudiante() {

        try {

            String idTexto =
                    txtId.getText().trim();

            if (idTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese el ID del estudiante."
                );

                return;
            }

            int id =
                    Integer.parseInt(idTexto);

            String nombre =
                    txtNombre.getText().trim();

            String rut =
                    txtRut.getText().trim();

            String curso =
                    txtCurso.getText().trim();

            String correo =
                    txtCorreo.getText().trim();

            if (nombre.isEmpty() ||
                    rut.isEmpty() ||
                    curso.isEmpty() ||
                    correo.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe completar todos los campos."
                );

                return;
            }

            Estudiante estudiante =
                    new Estudiante(
                            id,
                            nombre,
                            rut,
                            curso,
                            correo
                    );

            controladorEstudiante.actualizar(
                    estudiante
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante actualizado correctamente."
            );

            cargarTabla();
            limpiarCampos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un numero."
            );
        }
    }

    private void eliminarEstudiante() {

        try {

            String idTexto =
                    txtId.getText().trim();

            if (idTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese el ID del estudiante."
                );

                return;
            }

            int id =
                    Integer.parseInt(idTexto);

            int opcion =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Desea eliminar este estudiante?",
                            "Confirmar eliminacion",
                            JOptionPane.YES_NO_OPTION
                    );

            if (opcion == JOptionPane.YES_OPTION) {

                boolean eliminado =
                        controladorEstudiante.eliminar(id);

                if (eliminado) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Estudiante eliminado correctamente."
                    );

                    cargarTabla();
                    limpiarCampos();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "No se pudo eliminar el estudiante."
                    );
                }
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un numero."
            );
        }
    }

    private void limpiarCampos() {

        txtId.setText("");
        txtNombre.setText("");
        txtRut.setText("");
        txtCurso.setText("");
        txtCorreo.setText("");

        tablaEstudiantes.clearSelection();
    }
}
