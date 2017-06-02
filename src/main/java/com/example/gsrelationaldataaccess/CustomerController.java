package com.example.gsrelationaldataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Customer customer(@PathVariable long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, first_name, last_name FROM customers where id=?", new Object[]{id},
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"),
                        rs.getString("last_name"))
        );
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    List<Customer> getCustomers() {
        return jdbcTemplate.query("SELECT id, first_name, last_name FROM customers",
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"),
                        rs.getString("last_name")));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    int deleteCustomer(@PathVariable long id) {
        return jdbcTemplate.update("DELETE FROM customers where id = ?", new Object[]{id});
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody
    int createCustomer(@RequestBody Customer c) {
        return jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES(?,?)",
                new Object[]{c.getFirstName(), c.getLastName()});
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    int updateCustomer(@PathVariable long id, @RequestBody Customer c) {
        return jdbcTemplate.update("UPDATE customers Set first_name=?, last_name= ? where id=?",
                new Object[]{c.getFirstName(), c.getLastName(), id});
    }

}