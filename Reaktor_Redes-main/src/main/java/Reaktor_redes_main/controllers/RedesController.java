package Reaktor_redes_main.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import Reaktor_redes_main.models.Red;
import Reaktor_redes_main.services.RedesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173") //Para que el navegador me deje conectarlo a Vue
@RestController
@RequestMapping("/configuracion-redes")
public class RedesController {


    @Autowired
    private RedesService redesService;

    //Añadir una red a la BD
    @PostMapping
    @PreAuthorize("hasAnyAuthority('PROFESOR', 'ROLE_PROFESOR')")
    public ResponseEntity<?> aLtaRed(@RequestBody Red nuevared){
        return ResponseEntity.status(200).body(redesService.altaRed(nuevared) + "Red creada con exito y guardada en la BD");
    }

    @DeleteMapping("/{ssid}")
    @PreAuthorize("hasAnyAuthority('PROFESOR', 'ROLE_PROFESOR')")
    public ResponseEntity<?> eliminarRed(@PathVariable String ssid){
        redesService.eliminarRed(ssid);
        return ResponseEntity.status(200).body("Red eliminada con exito");
    }

    //Listar la información de las redes guardadas
    @GetMapping
    @PreAuthorize("hasAnyAuthority('PROFESOR', 'ROLE_PROFESOR')")
    public ResponseEntity<?> listarObjetivos(){
        //RETORNAMOS LA LISTA A AUDITAR PARA EL HARDWARE
        return ResponseEntity.status(200).body(redesService.listarObjetivos());
    }

}
