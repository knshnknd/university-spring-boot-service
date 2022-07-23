package application.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(scope = WorkshopLocation.class,
        generator = ObjectIdGenerators.PropertyGenerator.class, property = "workshopLocationId")
public class WorkshopLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workshopLocationId;
    private String workshopLocationFullAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "workshopLocation")
    private List<Workshop> workshops;

    public WorkshopLocation() {
    }

    public WorkshopLocation(String workshopLocationFullAddress) {
        this.workshopLocationFullAddress = workshopLocationFullAddress;
    }

    public WorkshopLocation(Integer workshopLocationId, String workshopLocationFullAddress) {
        this.workshopLocationId = workshopLocationId;
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
