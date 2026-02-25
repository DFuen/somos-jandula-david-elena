package Reaktor_redes_main.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import Reaktor_redes_main.models.Red;
import Reaktor_redes_main.services.RedesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuracion-redes")
public class RedesController {


    @Autowired
    private RedesService redesService;

    //Añadir una red a la BD
    @PostMapping
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<?> aLtaRed(@RequestBody Red nuevared){
        return ResponseEntity.status(200).body(redesService.altaRed(nuevared) + "Red creada con exito y guardada en la BD");
    }

    //Listar la información de las redes guardadas
    @GetMapping
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<?> listarObjetivos(){
        //RETORNAMOS LA LISTA A AUDITAR PARA EL HARDWARE
        return ResponseEntity.status(200).body(redesService.listarObjetivos());
    }

}
