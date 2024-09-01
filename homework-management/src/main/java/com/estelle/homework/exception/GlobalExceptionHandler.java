package com.estelle.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice // 전역적인 예외 처리를 담당하는 클래스임을 선언
public class GlobalExceptionHandler {

    // HomeworkAPIException 예외를 처리하는 메서드
    @ExceptionHandler(HomeworkAPIException.class)
    public ResponseEntity<ErrorDetails> handleTodoAPIException(HomeworkAPIException exception,
                                                               WebRequest webRequest){

        // 예외 발생 시 반환할 오류 세부 정보를 생성
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), // 오류 발생 시간
                exception.getMessage(), // 예외 메시지
                webRequest.getDescription(false) // 요청의 세부 정보 (쿼리 파라미터 제외)
        );

        // ErrorDetails 객체와 함께 BAD_REQUEST(400) 상태 코드를 반환
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
