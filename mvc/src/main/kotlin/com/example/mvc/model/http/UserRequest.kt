package com.example.mvc.model.http

import com.example.mvc.annotation.StringFormatDateTime
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.*

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class UserRequest(

		@field:NotEmpty
		@field:Size(min=2, max=8)
		var name:String?=null,

		@field:PositiveOrZero // 0 이상
		var age:Int?=null,

		@field:Email
		var email:String?=null,

		@field:NotBlank // 공백 검증
		var address:String?=null,

		@field:Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}\$") // 정규식으로 전화번호 검증
		var phoneNumber:String?=null,

        @field:StringFormatDateTime(pattern="yyyy-MM-dd HH:mm:ss")
		var createdAt:String?=null // yyyy-MM-dd HH:mm:ss ex) 2020-10-02 13:00:00
)
//	){
//
//		@AssertTrue(message="createdAt 패턴은 yyyy-MM-dd HH:mm:ss 입니다.") // 검증과정에서 실행되도록 하는 어노테이션
//		private fun isValidCreatedAt(): Boolean { // 메서드이므로 @field를 붙이지 않는다.
//                return try {
//                        LocalDateTime.parse(this.createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//                        true
//                }catch (e:Exception) {
//                        false
//                }
//
//		}
//}