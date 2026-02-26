package Reaktor_redes_main.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import Reaktor_redes_main.dto.EstadoDTO;
import Reaktor_redes_main.exceptions.HistroialRedNotFoundExcepcion;
import Reaktor_redes_main.models.HistorialEstado;
import Reaktor_redes_main.models.Red;
import Reaktor_redes_main.repository.HistorialRepository;
import Reaktor_redes_main.repository.RedRepository;

@Service
public class EstadoServices {

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private RedRepository redRepository;

    public HistorialEstado registrarPrueba(HistorialEstado estado) {

        List<Red> listaRedes = redRepository.findAll();

        for(Red r : listaRedes){
            if(estado.getSsid().equals(r.getSsid())){
                estado.setFechaReporte(Timestamp.from(Instant.now())); //Guarda la fecha actual, más adelante la fecha la dará la ESP-32
                historialRepository.save(estado);
                return estado;       
            }
        }
        throw new HistroialRedNotFoundExcepcion("No existe esta en la lista de redes");  
    }


public List<EstadoDTO> consultarHistorial() {
    List<EstadoDTO> ultimosRegistrosDTO = new ArrayList<>();
    List<Red> listaRedes = redRepository.findAll();
    
    for (Red red : listaRedes) {
        HistorialEstado ultimoRegistro = historialRepository.findTop1BySsidOrderByFechaReporteDesc(red.getSsid());

        EstadoDTO estadoDTO = ultimoRegistro != null
            ? new EstadoDTO(
                ultimoRegistro.getSsid(),
                ultimoRegistro.getEstado(),
                pasarAFecha(ultimoRegistro.getFechaReporte()),
                pasaraHora(ultimoRegistro.getFechaReporte())
            )
            : new EstadoDTO(
                red.getSsid(),
                "SIN_INFORMACION",
                null,
                null
            );
        
        ultimosRegistrosDTO.add(estadoDTO);
    }
    
    return ultimosRegistrosDTO;
}


public String pasarAFecha(Timestamp fechahora) {
    if (fechahora == null) {
        return null;
    }

    LocalDateTime fecha = fechahora.toLocalDateTime();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "EEEE, d 'de' MMMM 'de' yyyy",
            new Locale("es", "ES")
    );

    String fechaFormateada = fecha.format(formatter);

    // Poner primera letra en mayúscula
    return fechaFormateada.substring(0,1).toUpperCase()
            + fechaFormateada.substring(1);
}



//Pasar a hora
    public String pasaraHora(Timestamp fechahora){
        if (fechahora == null) {
            return null;
        }

        //Convierte el Timestamp a String y devuelve solo la parte de la hora (sin la fecha)
        return fechahora.toString().substring(11, 16);

    }

}