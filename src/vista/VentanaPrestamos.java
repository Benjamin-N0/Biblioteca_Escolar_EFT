package vista;

import controlador.ControladorEstudiante;
import controlador.ControladorLibro;
import controlador.ControladorPrestamo;
import controlador.OperacionPrestamo;
import modelo.Estudiante;
import modelo.Libro;
import modelo.Prestamo;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.List;



public class VentanaPrestamos extends JFrame{
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JTextField txtId;
    private JButton btnBuscar;
    private JLabel lblId;
    private JComboBox<Estudiante> cbEstudiante;
    private JComboBox<Libro> cbLibro;
    private JLabel lblEstudiante;
    private JLabel lblLibro;
    private JLabel lblFechaPrestamo;
    private JTextField txtFechaPrestamo;
    private JLabel lblFechaDevolucion;
    private JTextField txtFechaDevolucion;
    private JCheckBox chkDevuelto;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnDevolver;
    private JButton btnLimpiar;
    private JTable tablaPrestamos;

    private ControladorPrestamo controladorPrestamo;
    private ControladorEstudiante controladorEstudiante;
    private ControladorLibro controladorLibro;
    private OperacionPrestamo operacionPrestamo;
    private Usuario usuario;

    public VentanaPrestamos(Usuario usuario) {

        this.usuario = usuario;
        controladorPrestamo = new ControladorPrestamo();
        controladorEstudiante = new ControladorEstudiante();
        controladorLibro = new ControladorLibro();
        operacionPrestamo = new OperacionPrestamo();

        setTitle("Prestamos y Devoluciones");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        cargarEstudiantes();
        cargarLibros();
        cargarTabla();
        configurarPermisos();
        txtFechaDevolucion.setEditable(false);

        btnGuardar.addActionListener(e -> guardarPrestamo()
        );

        btnBuscar.addActionListener(e -> buscarPrestamo()
        );

        btnActualizar.addActionListener(e -> actualizarPrestamo()
        );

        btnEliminar.addActionListener(e -> eliminarPrestamo()
        );

        btnDevolver.addActionListener(e -> devolverPrestamo()
        );

        btnLimpiar.addActionListener(e -> limpiarCampos()
        );
    }

    private void configurarPermisos() {

        if (usuario.getRol().equals("bibliotecario")) {

            // El bibliotecario tiene acceso completo
            btnGuardar.setEnabled(true);
            btnBuscar.setEnabled(true);
            btnActualizar.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnDevolver.setEnabled(true);
            btnLimpiar.setEnabled(true);

        } else if (usuario.getRol().equals("estudiante")) {

            // El estudiante puede prestar, devolver y consultar
            btnGuardar.setEnabled(true);
            btnBuscar.setEnabled(true);
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnDevolver.setEnabled(true);
            btnLimpiar.setEnabled(true);
        }
    }

    private void cargarEstudiantes() {

        cbEstudiante.removeAllItems();

        List<Estudiante> estudiantes =
                controladorEstudiante.listar();

        if (usuario.getRol().equals("bibliotecario")) {

            // El bibliotecario puede trabajar con cualquier estudiante
            for (Estudiante estudiante : estudiantes) {
                cbEstudiante.addItem(estudiante);
            }
        } else if (usuario.getRol().equals("estudiante")) {

            // El estudiante solamente puede trabajar con su propio registro
            for (Estudiante estudiante : estudiantes) {

                if (estudiante.getRut().equals(usuario.getRut())) {

                    cbEstudiante.addItem(estudiante);
                    break;
                }
            }
        }
    }

    private void cargarLibros() {

        cbLibro.removeAllItems();

        List<Libro> libros = controladorLibro.listar();

        for (Libro libro : libros) {

            cbLibro.addItem(libro);
        }
    }

    private void cargarTabla() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Estudiante");
        modelo.addColumn("Libro");
        modelo.addColumn("Fecha Prestamo");
        modelo.addColumn("Fecha Devolucion");
        modelo.addColumn("Estado");

        List<Prestamo> prestamos = controladorPrestamo.listar();

