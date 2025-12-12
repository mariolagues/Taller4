package Taller_4;

public class AsignaturaCertificacion {
    private String idCertificacion;
    private String codigoCurso;
    
    public AsignaturaCertificacion(String idCertificacion, String codigoCurso) {
        this.idCertificacion = idCertificacion;
        this.codigoCurso = codigoCurso;
    }
    
    // Getters y Setters
    public String getIdCertificacion() { return idCertificacion; }
    public void setIdCertificacion(String idCertificacion) { this.idCertificacion = idCertificacion; }
    
    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }
}