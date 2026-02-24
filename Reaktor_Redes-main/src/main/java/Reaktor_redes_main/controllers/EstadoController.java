package Reaktor_redes_main.controllers;

import Reaktor_redes_main.models.HistorialEstado;
import Reaktor_redes_main.services.EstadoServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:5173") //Para que el navegador me deje conectarlo a Vue
@RestController
@RequestMapping("/registros-redes")
public class EstadoController {

    @Autowired private EstadoServices estadoServices;
    //@Autowired private RedRepository redesguardadas;


    //Peticion de la ESP-32 para guardar el estado de la Red en la BD
    @PostMapping
    public ResponseEntity<?> registrarPrueba(@RequestBody HistorialEstado estado){
       return ResponseEntity.status(200).body(estadoServices.registrarPrueba(estado));
    }


    @GetMapping
    public ResponseEntity<?> consultarhistorial(){
        return ResponseEntity.status(200).body(estadoServices.consultarHistorial());
    }


}
