package com.gxecard.customerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbDemo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbDemo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void doIt() {
        // jdbcTemplate.
        // jdbcTemplate.
    	System.out.println(jdbcTemplate);
    }
}
