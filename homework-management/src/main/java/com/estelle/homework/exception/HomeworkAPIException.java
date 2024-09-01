package com.estelle.homework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public class HomeworkAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;
}
