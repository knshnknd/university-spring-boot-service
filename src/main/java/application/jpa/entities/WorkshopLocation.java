package application.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public WorkshopLocation(String workshopLocationFullAddress) {
        this.workshopLocationFullAddress = workshopLocationFullAddress;
    }

    public WorkshopLocation(Integer workshopLocationId, String workshopLocationFullAddress) {
        this.workshopLocationId = workshopLocationId;
        this.workshopLocationFullAddress = workshopLocationFullAddress;
    }
}
