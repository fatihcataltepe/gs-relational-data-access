package com.example.gsrelationaldataaccess

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

import scala.beans.BeanProperty

@JsonCreator
case class Customer(@JsonProperty("id") @BeanProperty id: Long = 0,
                    @JsonProperty("firstName") @BeanProperty firstName: String = "",
                    @JsonProperty("lastName") @BeanProperty lastName: String = "")

