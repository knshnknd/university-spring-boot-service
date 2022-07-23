package application.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = Workshop.class,
        generator = ObjectIdGenerators.PropertyGenerator.class, property = "workshopId")
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workshopId;

    @NotNull
    @ManyToOne
    @JoinColumn(referencedColumnName = "subjectId")
    private Subject subject;

    @ManyToOne
    @JoinColumn(referencedColumnName = "workshopLocationId")
    private WorkshopLocation workshopLocation;

    @ManyToOne
    @JoinColumn(referencedColumnName = "teacherId")
    private Teacher teacher;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workshopDate;

    @JsonIgnore
    @ManyToMany(mappedBy = "workshops")
    private List<Student> students = new ArrayList<>();

    public Workshop(Long workshopId) {
        this.workshopId = workshopId;
    }

    public Workshop(Subject subject, WorkshopLocation workshopLocation, Teacher teacher,
                    Date workshopDate, List<Student> students) {
        this.subject = subject;
        this.workshopLocation = workshopLocation;
        this.teacher = teacher;
        this.workshopDate = workshopDate;
        this.students = students;
    }
}
