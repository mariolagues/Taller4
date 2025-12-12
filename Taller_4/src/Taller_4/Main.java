package Taller_4;
//Mario Lagues - 21974580K - ICCI
//Cristobal Espinoza - 217356121 - ICCI

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static Scanner sc;
    private static Sistema sistema = Sistema.getInstancia();
    
    public static void main(String[] args) throws FileNotFoundException {
        // Cargar datos
        leerUsuario();
        leerEstudiantes();
        leerCursos();
        leerCertificaciones();
        leerRegistros();
        leerNotas();
        leerAsignaturasCertificaciones();
        
        // Iniciar interfaz gráfica
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }

    private static void leerAsignaturasCertificaciones() throws FileNotFoundException {
        sc = new Scanner(new File("asignaturas_certificaciones.txt"));
        while(sc.hasNextLine()) {
            String datos[] = sc.nextLine().split(";");
            if(datos.length >= 2) {
                String idCertificacion = datos[0];
                String codigoCurso = datos[1];
                sistema.agregarAsignaturaCertificacion(idCertificacion, codigoCurso);
            }
        }
        sc.close();
    }

    private static void leerNotas() throws FileNotFoundException {
        sc = new Scanner(new File("notas.txt"));
        while(sc.hasNextLine()) {
            String datos[] = sc.nextLine().split(";");
            if(datos.length >= 5) {
                String rut = datos[0];
                String codigoCurso = datos[1];
                double calificacion = Double.parseDouble(datos[2]);
                String estado = datos[3];
                String semestre = datos[4];
                sistema.agregarNota(rut, codigoCurso, calificacion, estado, semestre);
            }
        }
        sc.close();
    }

    private static void leerRegistros() throws FileNotFoundException {
        sc = new Scanner(new File("registros.txt"));
        while(sc.hasNextLine()) {
            String datos[] = sc.nextLine().split(";");
            if(datos.length >= 5) {
                String rut = datos[0];
                String idCertificacion = datos[1];
                String fechaRegistro = datos[2];
                String estado = datos[3];
                int progreso = Integer.parseInt(datos[4]);
                sistema.agregarRegistro(rut, idCertificacion, fechaRegistro, estado, progreso);
            }
        }
        sc.close();
    }

    private static void leerCertificaciones() throws FileNotFoundException {
        sc = new Scanner(new File("certificaciones.txt"));
        while(sc.hasNextLine()) {
            String datos[] = sc.nextLine().split(";");
            if(datos.length >= 5) {
                String id = datos[0];
                String nombre = datos[1];
                String descripcion = datos[2];
                int creditosRequeridos = Integer.parseInt(datos[3]);
                int añosValidez = Integer.parseInt(datos[4]);
                sistema.agregarCertificacion(id, nombre, descripcion, creditosRequeridos, añosValidez);
            }
        }
        sc.close();
    }

    private static void leerCursos() throws FileNotFoundException {
        sc = new Scanner(new File("cursos.txt"));
        while(sc.hasNextLine()) {
            String datos[] = sc.nextLine().split(";");
            if(datos.length >= 5) {
                String nrc = datos[0];
                String nombre = datos[1];
                int semestre = Integer.parseInt(datos[2]);
                int creditos = Integer.parseInt(datos[3]);
                String area = datos[4];
                String prerrequisitos = datos.length > 5 ? datos[5] : "";
                sistema.agregarCurso(nrc, nombre, semestre, creditos, area, prerrequisitos);
            }
        }
        sc.close();
    }

    private static void leerEstudiantes() throws FileNotFoundException {
        sc = new Scanner(new File("estudiantes (1).txt"));
        while(sc.hasNextLine()) {
            String datos[] = sc.nextLine().split(";");
            if(datos.length >= 6) {
                String rut = datos[0];
                String nombre = datos[1];
                String carrera = datos[2];
                int semestre = Integer.parseInt(datos[3]);
                String correo = datos[4];
                String contraseña = datos[5];
                sistema.agregarEstudiante(rut, nombre, carrera, semestre, correo, contraseña);
            }
        }
        sc.close();
    }

    private static void leerUsuario() throws FileNotFoundException {
        sc = new Scanner(new File("usuarios.txt"));
        while(sc.hasNextLine()) {
            String datos[] = sc.nextLine().split(";");
            if(datos.length >= 3) {
                String nombreUsuario = datos[0];
                String contraseña = datos[1];
                String rol = datos[2];
                String area = datos.length > 3 ? datos[3] : "";
                sistema.agregarUsuario(nombreUsuario, contraseña, rol, area);
            }
        }
        sc.close();
    }
    
    public static Sistema getSistema() {
        return sistema;
    }
}