package application.dto.responses;

import application.dto.requests.WorkshopRequestDto;

import java.util.List;

public class WorkshopResponseDto {
    private List<WorkshopRequestDto> workshops;

    public WorkshopResponseDto(List<WorkshopRequestDto> workshops) {
        this.workshops = workshops;
    }

    public List<WorkshopRequestDto> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<WorkshopRequestDto> workshops) {
        this.workshops = workshops;
    }
}
