//package com.example.demo.exception;
//
//
//import com.example.demo.domain.RestResponse;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<RestResponse<Object>> handleGenericException(Exception ex) {
//        RestResponse<Object> response = new RestResponse<Object>();
//        return  ResponseEntity.badRequest().body(response);
//    }
//}
