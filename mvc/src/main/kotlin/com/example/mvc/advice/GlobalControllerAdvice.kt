package com.example.mvc.advice

import com.example.mvc.contoller.exception.ExceptionApiController
import com.example.mvc.contoller.put.PutApiController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.IndexOutOfBoundsException

// @RestControllerAdvice // RestController 에서 발생하는 Exceptino들이 이 RestControllerAdvice를 거치게 된다.
@RestControllerAdvice(basePackageClasses = [ExceptionApiController::class])
// @ControllerAdvice // Controller에서 발생하는 예외를 잡아내는 어노테이션
class GlobalControllerAdvice {

    @ExceptionHandler(value=[RuntimeException::class])
    fun exception(e: RuntimeException): String {
        return "Server Error"
    }

    @ExceptionHandler(value=[IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e: IndexOutOfBoundsException): ResponseEntity<String> { // String -> 200 OK
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Error") // 원래 이런 에러는 보여주면 안됨
    }
}