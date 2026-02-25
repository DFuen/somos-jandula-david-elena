package Reaktor_redes_main.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDTO {
    private String nombreRed;
    private String estado = "SIN_INFORMACION";
    private String fecha;
    private String hora;
}

