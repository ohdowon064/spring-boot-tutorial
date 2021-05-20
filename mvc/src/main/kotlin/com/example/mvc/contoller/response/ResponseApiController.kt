package com.example.mvc.contoller.response

import com.example.mvc.model.http.UserRequest
import com.example.mvc.model.http.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/response")
class ResponseApiController {

    // 1. get 4xx
    // GET localhost:8080/api/response
    @GetMapping
    fun getMapping(@RequestParam age: Int?): ResponseEntity<String> { // @RequestParam(required=false)

        return age?.let { // age 있을 때
            if(it < 20) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값은 20보다 커야합니다.")
            }
            ResponseEntity.ok("OK")

        }?: kotlin.run { // age 없을 때
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값이 누락되었습니다.")
        }


        /* // 1. age == null -> 400 error
        if(age == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값이 누락되었습니다.")
        }

        if(age < 20) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값은 20보다 커야합니다.")
        }

        // 2. age > 20 -> 400 error

        return ResponseEntity.ok("OK")*/
    }


    // 2. post 200
    @PostMapping
    fun postMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<UserRequest> {
        return ResponseEntity.status(200).body(userRequest)

    }

    // 3. put 201
    @PutMapping
    fun putMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<UserRequest> {
        // 1. 기존 데이터가 없어서 새로 생성했다.
        return ResponseEntity.status(HttpStatus.CREATED).body(userRequest)
    }

    // 4. delete 500
    @DeleteMapping("/id/{id}")
    fun deleteMapping(@PathVariable id:Int): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 처리 중 에러가 발생했습니다.")
    }

}