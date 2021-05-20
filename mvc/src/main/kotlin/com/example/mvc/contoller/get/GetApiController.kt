package com.example.mvc.contoller.get

import com.example.mvc.model.http.UserRequest
import org.springframework.web.bind.annotation.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements

@RestController // REST API Controller로 동작할 것이다. return type이 Object이면 JSON으로 바뀌어서 리턴된다.
@RequestMapping("/api") // http://localhost:8080/api
class GetApiController {

    @GetMapping("/hello") // http://localhost:8080/api/hello
    fun hello(): String{
        return "Hello Kotlin"
    }

    @RequestMapping(method=[RequestMethod.GET], path=["request-mapping"]) // 예전 방식
    fun requestMapping(): String{
        return "request-mapping"
    }

    @GetMapping("/get-mapping/path-variable/{name}/{age}") // GET http://localhost:8080/api/get-mapping/path-variable/dowon/25
    fun pathVariable(@PathVariable name: String, @PathVariable age: Int): String {
        println("${name}, ${age}")
        return name + " " + age
    }

    @GetMapping("/get-mapping/path-variable2/{name}/{age}") // GET http://localhost:8080/api/get-mapping2/path-variable/dowon/25
    fun pathVariable2(@PathVariable(value="name") _name: String, @PathVariable(name="age") age: Int): String {
        val name = "Kotlin"

        println("${_name}, ${age}")
        return _name + " " + age
    }

    // Query Parameter
    // http://localhost:8080/api/page?key=value&key2=value&key3=value
    @GetMapping("get-mapping/query-param") // ?name=dowon&age=25
    fun queryParam(
        @RequestParam(name="name") name: String, // name과 value는 동일
        @RequestParam(value="age") age: Int
    ): String {
        println("${name}, ${age}")
        return name+ " " + age
    }

    // Query parameter 객체 맵핑
    // name, age, address, email -> request param을 4개? -> 너무 비효율적
    // phoneNumber -> phonenumber, phone-number -> 이런 경우는 파싱 불가, value, name속성으로 phone-number 지정해야한다.
    // 따라서 주의해서 변수명 설정
    @GetMapping("/get-mapping/query-param/object")
    fun queryParamObject(userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

    // Map과 Object 차이점
    // phoneNumber 받을 수 있는가?
    @GetMapping("/get-mapping/query-param/map")
    fun queryParamMap(@RequestParam map: Map<String, Any>): Map<String, Any> {
        println(map)
        val phoneNumber = map.get("phone-number")
        return map
    }

    @GetMapping("/lotto")
    fun lottoData(): List<Map<String, String>> {
        val url = "https://dhlottery.co.kr/gameResult.do?method=byWin"
        val doc = Jsoup.connect(url).timeout(1000 * 10).get()  //타임아웃 10초
        val contentData : Elements = doc.select("table tbody tr")

        val winnerList = mutableListOf<Map<String, String>>()

        for(data in contentData) {
            val element = data.select("td")
            val lank = element[0].text()
            val money = element[2].text()
            val person = element[3].text()
            val winner = mutableMapOf("lank" to lank, "money" to money, "person" to person)
            winnerList.add(winner)
        }

        return winnerList
    }

}