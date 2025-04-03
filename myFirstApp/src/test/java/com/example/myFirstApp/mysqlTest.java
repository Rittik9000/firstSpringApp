package com.example.myFirstApp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class mysqlTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testDatabaseConnection() {
        // Try to execute a simple query to check if the database connection is working
        String sql = "SELECT 1";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        
        // Assert that the result is 1, indicating a successful database connection
        assertTrue(result != null && result == 1, "Database connection is not successful!");
        
        System.out.println("Database connection is successful!");
    }
}
