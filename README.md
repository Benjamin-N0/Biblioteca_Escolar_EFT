#Evaluación Sumativa Semana 8 – Desarrollo Orientado a Objetos II

## Autor del proyecto
- **Nombre completo:** Benjamin Norambuena
- **Carrera:** Analista Programador Computacional

## Descripcion de esta semana CASO

Sistema de Gestión de Biblioteca Escolar
En el marco de la modernización de la gestión escolar, las bibliotecas requieren herramientas digitales que permitan controlar de manera eficiente la circulación de materiales. Este sistema busca facilitar el registro, préstamo y devolución de libros para estudiantes, así como la gestión de inventario y usuarios por parte del personal administrativo. El software será de escritorio, utilizando Java con interfaz gráfica mediante JFrame, aplicando programación orientada a objetos, conexión a base de datos MySQL (JDBC) y patrones como MVC y DAO. 

 ## 🧱 Estructura general del proyecto
 ```plaintext
📁 lib/
└── mysql-connector-j-26.7.0.jar
📁 src/
├── 📁 controlador/
│ ├── ControladorEstudiante.java
│ ├── ControladorLibro.java
│ ├── ControladorPrestamo.java
│ ├── ControladorReporte.java
│ └── OperacionPrestamo.java
│ ├── 📁 dao/
│     ├── EstudianteDAO.java
│     ├── LibroDAO.java
│     ├── PrestamoDAO.java
│     ├── UsuarioDAO.java
│     │
│     └── 📁 impl/
│         ├── EstudianteDAOImpl.java
│         ├── LibroDAOImpl.java
│         ├── PrestamoDAOImpl.java
│         └── UsuarioDAOImpl.java
│
├── 📁 main/
│     └── Main.java
│
├── 📁 modelo/
│     ├── Persona.java
│     ├── Usuario.java
│     ├── Estudiante.java
│     ├── Libro.java
│     ├── Categoria.java
│     └── Prestamo.java
│
├── 📁 util/
│   └── DatabaseConnection.java
│
└── 📁 vista/
  ├── VentanaLogin.java
  ├── VentanaLogin.form
  ├── VentanaPrincipal.java
  ├── VentanaPrincipal.form
  ├── VentanaLibros.java
  ├── VentanaLibros.form
  ├── VentanaEstudiantes.java
  ├── VentanaEstudiantes.form
  ├── VentanaPrestamos.java
  ├── VentanaPrestamos.form
  ├── VentanaReportes.java
  └── VentanaReportes.form
📁 bd/
└── biblioteca.sql
```

## Clonar proyecto y ejecutar proyecto

1. Copiar la URL del repositorio de GitHub.
2. Abrir IntelliJ IDEA.
3. Seleccionar File → New → Project from Version Control.
4. Seleccionar Git.
5. Pegar la URL del repositorio.
6. Seleccionar la carpeta donde se guardará el proyecto.
7. Presionar Clone.
8. Abrir Main.java y ejecutar el programa.
