package Reaktor_redes_main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HistroialRedNotFoundExcepcion extends ResponseStatusException{

    public HistroialRedNotFoundExcepcion(String mensaje){
        super(HttpStatus.NOT_FOUND,mensaje);
    }

}
