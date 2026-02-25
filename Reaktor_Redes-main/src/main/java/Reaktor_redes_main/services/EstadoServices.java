package Reaktor_redes_main.services;

import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                    // Separar fecha y hora
                    String fechaHoraCompleta = historial.getFechaReporte().toString();
                    String fecha = "";
                    String hora = "";
                    if (fechaHoraCompleta.contains(" ")) {
                        String[] partes = fechaHoraCompleta.split(" ");
                        fecha = partes[0];
                        hora = partes[1].substring(0, 5); // HH:mm
                    } else if (fechaHoraCompleta.contains("T")) { // ISO format
                        String[] partes = fechaHoraCompleta.split("T");
                        fecha = partes[0];
                        hora = partes[1].substring(0, 5); // HH:mm
                    }
                    dto.setFecha(fecha);
                    dto.setHora(hora);
                    return dto;
                })
                .collect(Collectors.toList());
        return ultimosRegistrosDTO;

    }
}