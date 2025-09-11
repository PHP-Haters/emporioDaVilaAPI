package app.emporioDaVila.ExceptionHandlers;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private List<String> validationErrors; // optional, for multiple validation messages


    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message){
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;

    }

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, List<String> validationErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.status = status;
        this.error = error;
        this.validationErrors = validationErrors;
    }
}
