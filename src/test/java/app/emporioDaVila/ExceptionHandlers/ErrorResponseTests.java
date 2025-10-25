package app.emporioDaVila.ExceptionHandlers;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseTests {

    @Test
    void errorResponse_cenario01() {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                1,
                "Error",
                "Mensagem");
        ErrorResponse errorResponse2 = new ErrorResponse(
            LocalDateTime.now(),
            1,
            "Error",
            "Mensagem");
        assertEquals(errorResponse.getError(), errorResponse2.getError());
    }

    @Test
    void errorResponse_cenario02() {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                1,
                "Error",
                "Mensagem",
                new ArrayList<>());
        ErrorResponse errorResponse2 = new ErrorResponse(
                LocalDateTime.now(),
                1,
                "Error",
                "Mensagem",
                new ArrayList<>());
        assertEquals(errorResponse.getError(), errorResponse2.getError());
    }
}