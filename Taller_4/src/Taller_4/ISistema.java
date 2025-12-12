package Taller_4;

public interface ISistema {
    void agregarUsuario(String nombreUsuario, String contraseña, String rol, String area);
    void agregarEstudiante(String rut, String nombre, String carrera, int semestre, String correo, String contraseña);
    void agregarCurso(String nrc, String nombre, int semestre, int creditos, String area, String prerrequisitos);
    void agregarCertificacion(String id, String nombre, String descripcion, int creditosRequeridos, int añosValidez);
    void agregarRegistro(String rut, String idCertificacion, String fechaRegistro, String estado, int progreso);
    void agregarNota(String rut, String codigoCurso, double calificacion, String estado, String semestre);
    void agregarAsignaturaCertificacion(String idCertificacion, String codigoCurso);
}
