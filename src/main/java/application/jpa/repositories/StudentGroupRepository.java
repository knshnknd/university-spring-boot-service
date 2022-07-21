package application.jpa.repositories;

import application.jpa.entities.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Integer> {
    Optional<StudentGroup> findStudentGroupByStudentGroupName(String name);
    void deleteStudentGroupByStudentGroupId(Integer id);
}
