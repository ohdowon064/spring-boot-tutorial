package com.example.mvc.contoller.delete

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@RestController
@RequestMapping("/api")
@Validated // 하위 bean validation 어노테이션이 동작하게 된다.
class DeleteApiController {

    @DeleteMapping(path=["/delete-mapping"])
    fun deleteMapping(
        @RequestParam(value="name") _name : String,

        @NotNull(message = "age 값이 누락되었습니다.")
        @Min(value=20, message="age 값은 20보다 커야합니다.")
        @RequestParam(name="age") _age : Int // 이것들은 bean이 아니기 때문에 @Validated를 적어주어야한다.
    ): String {
        println("${_name}, ${_age}")
        return "${_name} ${_age}"
    }


    @DeleteMapping(path=["/delete-mapping/name/{name}/age/{age}"])
    fun deleteMappingPath(
        @NotNull(message="name 값이 누락되었습니다.")
        @Size(min=2, max=5, message="name 길이는 2이상 5이하입니다.")
        @PathVariable(value="name") _name: String,

        @NotNull(message="age 값이 누락되었습니다.")
        @Min(value=20, message="age 값은 20보다 커야합니다.")
        @PathVariable(name="age") _age: Int
    ): String {
        println("${_name}, ${_age}")
        return "${_name} ${_age}"
    }

}