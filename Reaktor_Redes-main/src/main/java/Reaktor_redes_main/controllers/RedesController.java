package Reaktor_redes_main.controllers;

import Reaktor_redes_main.models.Red;
import Reaktor_redes_main.repository.RedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/configuracion-redes")
public class RedesController {

    @Autowired
    private RedRepository redesRepository;

    //Añadir una red a la BD
    @PostMapping
    public ResponseEntity<?> aLtaRed(@RequestBody Red nuevared){

        //COMPROBACIÓN DE ERRORES EN VACÍOS O NULL
        String ssid = nuevared.getSsid();
        String contra = nuevared.getPassword();

        if(ssid == null || contra == null){
            return ResponseEntity.status(400).body("Se requieren los campos obligatorios");
        }

        //AÑADIMOS RED A LA BASE DE DATOS + Mensaje de confirmación
        return ResponseEntity.status(200).body(redesRepository.save(nuevared) + "Red creada con exito y guardada en la BD");

    }


    //Listar la información de las redes guardadas
    @GetMapping
    public ResponseEntity<?> listarObjetivos(){
        List<Red> listaRedes = redesRepository.findAll();

        //COMPROBACIÓN SI NO HAY REDES
        if(listaRedes.isEmpty()){
            return ResponseEntity.status(400).body("No hya redes registradas");
        }

        //RETORNAMOS LA LISTA A AUDITAR PARA EL HARDWARE
        return ResponseEntity.status(200).body(listaRedes);

    }

}
