package Reaktor_redes_main.exceptions;

import org.springframework.web.server.ResponseStatusException;

public class RedesBadRequestException extends ResponseStatusException {
    public RedesBadRequestException(String mensaje) {
        super(org.springframework.http.HttpStatus.BAD_REQUEST, mensaje);
    }
}
