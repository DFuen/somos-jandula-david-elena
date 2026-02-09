package Reaktor_redes_main.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "historial_estados")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistorialEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ssid")
    private String ssid;

    @Column
    private String estado = "SIN_INFORMACION";

    @Column(name = "fecha_reporte", updatable = false)
    private Timestamp fechaReporte;

}