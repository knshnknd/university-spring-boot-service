package application.dto;

import application.jpa.entities.Student;
import application.jpa.entities.Workshop;

import java.util.List;

public class WorkshopResponse {
    private List<WorkshopDTO> workshops;

    public WorkshopResponse(List<WorkshopDTO> workshops) {
        this.workshops = workshops;
    }

    public List<WorkshopDTO> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<WorkshopDTO> workshops) {
        this.workshops = workshops;
    }
}
