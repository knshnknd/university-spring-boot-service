package application.jpa.repositories;

import application.jpa.entities.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, Integer> {
    Optional<Workshop> findWorkshopByWorkshopId(Integer id);
    void deleteLectureByWorkshopId(Integer id);
}
