package com.example.mvc.contoller.put

import com.example.mvc.model.http.Result
import com.example.mvc.model.http.UserRequest
import com.example.mvc.model.http.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class PutApiController {

    @PutMapping("/put-mapping")
    fun putMapping() : String{
        return "put-mapping"
    }

    @RequestMapping(method=[RequestMethod.PUT], path=["/request-mapping"])
    fun requestMapping(): String {
        return "request-mapping PUT Method"
    }

    @PutMapping(path=["/put-mapping/object"])
    fun putMappingObject(@Valid @RequestBody userRequest: UserRequest, bindingResult: BindingResult): ResponseEntity<String> {
        if(bindingResult.hasErrors()) { // 에러가 있는 경우
            // 500 error
            val msg = StringBuilder()
            bindingResult.allErrors.forEach {
                val field = it as FieldError // FieldError로 형변환
                val message = it.defaultMessage
                msg.append(field.field + " : " + message + "\n")
            }
            return ResponseEntity.badRequest().body(msg.toString())
        }

//        // 1. user response
//        return UserResponse().apply {
//            result = Result().apply {
//                resultCode = "OK"
//                resultMassage = "성공"
//            }
//        }.apply {
//            // 2. description
//            description = "코틀린 스프링부트 REST API 튜토리얼"
//
//            // 3. user list
//            val userList = mutableListOf<UserRequest>()
//            userList.add(userRequest)
//            userList.add(
//                UserRequest().apply {
//                    name = "김길동"
//                    age = 10
//                    email = "ak@gmail.com"
//                    address = "부산광역시 자갈치마을"
//                    phoneNumber = "010-1111-aaaa"
//                }
//            )
//            userList.add(
//                UserRequest().apply {
//                    name = "나길동"
//                    age = 27
//                    email = "bk@gmail.com"
//                    address = "서울특별시 한강마을"
//                    phoneNumber = "010-2222-bbbb"
//                }
//            )
//
//            user = userList
//        }
        return ResponseEntity.ok("")
    }


}