package pt.ulisboa.tecnico.rnl.dei.dms.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntitityAlreadyExists extends RuntimeException {
    public EntitityAlreadyExists(String message) {
        super(message);
    }
}
