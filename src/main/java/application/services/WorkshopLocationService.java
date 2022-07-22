package application.services;

import application.jpa.entities.WorkshopLocation;
import application.jpa.repositories.WorkshopLocationRepository;
import application.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WorkshopLocationService {
    private static final String WORKSHOP_LOCATION_NOT_FOUND_ERROR_MESSAGE = "Workshop location with this ID was not found.";

    private final WorkshopLocationRepository workshopLocationRepository;

    @Autowired
    public WorkshopLocationService(WorkshopLocationRepository workshopLocationRepository) {
        this.workshopLocationRepository = workshopLocationRepository;
    }

    public List<WorkshopLocation> findAll() {
        return workshopLocationRepository.findAll();
    }

    public WorkshopLocation findOne(Integer id) {
        Optional<WorkshopLocation> workshopLocationOptional = workshopLocationRepository.findById(id);
        return workshopLocationOptional.orElseThrow(() -> new EntityNotFoundException(WORKSHOP_LOCATION_NOT_FOUND_ERROR_MESSAGE));
    }

    public Optional<WorkshopLocation> findByWorkshopLocationFullAddress(String name) {
        return workshopLocationRepository.findWorkshopLocationByWorkshopLocationFullAddress(name);
    }

    public Optional<WorkshopLocation> findOptionalById(Integer id) {
        return workshopLocationRepository.findById(id);
    }

    public WorkshopLocation save(WorkshopLocation workshopLocation) {
        workshopLocationRepository.save(workshopLocation);
        return workshopLocation;
    }

    public void update(int id, WorkshopLocation workshopLocation) {
        workshopLocation.setWorkshopLocationId(findOne(id).getWorkshopLocationId());
        workshopLocationRepository.save(workshopLocation);
    }

    public void delete(int id) {
        workshopLocationRepository.deleteById(id);
    }
}
