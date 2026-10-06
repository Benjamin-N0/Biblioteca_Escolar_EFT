package controlador;

import modelo.Libro;
import modelo.Prestamo;

public class OperacionPrestamo {

    private ServicioPrestamo servicioPrestamo;
    private ServicioLibro servicioLibro;

    public OperacionPrestamo() {

        servicioPrestamo = new ServicioPrestamo();
        servicioLibro = new ServicioLibro();
    }

    public synchronized boolean realizarPrestamo(
            Prestamo prestamo) {

        int idLibro = prestamo.getIdLibro();

        Libro libro = servicioLibro.buscarLibro(idLibro);

        if (libro == null) {

            return false;
        }

        if (libro.getStock() <= 0) {

            return false;
        }

        servicioPrestamo.registrarPrestamo(prestamo
        );

        servicioLibro.descontarStock(
                idLibro
        );
        return true;
    }

    public synchronized boolean realizarDevolucion(
            Prestamo prestamo) {

        if (prestamo == null) {

            return false;
        }

        if (prestamo.isDevuelto()) {

            return false;
        }

        prestamo.setDevuelto(true);

        servicioPrestamo.actualizarPrestamo(
                prestamo
        );

        servicioLibro.aumentarStock(
                prestamo.getIdLibro()
        );
        return true;
    }
}