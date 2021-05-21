package com.example.mvc.controller.exception

import com.example.mvc.model.http.UserRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap

@WebMvcTest
@AutoConfigureMockMvc
class ExceptionApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun helloTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/exception/hello")
        ).andExpect (
            MockMvcResultMatchers.status().isOk
        ).andExpect(
            MockMvcResultMatchers.content().string("hello")
        ).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun getTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "dowon")
        queryParams.add("age", "26")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/exception").queryParams(queryParams)
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        ).andExpect(
            MockMvcResultMatchers.content().string("dowon, 26")
        ).andDo(
            MockMvcResultHandlers.print()
        )
    }

    @Test
    fun getFailTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "dowon")
        queryParams.add("age", "9")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/exception").queryParams(queryParams)
        ).andExpect(
            MockMvcResultMatchers.status().isBadRequest
        ).andExpect(
            MockMvcResultMatchers.content().contentType("application/json")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.result_code").value("FAIL")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.errors[0].field").value("age")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.errors[0].value").value("9")
        ).andDo(
            MockMvcResultHandlers.print()
        )
    }

    @Test
    fun postTest() {
        val userRequest = UserRequest().apply {
            this.name = "dowon"
            this.age = 10
            this.phoneNumber = "010-1111-2222"
            this.address = "부산광역시 부산대학로"
            this.email = "dowon@naver.com"
            this.createdAt = "2020-10-05 13:00:00"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/exception")
                .content(json)
                .contentType("application/json")
                .accept("application/json")
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.name").value("dowon")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.age").value("10")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.phone_number").value("010-1111-2222")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.address").value("부산광역시 부산대학로")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.email").value("dowon@naver.com")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.created_at").value("2020-10-05 13:00:00")
        ).andDo(
            MockMvcResultHandlers.print()
        )
    }

    @Test
    fun postFailTest() {
        val userRequest = UserRequest().apply {
            this.name = "dowon"
            this.age = -1
            this.phoneNumber = "010-1111-aaaa"
            this.address = "부산광역시 부산대학로"
            this.email = "dowon@naver.com"
            this.createdAt = "2020-10-05 13:00:00"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/exception")
                .content(json)
                .contentType("application/json")
                .accept("application/json")
        ).andExpect(
            MockMvcResultMatchers.status().isBadRequest
        ).andDo(
            MockMvcResultHandlers.print()
        )
    }
}
