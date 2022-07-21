package application.jpa.repositories;

import application.jpa.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findStudentByStudentFullName(String fullName);
    void deleteStudentByStudentId(Integer id);
}
