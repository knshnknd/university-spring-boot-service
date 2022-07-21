package application.services;

import application.jpa.entities.Subject;
import application.jpa.entities.WorkshopLocation;
import application.jpa.repositories.WorkshopLocationRepository;
import application.util.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
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

    public Optional<WorkshopLocation> findByName(String name) {
        return workshopLocationRepository.findWorkshopLocationByWorkshopLocationFullAddress(name);
    }

    @Transactional
    public void save(WorkshopLocation workshopLocation) {
        workshopLocationRepository.save(workshopLocation);
    }

    @Transactional
    public void update(int id, WorkshopLocation workshopLocation) {
        workshopLocation.setWorkshopLocationId(findOne(id).getWorkshopLocationId());
        workshopLocationRepository.save(workshopLocation);
    }

    @Transactional
    public void delete(int id) {
        workshopLocationRepository.deleteById(id);
    }
}
