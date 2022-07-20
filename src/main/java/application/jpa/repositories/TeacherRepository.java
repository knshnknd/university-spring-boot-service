package application.jpa.repositories;

import application.jpa.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findTeacherByTeacherId(Integer id);
    Optional<Teacher> findTeacherByTeacherFullName(String name);
    void deleteTeacherByTeacherId(Integer id);
}
