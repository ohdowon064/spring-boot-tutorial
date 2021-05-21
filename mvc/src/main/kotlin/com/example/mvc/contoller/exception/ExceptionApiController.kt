package com.example.mvc.contoller.exception

import com.example.mvc.model.http.Error
import com.example.mvc.model.http.ErrorResponse
import com.example.mvc.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.lang.IndexOutOfBoundsException
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@RestController
@RequestMapping("/api/exception")
@Validated
class ExceptionApiController {

    @GetMapping("/hello")
    fun hello(): String {
        val list = mutableListOf<String>()
         // val tmp = list[0]
        return "hello"
    }

    @GetMapping("")
    fun get(
        @NotBlank
        @Size(min=2, max=6)
        @RequestParam name:String,

        @Min(10)
        @RequestParam age: Int
    ): String {
        println("$name, $age")
        return "$name, $age"
    }

    @PostMapping("")
    fun post(@Valid @RequestBody userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

    @PutMapping("")
    fun put(@Valid @RequestBody userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

//    @ExceptionHandler(value=[MethodArgumentNotValidException::class])
//    fun methodArgumentNotValidException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
//        val errors = mutableListOf<Error>()
//
//        e.bindingResult.allErrors.forEach { errorObject ->
//            val error = Error().apply {
//                this.field = (errorObject as FieldError).field
//                this.message = errorObject.defaultMessage
//                this.value = errorObject.rejectedValue
//            }
//
//            errors.add(error)
//        }
//
//        var errorResponse = ErrorResponse().apply {
//            this.resultCode = "FAIL"
//            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
//            this.httpMethod = request.method
//            this.message = "요청에 에러가 발생하였습니다."
//            this.path = request.requestURI
//            this.timestamp = LocalDateTime.now()
//            this.errors = errors
//        }
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
//    }

    @ExceptionHandler(value=[ConstraintViolationException::class])
    fun constraintViolationException(e: ConstraintViolationException, request: HttpServletRequest):ResponseEntity<ErrorResponse> {
        // 1. 에러 분석
        val errors = mutableListOf<Error>()

        e.constraintViolations.forEach {
            val error = Error().apply {
                this.field = it.propertyPath.last().name
                this.message = it.message
                this.value = it.invalidValue
            }
            errors.add(error)
        }


        // 2. Error Response 응답
        var errorResponse = ErrorResponse().apply {
            this.resultCode = "FAIL"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요청에 에러가 발생하였습니다."
            this.path = request.requestURI
            this.timestamp = LocalDateTime.now()
            this.errors = errors
        }

        // 3. ResponseEntity
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(value=[IndexOutOfBoundsException::class]) // Controller 내부에 있으면 @RestControllerAdvice를 거치지않는다.
    fun indexOutOfBoundsException(e: IndexOutOfBoundsException): ResponseEntity<String> { // String -> 200 OK
        println("Controller Exception Handler")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Error") // 원래 이런 에러는 보여주면 안됨
    }
}








