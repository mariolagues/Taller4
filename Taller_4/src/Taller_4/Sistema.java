package Taller_4;

import java.util.ArrayList;
import java.util.List;

public class Sistema implements ISistema {
    private static Sistema instancia;
    private List<Usuario> usuarios;
    private List<Estudiante> estudiantes;
    private List<Curso> cursos;
    private List<Certificacion> certificaciones;
    private List<Registro> registros;
    private List<Nota> notas;
    private List<AsignaturaCertificacion> asignaturasCertificaciones;
    
    private Sistema() {
        usuarios = new ArrayList<>();
        estudiantes = new ArrayList<>();
        cursos = new ArrayList<>();
        certificaciones = new ArrayList<>();
        registros = new ArrayList<>();
        notas = new ArrayList<>();
        asignaturasCertificaciones = new ArrayList<>();
    }
    
    public static Sistema getInstancia() {
        if(instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }
    
    @Override
    public void agregarUsuario(String nombreUsuario, String contraseña, String rol, String area) {
        Usuario usuario = new Usuario(nombreUsuario, contraseña, rol, area);
        usuarios.add(usuario);
    }
    
    @Override
    public void agregarEstudiante(String rut, String nombre, String carrera, int semestre, String correo, String contraseña) {
        Estudiante estudiante = new Estudiante(rut, nombre, carrera, semestre, correo, contraseña);
        estudiantes.add(estudiante);
    }
    
    @Override
    public void agregarCurso(String nrc, String nombre, int semestre, int creditos, String area, String prerrequisitos) {
        Curso curso = new Curso(nrc, nombre, semestre, creditos, area, prerrequisitos);
        cursos.add(curso);
    }
    
    @Override
    public void agregarCertificacion(String id, String nombre, String descripcion, int creditosRequeridos, int añosValidez) {
        Certificacion certificacion = new Certificacion(id, nombre, descripcion, creditosRequeridos, añosValidez);
        certificaciones.add(certificacion);
    }
    
    @Override
    public void agregarRegistro(String rut, String idCertificacion, String fechaRegistro, String estado, int progreso) {
        Registro registro = new Registro(rut, idCertificacion, fechaRegistro, estado, progreso);
        registros.add(registro);
    }
    
    @Override
    public void agregarNota(String rut, String codigoCurso, double calificacion, String estado, String semestre) {
        Nota nota = new Nota(rut, codigoCurso, calificacion, estado, semestre);
        notas.add(nota);
    }
    
    @Override
    public void agregarAsignaturaCertificacion(String idCertificacion, String codigoCurso) {
        AsignaturaCertificacion asignaturaCert = new AsignaturaCertificacion(idCertificacion, codigoCurso);
        asignaturasCertificaciones.add(asignaturaCert);
    }
    
    // Métodos para obtener las listas
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Estudiante> getEstudiantes() { return estudiantes; }
    public List<Curso> getCursos() { return cursos; }
    public List<Certificacion> getCertificaciones() { return certificaciones; }
    public List<Registro> getRegistros() { return registros; }
    public List<Nota> getNotas() { return notas; }
    public List<AsignaturaCertificacion> getAsignaturasCertificaciones() { return asignaturasCertificaciones; }
}