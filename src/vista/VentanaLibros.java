package vista;

import controlador.ControladorCategoria;
import controlador.ControladorLibro;
import modelo.Libro;
import modelo.Categoria;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaLibros extends JFrame{
    private JPanel panelPrincipal;
    private JTextField txtId;
    private JButton btnBuscar;
    private JLabel lblId;
    private JLabel lblNombre;
    private JLabel lblAutor;
    private JLabel lblIsbn;
    private JLabel lblStock;
    private JLabel lblCategoria;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtIsbn;
    private JTextField txtStock;
    private JComboBox<Categoria> cbCategoria;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JTable tablaLibros;
    private JLabel lblTitulo;
    private JTextField txtEditorial;
    private JLabel lblEditorial;

    private ControladorLibro controladorLibro;
    private ControladorCategoria controladorCategoria;

    public VentanaLibros() {

        controladorLibro = new ControladorLibro();
        controladorCategoria = new ControladorCategoria();

        setTitle("Gestion de Libros");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        cargarCategorias();
        cargarTabla();

        btnGuardar.addActionListener(e -> guardarLibro());
        btnBuscar.addActionListener(e -> buscarLibro());
        btnActualizar.addActionListener(e -> actualizarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void cargarCategorias() {

        cbCategoria.removeAllItems();

        List<Categoria> categorias = controladorCategoria.listar();

        for (Categoria categoria : categorias) {

            cbCategoria.addItem(categoria);
        }
    }

    private void cargarTabla() {

        DefaultTableModel modelo =
                new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Titulo");
        modelo.addColumn("Autor");
        modelo.addColumn("ISBN");
        modelo.addColumn("Editorial");
        modelo.addColumn("Stock");
        modelo.addColumn("Categoria");

        List<Libro> libros = controladorLibro.listar();

        for (Libro libro : libros) {

            modelo.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getEditorial(),
                    libro.getStock(),
                    libro.getIdCategoria()
            });
        }
        tablaLibros.setModel(modelo);
    }

    private void guardarLibro() {

        try {

            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String isbn = txtIsbn.getText().trim();
            String editorial = txtEditorial.getText().trim();
            String stockTexto = txtStock.getText().trim();

            if (titulo.isEmpty() ||
                    autor.isEmpty() ||
                    isbn.isEmpty() ||
                    editorial.isEmpty() ||
                    stockTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe completar todos los campos."
                );
                return;
            }

            int stock = Integer.parseInt(stockTexto);

            if (stock < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El stock no puede ser negativo."
                );
                return;
            }

            int idCategoria = obtenerIdCategoria();

            Libro libro = new Libro(
                    0,
                    titulo,
                    autor,
                    isbn,
                    editorial,
                    stock,
                    idCategoria
            );

            controladorLibro.guardar(libro);

            JOptionPane.showMessageDialog(
                    this,
                    "Libro guardado correctamente."
            );
            cargarTabla();
            limpiarCampos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe ser un numero."
            );
        }
    }

    private void buscarLibro() {

        try {

            String idTexto = txtId.getText().trim();

            if (idTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese un ID."
                );
                return;
            }

            int id = Integer.parseInt(idTexto);

            Libro libro = controladorLibro.buscarPorId(id);

            if (libro != null) {

                txtTitulo.setText(libro.getTitulo());
                txtAutor.setText(libro.getAutor());
                txtIsbn.setText(libro.getIsbn());
                txtEditorial.setText(libro.getEditorial());
                txtStock.setText(
                        String.valueOf(libro.getStock())
                );

                seleccionarCategoria(
                        libro.getIdCategoria()
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontro el libro."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un numero."
            );
        }
    }

    private void actualizarLibro() {

        try {

            String idTexto = txtId.getText().trim();

            if (idTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese el ID del libro."
                );
                return;
            }

            int id = Integer.parseInt(idTexto);

            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String isbn = txtIsbn.getText().trim();
            String editorial = txtEditorial.getText().trim();
            String stockTexto = txtStock.getText().trim();

            if (titulo.isEmpty() ||
                    autor.isEmpty() ||
                    isbn.isEmpty() ||
                    editorial.isEmpty() ||
                    stockTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe completar todos los campos."
                );
                return;
            }

            int stock = Integer.parseInt(stockTexto);

            if (stock < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El stock no puede ser negativo."
                );
                return;
            }

            int idCategoria = obtenerIdCategoria();

            Libro libro = new Libro(
                    id,
                    titulo,
                    autor,
                    isbn,
                    editorial,
                    stock,
                    idCategoria
            );

            controladorLibro.actualizar(libro);

            JOptionPane.showMessageDialog(
                    this,
                    "Libro actualizado correctamente."
            );
            cargarTabla();
            limpiarCampos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID y stock deben ser numeros."
            );
        }
    }

    private void eliminarLibro() {

        try {

            String idTexto = txtId.getText().trim();

            if (idTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese el ID del libro."
                );
                return;
            }

            int id = Integer.parseInt(idTexto);

            int opcion =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Desea eliminar este libro?",
                            "Confirmar eliminacion",
                            JOptionPane.YES_NO_OPTION
                    );

            if (opcion == JOptionPane.YES_OPTION) {

                boolean eliminado = controladorLibro.eliminar(id);

                if (eliminado) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Libro eliminado correctamente."
                    );

                    cargarTabla();
                    limpiarCampos();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "No se pudo eliminar el libro."
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

    private int obtenerIdCategoria() {

        Categoria categoria = (Categoria) cbCategoria.getSelectedItem();

        if (categoria == null) {
            return 0;
        }
        return categoria.getId();
    }

    private void seleccionarCategoria(int idCategoria) {

        for (int i = 0; i < cbCategoria.getItemCount(); i++) {

            Categoria categoria = cbCategoria.getItemAt(i);

            if (categoria.getId() == idCategoria) {

                cbCategoria.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limpiarCampos() {

        txtId.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtEditorial.setText("");
        txtStock.setText("");

        if (cbCategoria.getItemCount() > 0) {
            cbCategoria.setSelectedIndex(0);
        }

        tablaLibros.clearSelection();
    }
}
