package com.example.myFirstApp.repository;
import com.example.myFirstApp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface StudentRepository extends JpaRepository<Student, String> {
	boolean existsByEmail(String email);

	@Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.email = :email AND s.id != :id")
	boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") String id);
}