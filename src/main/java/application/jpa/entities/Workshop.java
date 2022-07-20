package application.jpa.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Workshop {

    @Id
    private Integer workshopId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "subjectId")
    private Subject fkWorkshopId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "workshopLocationId")
    private WorkshopLocation fkWorkshopLocationId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "teacherId")
    private Teacher fkTeacherId;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date workshopDate;

    @ManyToMany(mappedBy = "workshops")
    private List<Student> students;

    public Workshop() {
    }

    public Workshop(Integer workshopId, Subject fkWorkshopId, WorkshopLocation fkWorkshopLocationId,
                    Teacher fkTeacherId, Date workshopDate, List<Student> students) {
        this.workshopId = workshopId;
        this.fkWorkshopId = fkWorkshopId;
        this.fkWorkshopLocationId = fkWorkshopLocationId;
        this.fkTeacherId = fkTeacherId;
        this.workshopDate = workshopDate;
        this.students = students;
    }

    public Integer getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public Subject getFkWorkshopId() {
        return fkWorkshopId;
    }

    public void setFkWorkshopId(Subject fkWorkshopId) {
        this.fkWorkshopId = fkWorkshopId;
    }

    public WorkshopLocation getFkWorkshopLocationId() {
        return fkWorkshopLocationId;
    }

    public void setFkWorkshopLocationId(WorkshopLocation fkWorkshopLocationId) {
        this.fkWorkshopLocationId = fkWorkshopLocationId;
    }

    public Teacher getFkTeacherId() {
        return fkTeacherId;
    }

    public void setFkTeacherId(Teacher fkTeacherId) {
        this.fkTeacherId = fkTeacherId;
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
}
