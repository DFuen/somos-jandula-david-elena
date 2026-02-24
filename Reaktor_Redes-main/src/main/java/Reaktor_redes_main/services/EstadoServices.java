package Reaktor_redes_main.services;

import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import Reaktor_redes_main.dto.EstadoDTO;
import Reaktor_redes_main.models.HistorialEstado;
import Reaktor_redes_main.repository.HistorialRepository;

@Service
public class EstadoServices {

    @Autowired
    private HistorialRepository historialRepository;

    public HistorialEstado registrarPrueba(HistorialEstado estado) {

        estado.setFechaReporte(Timestamp.from(Instant.now())); //Guarda la fecha actual, más adelante la fecha la dará la ESP-32
        historialRepository.save(estado);
        //EstadoDTO nuevoestado = new EstadoDTO(estado.getSsid(), estado.getEstado(), estado.getFechaReporte().toString());
        return estado;
    }


    public List<EstadoDTO> consultarHistorial(){
        //Sabiendo el nombre de esas redes, filtramos el historial para devolver el ultimo registro de cada red.
        List<HistorialEstado> listaHistorial = historialRepository.findAll();

        List<HistorialEstado> ultimosRegistrosHistorial = listaHistorial.stream()
                .collect(Collectors.toMap(
                        HistorialEstado::getSsid,
                        Function.identity(),
                        (h1, h2) -> h1.getFechaReporte().compareTo(h2.getFechaReporte()) > 0 ? h1 : h2
                ))
                .values()
                .stream()
                .collect(Collectors.toList());

        //Filtramos para obtener una Lista con el ultimo registro de cada red
        List<EstadoDTO> ultimosRegistrosDTO = ultimosRegistrosHistorial.stream()
                .map(historial -> {
                    EstadoDTO dto = new EstadoDTO();
                    dto.setNombreRed(historial.getSsid());
                    dto.setEstado(historial.getEstado());
                    dto.setFecha_hora(historial.getFechaReporte().toString()); // aquí lo conviertes a String
                    return dto;
                })
                .collect(Collectors.toList());
        return ultimosRegistrosDTO;

    }
}