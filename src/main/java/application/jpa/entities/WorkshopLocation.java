package application.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class WorkshopLocation {

    @Id
    private Integer workshopLocationId;
    private String workshopLocationFullAddress;

    @OneToMany(mappedBy = "fkWorkshopLocationId")
    private List<Workshop> workshops;

    public WorkshopLocation() {
    }

    public WorkshopLocation(String workshopLocationFullAddress) {
        this.workshopLocationFullAddress = workshopLocationFullAddress;
    }

    public WorkshopLocation(Integer workshopLocationId, String workshopLocationFullAddress,
                            List<Workshop> workshops) {
        this.workshopLocationId = workshopLocationId;
        this.workshopLocationFullAddress = workshopLocationFullAddress;
        this.workshops = workshops;
    }

    public Integer getWorkshopLocationId() {
        return workshopLocationId;
    }

    public void setWorkshopLocationId(Integer workshopLocationId) {
        this.workshopLocationId = workshopLocationId;
    }

    public String getWorkshopLocationFullAddress() {
        return workshopLocationFullAddress;
    }

    public void setWorkshopLocationFullAddress(String workshopLocationFullAddress) {
        this.workshopLocationFullAddress = workshopLocationFullAddress;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }
}
