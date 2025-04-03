package com.example.myFirstApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password; // Add password field
    private String role;     // e.g., "USER" or "ADMIN"

    public User() {}
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @PrePersist
    public void generateId() {
		if(id==null) {
			id= RandomIdGenerator.generateRandomId();
		}
	}
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}