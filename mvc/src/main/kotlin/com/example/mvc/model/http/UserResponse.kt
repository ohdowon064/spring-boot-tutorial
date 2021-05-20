package com.example.mvc.model.http

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

data class UserResponse (
    var result:Result?=null,
    var description:String?=null,
    var user:MutableList<UserRequest>?=null
)


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Result (
    var resultCode:String?=null,
    var resultMassage:String?=null
)