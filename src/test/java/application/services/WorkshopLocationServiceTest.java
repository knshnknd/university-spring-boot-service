package application.services;

import application.jpa.entities.WorkshopLocation;
import application.jpa.repositories.WorkshopLocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class WorkshopLocationServiceTest {

    @MockBean
    private WorkshopLocationRepository workshopLocationRepository;
    @Autowired
    private WorkshopLocationService workshopLocationService;

    @Test
    void findAllTest() {
        List<WorkshopLocation> workshopLocationList = new ArrayList<>(List.of(
                new WorkshopLocation("Main building, auditorium 22")));

        given(workshopLocationRepository.findAll()).willReturn(workshopLocationList);
        List<WorkshopLocation> workshopLocations = workshopLocationService.findAll();

        assertThat(workshopLocationList).isEqualTo(workshopLocations);
        assertThat(workshopLocations.size()).isEqualTo(1);

        verify(workshopLocationRepository).findAll();
    }

    @Test
    void findOneTest() {
        WorkshopLocation workshopLocation = new WorkshopLocation(1,"Main building, auditorium 22");

        given(workshopLocationRepository.findById(1))
                .willReturn(Optional.of(new WorkshopLocation(1,"Main building, auditorium 22")));

        workshopLocationService.findOne(1);
        assertThat(workshopLocation.getWorkshopLocationId()).isEqualTo(1);
    }

    @Test
    void findBySubjectNameTest() {
        given(workshopLocationRepository.findWorkshopLocationByWorkshopLocationFullAddress("Main building, auditorium 22"))
                .willReturn(Optional.of(new WorkshopLocation(1,"Main building, auditorium 22")));
        WorkshopLocation workshopLocation = workshopLocationService.findByWorkshopLocationFullAddress("Main building, auditorium 22").get();
        assertThat(workshopLocation.getWorkshopLocationFullAddress()).isEqualTo("Main building, auditorium 22");
    }

    @Test
    void findOptionalByIdTest() {
        given(workshopLocationRepository.findById(1))
                .willReturn(Optional.of(new WorkshopLocation(1,"Main building, auditorium 22")));
        Optional<WorkshopLocation> workshopLocation = workshopLocationService.findOptionalById(1);
        assertThat(workshopLocation.get().getWorkshopLocationId()).isEqualTo(1);
    }

    @Test
    void saveTest() {
        WorkshopLocation workshopLocation = new WorkshopLocation(1,"Main building, auditorium 22");
        when(workshopLocationRepository.save(any(WorkshopLocation.class))).thenReturn(
                new WorkshopLocation(1,"Main building, auditorium 22"));

        WorkshopLocation created = workshopLocationService.save(workshopLocation);
        assertThat(created.getWorkshopLocationId()).isSameAs(workshopLocation.getWorkshopLocationId());
    }

    @Test
    void updateTest() {
        WorkshopLocation workshopLocation = new WorkshopLocation(1,"Main building, auditorium 22");

        WorkshopLocation newWorkshopLocation = new WorkshopLocation(1,"Campus, Laboratory 33");

        given(workshopLocationRepository.findById(workshopLocation.getWorkshopLocationId()))
                .willReturn(Optional.of(workshopLocation));

        workshopLocationService.update(workshopLocation.getWorkshopLocationId(), newWorkshopLocation);

        verify(workshopLocationRepository).save(newWorkshopLocation);
        verify(workshopLocationRepository).findById(workshopLocation.getWorkshopLocationId());
    }

    @Test
    void deleteTest() {
        WorkshopLocation workshopLocation = new WorkshopLocation(1,"Main building, auditorium 22");

        when(workshopLocationRepository.findById(workshopLocation.getWorkshopLocationId()))
                .thenReturn(Optional.of(workshopLocation));

        workshopLocationService.delete(workshopLocation.getWorkshopLocationId());

        verify(workshopLocationRepository).deleteById(workshopLocation.getWorkshopLocationId());
    }
}