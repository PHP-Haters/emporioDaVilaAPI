package app.emporioDaVila.ExceptionHandlers;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GenericExceptionsTests {
    @Test
    //GenericExceptions.AlreadyExists test1 = new GenericExceptions.AlreadyExists("cenario01");
    void AlreadyExists_cenario01() {
        assertThrows(Exception.class, () -> {
            throw  new GenericExceptions.AlreadyExists("Puta que pariu");
        });
    }
    @Test
    void GenericExceptions_cenario01() {
        assertThrows(Exception.class, () -> {
            GenericExceptions test  = new GenericExceptions();
            throw  new  GenericExceptions.General("Puta que pariu");
        });
    }
}
