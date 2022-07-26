package application.jpa.repositories;

import application.jpa.entities.WorkshopLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkshopLocationRepository extends JpaRepository<WorkshopLocation, Integer> {

    Optional<WorkshopLocation> findWorkshopLocationByWorkshopLocationFullAddress(String name);
}
