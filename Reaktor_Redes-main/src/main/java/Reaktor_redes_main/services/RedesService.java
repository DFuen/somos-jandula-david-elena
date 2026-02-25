package Reaktor_redes_main.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Reaktor_redes_main.dto.RedDTO;
import Reaktor_redes_main.exceptions.RedesBadRequestException;
import Reaktor_redes_main.models.Red;
import Reaktor_redes_main.repository.RedRepository;

@Service
public class RedesService {

    @Autowired
    private RedRepository redRepository;
    

    public Red altaRed(Red nuevared){

        List<Red> listaRedes = redRepository.findAll();

        //COMPROBACIÓN DE ERRORES EN VACÍOS O NULL
        String ssid = nuevared.getSsid();
        String contrasena = nuevared.getPassword();

        for(Red r : listaRedes){
            if(ssid.equals(r.getSsid())){
                throw new RedesBadRequestException("Ya existe una red con ese nombre");
            }
        }

        if(ssid.isEmpty() || contrasena.isEmpty()){
            throw new RedesBadRequestException("La contraseña , el usuario o el SSID no pueden estar vacios");
        }
        if(ssid==null || contrasena==null){
            throw new RedesBadRequestException("La contraseña , el usuario o el SSID no pueden estar nulo");
        }
        else{
            return redRepository.save(nuevared);
        }
    }

    public List<RedDTO> listarObjetivos(){
        //CREACIÓN DE LA LISTA DE REDES DTO
        List<RedDTO> listaRedesDTO = new ArrayList<>();

        //LISTAMOS TODAS LAS REDES
        List<Red> listaRedes = redRepository.findAll();

        //PASAMOS TODAS LAS REDES A DTO Y LAS GUARDAMOS EN UNA LISTA NUEVA
        for(Red r : listaRedes){
            listaRedesDTO.add(new RedDTO(r.getSsid(),r.getPassword(),r.getSeguridad()));
        }
        return listaRedesDTO;
    }

    public void eliminarRed(String ssid){
        List<Red> listaRedes = redRepository.findAll();
        for(Red r : listaRedes){
            if(ssid.equals(r.getSsid())){
                redRepository.delete(r);
            }
        }
    }

}
