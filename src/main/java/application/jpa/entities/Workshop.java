package application.jpa.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workshopId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "workshop_name", referencedColumnName = "subjectName")
    private Subject subjectName;

    @ManyToOne
    @JoinColumn(referencedColumnName = "workshopLocationId")
    private WorkshopLocation workshopLocationId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "teacherId")
    private Teacher teacherId;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date workshopDate;

    @ManyToMany(mappedBy = "workshops")
    private List<Student> students;

    public Workshop() {
    }

    public Workshop(Integer workshopId, Subject subjectName, WorkshopLocation workshopLocationId,
                    Teacher teacherId, Date workshopDate, List<Student> students) {
        this.workshopId = workshopId;
        this.subjectName = subjectName;
        this.workshopLocationId = workshopLocationId;
        this.teacherId = teacherId;
        this.workshopDate = workshopDate;
        this.students = students;
    }

    public Integer getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public WorkshopLocation getWorkshopLocationId() {
        return workshopLocationId;
    }

    public void setWorkshopLocationId(WorkshopLocation fkWorkshopLocationId) {
        this.workshopLocationId = fkWorkshopLocationId;
    }

    public Teacher getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Teacher fkTeacherId) {
        this.teacherId = fkTeacherId;
    }

    public Date getWorkshopDate() {
        return workshopDate;
    }

    public void setWorkshopDate(Date workshopDate) {
        this.workshopDate = workshopDate;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Subject getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(Subject subjectName) {
        this.subjectName = subjectName;
    }
}
