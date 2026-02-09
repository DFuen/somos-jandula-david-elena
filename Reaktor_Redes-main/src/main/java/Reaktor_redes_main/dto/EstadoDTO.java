package Reaktor_redes_main.dto;

public class EstadoDTO {
    private String nombreRed;
    private String estado = "SIN_INFORMACION";
    private String fecha_hora;

    public EstadoDTO() {
    }

    public EstadoDTO(String nombreRed, String estado, String fecha_hora) {
        this.nombreRed = nombreRed;
        this.estado = estado;
        this.fecha_hora = fecha_hora;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreRed() {
        return nombreRed;
    }

    public void setNombreRed(String nombreRed) {
        this.nombreRed = nombreRed;
    }
}
