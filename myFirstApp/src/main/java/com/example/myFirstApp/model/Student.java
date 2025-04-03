package com.example.myFirstApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
@Entity
public class Student {
	
	@Id
	private String id;
	private String name;
	private String standard;
	private String section;
	private String email;
	private long number;
	
	public Student(){
	}
	
	public Student(String name, String email, String standard, long number, String section){
		this.name = name;
		this.email = email;
		this.number = number;
		this.standard = standard;
		this.section = section;
	}
	
	@PrePersist
	public void generateId() {
		if(id==null) {
			id= RandomIdGenerator.generateRandomId();
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}
}
