package Reaktor_redes_main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Reaktor_redes_main.dto.TemporizadorDTO;
import Reaktor_redes_main.models.Temporizador;
import Reaktor_redes_main.services.TemporizadorServices;

@RestController
@RequestMapping("/temporizador")
public class TemporizadorController {

    @Autowired
    private TemporizadorServices temporizadorServices;


    @GetMapping
    public TemporizadorDTO obtenerTemporizador(){
        return temporizadorServices.obtenerTemporizador();
    }

    @PutMapping
    public TemporizadorDTO actualizarTemporizador(Temporizador temporizador){
        return temporizadorServices.actualizarTemporizador(temporizador);
    }

}
