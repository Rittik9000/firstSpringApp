package com.example.myFirstApp.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.myFirstApp.model.Student;
import com.example.myFirstApp.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	public List<Student> getAll(){
		return studentRepository.findAll();
	}
	
	public Student createStudent(Student student) {
		if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("Email '" + student.getEmail() + "' is already been registered");
        }
        return studentRepository.save(student);
	}
	
	
	public Optional<Student> getStudentById(String id){
		return studentRepository.findById(id);
	}
	
	
	public Student updateStudent(String id, Student studentDetails){
		Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found"));
		if (studentDetails.getEmail() != null && !studentDetails.getEmail().equals(student.getEmail())) {
			
            if (studentRepository.existsByEmailAndIdNot(studentDetails.getEmail(), id)) {
                throw new IllegalArgumentException("Email '" + studentDetails.getEmail() + "' is already in use!");
            }
            student.setEmail(studentDetails.getEmail());
        }

        // Update name if provided
        if (studentDetails.getName() != null) {
            student.setName(studentDetails.getName());
        }
        
        if(studentDetails.getStandard() != null) {
        	student.setStandard(studentDetails.getStandard());
        }
        if(studentDetails.getNumber()<=1000000000) {
        	student.setNumber(studentDetails.getNumber());
        }
        if(studentDetails.getSection() != null) {
        	student.setSection(studentDetails.getSection());
        }
        return studentRepository.save(student); 
		
	}
	
	public void deleteStudent(String id) {
	studentRepository.deleteById(id);
	}
}
