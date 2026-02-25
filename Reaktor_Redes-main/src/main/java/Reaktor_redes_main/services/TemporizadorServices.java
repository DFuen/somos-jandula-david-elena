package Reaktor_redes_main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Reaktor_redes_main.dto.TemporizadorDTO;
import Reaktor_redes_main.models.Temporizador;
import Reaktor_redes_main.repository.TemporizadorRepository;

@Service
public class TemporizadorServices {
    
    @Autowired
    private TemporizadorRepository temporizadorRepository;

    public TemporizadorDTO obtenerTemporizador(){
        return new TemporizadorDTO(temporizadorRepository.findAll().get(0).getMilisegundos());
    }

    public TemporizadorDTO actualizarTemporizador(Temporizador temporizador){
        Temporizador entrada = temporizadorRepository.findById(1L).orElseThrow(
            ()-> new RuntimeException("No existe el temporizador")
        );

        entrada.setMilisegundos(temporizador.getMilisegundos());
        temporizadorRepository.save(entrada);
        return new TemporizadorDTO(entrada.getMilisegundos());
    }


}
