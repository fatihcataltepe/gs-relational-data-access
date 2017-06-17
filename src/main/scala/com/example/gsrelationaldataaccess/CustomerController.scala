package com.example.gsrelationaldataaccess


import java.sql.ResultSet

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.{JdbcTemplate, RowMapper}
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/customers"))
class CustomerController {

  @Autowired
  val jdbcTemplate: JdbcTemplate = null

  @RequestMapping(value = Array(""))
  def getCustomers = {
    jdbcTemplate.queryForList("SELECT id, first_name, last_name FROM customers")
  }

  @GetMapping(value = Array("/{id}"))
  def getCustomer(@PathVariable id: AnyRef): Customer = {
    jdbcTemplate.queryForObject("SELECT id, first_name, last_name FROM customers where id=?", Array(id), CustomerMapper())
  }

  @DeleteMapping(value = Array("/{id}"))
  def deleteCustomer(@PathVariable id: AnyRef): Int = {
    return jdbcTemplate.update("DELETE FROM customers where id = ?", id)
  }

  @PostMapping
  def createCustomer(@RequestBody customer: Customer): Int = {
    return jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES(?,?)", customer.firstName, customer.lastName)
  }

  @PutMapping(value = Array("/{id}"))
  def updaetCustomer(@PathVariable id: AnyRef, @RequestBody customer: Customer): Int = {
    return jdbcTemplate.update("UPDATE customers Set first_name=?, last_name=? where id=?", customer.firstName, customer.lastName, id)
  }

}

case class CustomerMapper() extends RowMapper[Customer] {
  override def mapRow(rs: ResultSet, rowNum: Int): Customer = Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
}
