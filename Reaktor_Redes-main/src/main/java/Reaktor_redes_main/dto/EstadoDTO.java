package Reaktor_redes_main.dto;

public class EstadoDTO {
    private String nombreRed;
    private String estado = "SIN_INFORMACION";
    private String fecha;
    private String hora;

  public EstadoDTO(String nombreRed, String estado, String fecha, String hora) {
        this.nombreRed = nombreRed;
        this.estado = estado;
        this.fecha = fecha;
  }
  public EstadoDTO(){
  } 
  public String getEstado() {
      return estado;
  }
  public void setEstado(String estado) {
      this.estado = estado;
  }

  public String getFecha() {
      return fecha;
  } 
  public void setFecha(String fecha) {
      this.fecha = fecha;
  }
  public void setNombreRed(String nombreRed) {
      this.nombreRed = nombreRed;
  }
  public String getNombreRed() {
      return nombreRed;
  }
  public String getHora() {
      return hora;
  }
  public void setHora(String hora) {
      this.hora = hora;
  }
}

