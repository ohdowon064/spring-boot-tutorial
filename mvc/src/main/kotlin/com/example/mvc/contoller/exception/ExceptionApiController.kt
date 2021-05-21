package com.example.mvc.contoller.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IndexOutOfBoundsException

@RestController
@RequestMapping("/api/exception")
class ExceptionApiController {

    @GetMapping("/hello")
    fun hello() {
        val list = mutableListOf<String>()
        val tmp = list[0]
    }

    @ExceptionHandler(value=[IndexOutOfBoundsException::class]) // Controller 내부에 있으면 @RestControllerAdvice를 거치지않는다.
    fun indexOutOfBoundsException(e: IndexOutOfBoundsException): ResponseEntity<String> { // String -> 200 OK
        println("Controller Exception Handler")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Error") // 원래 이런 에러는 보여주면 안됨
    }
}