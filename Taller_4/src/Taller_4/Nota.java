package Taller_4;

public class Nota {
    private String rut;
    private String codigoCurso;
    private double calificacion;
    private String estado;
    private String semestre;
    
    public Nota(String rut, String codigoCurso, double calificacion, String estado, String semestre) {
        this.rut = rut;
        this.codigoCurso = codigoCurso;
        this.calificacion = calificacion;
        this.estado = estado;
        this.semestre = semestre;
    }
    
    // Getters y Setters
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    
    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }
    
    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }
}
