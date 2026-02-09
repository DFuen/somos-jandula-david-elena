package Reaktor_redes_main.controllers;

import Reaktor_redes_main.dto.EstadoDTO;
import Reaktor_redes_main.models.HistorialEstado;
import Reaktor_redes_main.repository.HistorialRepository;
import Reaktor_redes_main.repository.RedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173") //Para que el navegador me deje conectarlo a Vue
@RestController
@RequestMapping("/registros-redes")
public class EstadoController {

    @Autowired private HistorialRepository historialRepository;
    @Autowired private RedRepository redesguardadas;


    //Peticion de la ESP-32 para guardar el estado de la Red en la BD
    @PostMapping
    public ResponseEntity<?> registrarPrueba(@RequestBody HistorialEstado estado){

        estado.setFechaReporte(Timestamp.from(Instant.now())); //Guarda la fecha actual, más adelante la fecha la dará la ESP-32
        historialRepository.save(estado);
        EstadoDTO nuevoestado = new EstadoDTO(estado.getSsid(), estado.getEstado(), estado.getFechaReporte().toString());

        return ResponseEntity.status(200).body(nuevoestado);
    }


    @GetMapping
    public ResponseEntity<?> consultarhistorial(){

        //Sabiendo el nombre de esas redes, filtramos el historial para devolver el ultimo registro de cada red.
        List<HistorialEstado> listaHistorial = historialRepository.findAll();

        if(listaHistorial.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se han encontrado registros");
        }

        //Filtramos para obtener una Lista con el ultimo registro de cada red

        List<HistorialEstado> ultimosRegistrosHistorial = listaHistorial.stream()
                .collect(Collectors.toMap(
                        HistorialEstado::getSsid,
                        Function.identity(),
                        (h1, h2) -> h1.getFechaReporte().compareTo(h2.getFechaReporte()) > 0 ? h1 : h2
                ))
                .values()
                .stream()
                .collect(Collectors.toList());

        List<EstadoDTO> ultimosRegistrosDTO = ultimosRegistrosHistorial.stream()
                .map(historial -> {
                    EstadoDTO dto = new EstadoDTO();
                    dto.setNombreRed(historial.getSsid());
                    dto.setEstado(historial.getEstado());
                    dto.setFecha_hora(historial.getFechaReporte().toString()); // aquí lo conviertes a String
                    return dto;
                })
                .collect(Collectors.toList());


        return ResponseEntity.status(200).body(ultimosRegistrosDTO);
    }


}
