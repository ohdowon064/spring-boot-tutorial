package com.example.mvc.contoller.page

import com.example.mvc.model.http.UserRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

// @RestController 둘을 같이 쓰면 RestController로 동작한다. // RestController와 Controller는 패키지로 나눠서 관리하는 것이 좋다.
@Controller // 특정 페이지를 리턴할 때 사용하는 어노테이션, // resources/static 디렉토리에서 찾는다.
class PageController {

    @GetMapping("/main")
    fun main(): String {
        println("init main")
        return "main.html"
    }

    @ResponseBody // 페이지를 찾지않고 body를 반환하도록 하는 어노테이션
    @GetMapping("/test")
    fun response(): UserRequest {
        return UserRequest().apply {
            this.name = "dowon"
        }
        // return "main.html"
    }
}