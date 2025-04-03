package com.example.myFirstApp.controller;
import com.example.myFirstApp.model.Student;
import com.example.myFirstApp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/students")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@PostMapping
	public ResponseEntity<?> createStudent(@RequestBody Student student){
		try {
			Student createdStudent = studentService.createStudent(student);
            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED); 
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public  List<Student> getAllStudents(){
		return studentService.getAll();
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody Student student){
		try {
			Student updatedStudent = studentService.updateStudent(id, student);
			return new ResponseEntity<>(updatedStudent, HttpStatus.CREATED);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable String id) {
		return studentService.getStudentById(id).map(student -> ResponseEntity.ok(student)).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable String id){
		try {
			studentService.deleteStudent(id);
			return new ResponseEntity<>("Student deleted successfully",HttpStatus.OK);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
}
