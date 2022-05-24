package com.heroes.controller.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.heroes.core.exception.DataException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    @Setter
    @Getter
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    public static class ExceptionMessage {
        private int code;
        private String message;
        private String status;

        public ExceptionMessage(int code, String message, String status) {
            this.code = code;
            this.message = message;
            this.status = status;
        }
    }

    @ExceptionHandler(DataException.class)
    public ResponseEntity<ExceptionMessage> handleDataException(DataException ex) {
        if (ex.isShowStackTrace()) ex.printStackTrace();
        ExceptionMessage em = new ExceptionMessage(ex.getCode(), ex.getMessage(), ex.getStatus());
        return ResponseEntity.status(HttpStatus.valueOf(ex.getCode())).body(em);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
