package br.com.william.planner.model;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private int status;
    private String message;

    public static ErrorResponse invalidArgumentsError(FieldError fieldError){
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())//Só pega o código do erro
                .message(fieldError.getDefaultMessage())
                .build();
    }

    public static ErrorResponse internalError(RuntimeException ex){

        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
    }
}
