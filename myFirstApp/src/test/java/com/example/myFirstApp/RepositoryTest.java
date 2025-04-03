package com.example.myFirstApp;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;

@SpringBootTest
public class RepositoryTest {
    
    @Autowired
    private EntityManager entityManager;
    
    @Test
    public void testEntityManager() {
        assertNotNull(entityManager);
        System.out.println("EntityManager is working!");
    }
}	