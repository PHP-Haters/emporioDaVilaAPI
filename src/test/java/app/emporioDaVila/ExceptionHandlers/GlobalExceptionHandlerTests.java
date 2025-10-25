package app.emporioDaVila.ExceptionHandlers;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTests {

    @DisplayName("Testando NotFound handler")
    @Test
    void handleUsuarioNotFound_cenario01() {
        // Arrange
        String mensagem = "not found";
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        GenericExceptions.NotFound notFound = new GenericExceptions.NotFound(mensagem);

        // Act
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleUsuarioNotFound(notFound);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), responseEntity.getBody().getError());
        assertEquals(mensagem, responseEntity.getBody().getMessage());
    }

    @DisplayName("Testando AlreadyExists handler")
    @Test
    void handleUsuarioAlreadyExists_cenario01() {
        // Arrange
        String mensagem = "Already Exists";
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        GenericExceptions.AlreadyExists alreadyExists = new GenericExceptions.AlreadyExists(mensagem);

        // Act
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleUsuarioAlreadyExists(alreadyExists);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.CONFLICT.value(), responseEntity.getBody().getStatus());
        assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), responseEntity.getBody().getError());
        assertEquals(mensagem, responseEntity.getBody().getMessage());
    }

    @DisplayName("Testando InvalidData handler")
    @Test
    void handleUsuarioInvalidData_cenario01() {
        // Arrange
        String mensagem = "Invalid data";
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        GenericExceptions.InvalidData invalidData = new GenericExceptions.InvalidData(mensagem);

        // Act
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleUsuarioInvalidData(invalidData);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getBody().getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), responseEntity.getBody().getError());
        assertEquals(mensagem, responseEntity.getBody().getMessage());
    }

    @DisplayName("Testando Unathorized handler")
    @Test
    void handleUsuarioUnathorized_cenario01() {
        // Arrange
        String mensagem = "Unathorized";
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        GenericExceptions.Unauthorized unauthorized = new GenericExceptions.Unauthorized(mensagem);

        // Act
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleUsuarioUnauthorized(unauthorized);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getBody().getStatus());
        assertEquals(HttpStatus.UNAUTHORIZED.getReasonPhrase(), responseEntity.getBody().getError());
        assertEquals(mensagem, responseEntity.getBody().getMessage());
    }

    @DisplayName("Testando General handler")
    @Test
    void handleUsuarioGeneral_cenario01() {
        // Arrange
        String mensagem = "General";
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        GenericExceptions.General general = new GenericExceptions.General(mensagem);

        // Act
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleUsuarioGeneral(general);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), responseEntity.getBody().getError());
        assertEquals(mensagem, responseEntity.getBody().getMessage());
    }

    @DisplayName("Testando Validations handler")
    @Test
    void handleValidationExceptions_cenario01() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Simulate a simple invalid object
        Object targetObject = new Object();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(targetObject, "target");

        // Add fake validation errors
        bindingResult.addError(new FieldError("target", "username", "Username is required"));
        bindingResult.addError(new FieldError("target", "email", "Email must be valid"));

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<ErrorResponse> response = handler.handleValidationExceptions(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("Validation failed", response.getBody().getError());
        assertEquals("Dados inválidos", response.getBody().getMessage());

        List<String> errors = response.getBody().getValidationErrors();
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertTrue(errors.contains("Username is required"));
        assertTrue(errors.contains("Email must be valid"));
    }


    enum TestEnum { A, B, C }
    @DisplayName("Testando caso não seja um enum")
    @Test
    void handleEnum_cenario01() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Simulate an invalid enum conversion ("Z" is not a valid TestEnum)
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "Z",                    // the invalid value
                TestEnum.class,         // required type (enum)
                "paramName",            // parameter name
                null,                   // method parameter (null is fine)
                new IllegalArgumentException("Cannot convert")
        );

        // Act
        ResponseEntity<ErrorResponse> response = handler.handleEnumConversionError(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("Invalid Enum Value", response.getBody().getError());

        String expectedMessage = "O valor 'Z' não é uma TestEnum";
        assertEquals(expectedMessage, response.getBody().getMessage());
    }

    @DisplayName("Testando Caso seja um valor inválido Enum")
    @Test
    void handleEnum_cenario02() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "123",
                Integer.class,      // not an enum type
                "param",
                null,
                new IllegalArgumentException("Cannot convert")
        );

        // Act
        ResponseEntity<ErrorResponse> response = handler.handleEnumConversionError(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody()); // fallback returns empty ResponseEntity
    }
}


