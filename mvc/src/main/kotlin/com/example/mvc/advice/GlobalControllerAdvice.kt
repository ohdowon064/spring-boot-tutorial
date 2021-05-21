package com.example.mvc.advice

import com.example.mvc.contoller.exception.ExceptionApiController
import com.example.mvc.contoller.put.PutApiController
import com.example.mvc.model.http.Error
import com.example.mvc.model.http.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.IndexOutOfBoundsException
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

// @RestControllerAdvice // RestController 에서 발생하는 Exceptino들이 이 RestControllerAdvice를 거치게 된다.
@RestControllerAdvice(basePackageClasses = [ExceptionApiController::class])
// @ControllerAdvice // Controller에서 발생하는 예외를 잡아내는 어노테이션
class GlobalControllerAdvice {

//    @ExceptionHandler(value=[RuntimeException::class])
//    fun exception(e: RuntimeException): String {
//        return "Server Error" // 지정하지 않은 것을 어디선가 처리할 수 있으므로 Advice를 할 때는 반드시 명시를 해주어야한다.
//    }
    @ExceptionHandler(value=[MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<Error>()

        e.bindingResult.allErrors.forEach { errorObject ->
            val error = Error().apply {
                this.field = (errorObject as FieldError).field
                this.message = errorObject.defaultMessage
                this.value = errorObject.rejectedValue
            }

            errors.add(error)
        }

        var errorResponse = ErrorResponse().apply {
            this.resultCode = "FAIL"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요청에 에러가 발생하였습니다."
            this.path = request.requestURI
            this.timestamp = LocalDateTime.now()
            this.errors = errors
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(value=[IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e: IndexOutOfBoundsException): ResponseEntity<String> { // String -> 200 OK
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Error") // 원래 이런 에러는 보여주면 안됨
    }
}