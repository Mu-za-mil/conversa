package com.conversa.userservice.controller;
import com.conversa.userservice.service.*; import jakarta.servlet.http.HttpServletRequest; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.util.Map;
@RestControllerAdvice public class GlobalExceptionHandler {
 @ExceptionHandler(ConflictException.class) ResponseEntity<?> conflict(ConflictException e){return body(HttpStatus.CONFLICT,e.getMessage());}
 @ExceptionHandler(UnauthorizedException.class) ResponseEntity<?> unauthorized(UnauthorizedException e){return body(HttpStatus.UNAUTHORIZED,e.getMessage());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> validation(MethodArgumentNotValidException e){String msg=e.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getField()+": "+x.getDefaultMessage()).orElse("Invalid request");return body(HttpStatus.BAD_REQUEST,msg);}
 private ResponseEntity<?> body(HttpStatus status,String message){return ResponseEntity.status(status).body(Map.of("timestamp",Instant.now(),"status",status.value(),"error",status.getReasonPhrase(),"message",message));}
}