package application.dto.responses;

import application.jpa.entities.Subject;
import application.jpa.entities.WorkshopLocation;

import java.util.List;

public class WorkshopLocationResponse {
    private List<WorkshopLocation> workshopLocations;

    public WorkshopLocationResponse(List<WorkshopLocation> workshopLocations) {
        this.workshopLocations = workshopLocations;
    }

    public List<WorkshopLocation> getWorkshopLocations() {
        return workshopLocations;
    }

    public void setWorkshopLocations(List<WorkshopLocation> workshopLocations) {
        this.workshopLocations = workshopLocations;
    }
}