        for (Prestamo prestamo : prestamos) {

            String nombreEstudiante = obtenerNombreEstudiante(
                    prestamo.getIdEstudiante()
            );

            String tituloLibro = obtenerTituloLibro(
                    prestamo.getIdLibro()
            );

            String estado;

            if (prestamo.isDevuelto()) {

                estado = "DEVUELTO";

            } else if (LocalDate.now().isAfter(prestamo.getFechaDevolucion())) {

                estado = "VENCIDO";

            } else {
                estado = "VIGENTE";
            }

            modelo.addRow(new Object[]{
                    prestamo.getId(),
                    nombreEstudiante,
                    tituloLibro,
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucion(),
                    estado
            });
        }
        tablaPrestamos.setModel(modelo);
    }

    private String obtenerNombreEstudiante(
            int idEstudiante) {

        Estudiante estudiante =
                controladorEstudiante.buscarPorId(
                        idEstudiante
                );

        if (estudiante != null) {

            return estudiante.getNombre();
        }
        return "Sin estudiante";
    }

    private String obtenerTituloLibro(
            int idLibro) {

        Libro libro = controladorLibro.buscarPorId(idLibro
        );

        if (libro != null) {
            return libro.getTitulo();
        }
        return "Sin libro";
    }

    private void guardarPrestamo() {

        Estudiante estudiante = (Estudiante) cbEstudiante.getSelectedItem();
        Libro libro = (Libro) cbLibro.getSelectedItem();
        String fechaPrestamoTexto = txtFechaPrestamo.getText().trim();

        if (estudiante == null ||
                libro == null ||
                fechaPrestamoTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos."
            );
            return;
        }

        try {

            LocalDate fechaPrestamo;

            try {
                fechaPrestamo = LocalDate.parse(txtFechaPrestamo.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "La fecha de prestamo no es valida."
                );
                return;
            }

            LocalDate fechaDevolucion = fechaPrestamo.plusDays(7);
            txtFechaDevolucion.setText(fechaDevolucion.toString()
            );

            if (fechaDevolucion.isBefore(
                    fechaPrestamo)) {

                JOptionPane.showMessageDialog(
                        this,
                        "La fecha de devolucion no puede ser anterior al prestamo."
                );
                return;
            }

            Prestamo prestamo = new Prestamo(
                            0,
                            estudiante.getId(),
                            libro.getId(),
                            fechaPrestamo,
                            fechaDevolucion,
                            false
                    );

            btnGuardar.setEnabled(false);Thread hilo = new Thread(() -> {

                        boolean resultado = operacionPrestamo.realizarPrestamo(prestamo
                                );

                        SwingUtilities.invokeLater(() -> {

                            btnGuardar.setEnabled(true);

                            if (resultado) {

                                JOptionPane.showMessageDialog(
                                        this,
                                        "Prestamo registrado correctamente."
                                );

                                cargarTabla();
                                cargarLibros();
                                limpiarCampos();

                            } else {
                                JOptionPane.showMessageDialog(
                                        this,
                                        "No hay stock disponible para este libro."
                                );
                            }
                        });
                    });

            hilo.start();
        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Las fechas deben tener formato YYYY-MM-DD."
            );
        }
    }

    private void seleccionarEstudiante(int idEstudiante) {

        for (int i = 0; i < cbEstudiante.getItemCount(); i++) {

            Estudiante estudiante = cbEstudiante.getItemAt(i);

            if (estudiante.getId() == idEstudiante) {

                cbEstudiante.setSelectedIndex(i);
                break;
            }
        }
    }

    private void seleccionarLibro(int idLibro) {

        for (int i = 0; i < cbLibro.getItemCount(); i++) {

            Libro libro = cbLibro.getItemAt(i);

            if (libro.getId() == idLibro) {

                cbLibro.setSelectedIndex(i);
                break;
            }
        }
    }

    private void buscarPrestamo() {

        try {

            String idTexto = txtId.getText().trim();

            if (idTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese el ID del prestamo."
                );
                return;
            }

            int id = Integer.parseInt(idTexto);

            Prestamo prestamo = controladorPrestamo.buscarPorId(id);

            if (prestamo == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontro el prestamo."
                );
                return;
            }

            //validacion para que un estudiante no pueda buscar el prestamo de otra persona

            if (usuario.getRol().equals("estudiante")) {

                Estudiante estudiante = controladorEstudiante.buscarPorId(prestamo.getIdEstudiante()
                        );

                if (estudiante == null || !estudiante.getRut().equals(usuario.getRut())) {

                    JOptionPane.showMessageDialog(
                            this,
                            "No puede consultar un prestamo de otro estudiante."
                    );
                    return;
                }
            }

            seleccionarEstudiante(prestamo.getIdEstudiante());
            seleccionarLibro(prestamo.getIdLibro());
            txtFechaPrestamo.setText(prestamo.getFechaPrestamo().toString());
            txtFechaDevolucion.setText(prestamo.getFechaDevolucion().toString());
            chkDevuelto.setSelected(prestamo.isDevuelto());

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un numero."
            );
        }
    }

    private void actualizarPrestamo() {

        try {

            String idTexto = txtId.getText().trim();

            if (idTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese el ID del prestamo."
                );
                return;
            }

            int id = Integer.parseInt(idTexto);

            Estudiante estudiante = (Estudiante) cbEstudiante.getSelectedItem();
            Libro libro = (Libro) cbLibro.getSelectedItem();
            LocalDate fechaPrestamo = LocalDate.parse(txtFechaPrestamo.getText().trim());
            LocalDate fechaDevolucion = LocalDate.parse(txtFechaDevolucion.getText().trim());

            if (fechaDevolucion.isBefore(fechaPrestamo)) {

                JOptionPane.showMessageDialog(
                        this,
                        "La fecha de devolucion no puede ser anterior al prestamo."
                );
                return;
            }

            Prestamo prestamo = new Prestamo(
                            id,
                            estudiante.getId(),
                            libro.getId(),
                            fechaPrestamo,
                            fechaDevolucion,
                            chkDevuelto.isSelected()
                    );

            controladorPrestamo.actualizar(prestamo
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Prestamo actualizado correctamente."
            );

            cargarTabla();
            cargarLibros();
            limpiarCampos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un numero."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Las fechas deben tener formato YYYY-MM-DD."
            );
        }
    }

    private void eliminarPrestamo() {

        try {

            String idTexto = txtId.getText().trim();

            if (idTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese el ID del prestamo."
                );
                return;
            }

            int id = Integer.parseInt(idTexto);

            int opcion = JOptionPane.showConfirmDialog(
                            this,
                            "¿Desea eliminar este prestamo?",
                            "Confirmar eliminacion",
                            JOptionPane.YES_NO_OPTION
                    );

            if (opcion == JOptionPane.YES_OPTION) {

                boolean eliminado =
                        controladorPrestamo.eliminar(
                                id
                        );

                if (eliminado) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Prestamo eliminado correctamente."
                    );

                    cargarTabla();
                    limpiarCampos();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "No se pudo eliminar el prestamo."
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

    private void devolverPrestamo() {

        try {

            String idTexto = txtId.getText().trim();

            if (idTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese el ID del prestamo."
                );
                return;
            }

            int id = Integer.parseInt(idTexto);

            Prestamo prestamo = controladorPrestamo.buscarPorId(id);

            if (prestamo == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontro el prestamo."
                );
                return;
            }

            // Validacion para que un estudiante no pueda devolver el prestamo de otro estudiante
            if (usuario.getRol().equals("estudiante")) {

                Estudiante estudiante = controladorEstudiante.buscarPorId(prestamo.getIdEstudiante()
                        );

                if (estudiante == null ||
                        !estudiante.getRut().equals(usuario.getRut())) {

                    JOptionPane.showMessageDialog(
                            this,
                            "No puede devolver un prestamo de otro estudiante."
                    );
                    return;
                }
            }

            // Verificamos si el prestamo ya fue devuelto
            if (prestamo.isDevuelto()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Este prestamo ya fue devuelto."
                );
                return;
            }

            // Confirmacion antes de realizar la devolucion
            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea registrar la devolucion de este prestamo?",
                    "Confirmar devolucion",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }

            // La devolucion se realiza en un hilo independiente
            Thread hilo = new Thread(() -> {

                boolean resultado = operacionPrestamo.realizarDevolucion(prestamo);

                SwingUtilities.invokeLater(() -> {

                    if (resultado) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Devolucion registrada correctamente."
                        );
                        cargarTabla();
                        cargarLibros();
                        limpiarCampos();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "No se pudo registrar la devolucion."
                        );
                    }
                });
            });
            hilo.start();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un numero."
            );
        }
    }

    private void limpiarCampos() {

        txtId.setText("");
        txtFechaPrestamo.setText("");
        txtFechaDevolucion.setText("");
        chkDevuelto.setSelected(false);

        if (cbEstudiante.getItemCount() > 0) {

            cbEstudiante.setSelectedIndex(0);
        }

        if (cbLibro.getItemCount() > 0) {

            cbLibro.setSelectedIndex(0);
        }
        tablaPrestamos.clearSelection();
    }

}