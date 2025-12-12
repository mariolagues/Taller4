package Taller_4;

public class Registro {
    private String rut;
    private String idCertificacion;
    private String fechaRegistro;
    private String estado;
    private int progreso;
    
    public Registro(String rut, String idCertificacion, String fechaRegistro, String estado, int progreso) {
        this.rut = rut;
        this.idCertificacion = idCertificacion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.progreso = progreso;
    }
    
    // Getters y Setters
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    
    public String getIdCertificacion() { return idCertificacion; }
    public void setIdCertificacion(String idCertificacion) { this.idCertificacion = idCertificacion; }
    
    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public int getProgreso() { return progreso; }
    public void setProgreso(int progreso) { this.progreso = progreso; }
}