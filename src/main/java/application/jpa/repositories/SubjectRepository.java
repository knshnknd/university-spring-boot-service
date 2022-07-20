package application.jpa.repositories;

import application.jpa.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Optional<Subject> findStudentGroupBySubjectId (Integer id);
    void deleteStudentGroupBySubjectId (Integer id);
}
