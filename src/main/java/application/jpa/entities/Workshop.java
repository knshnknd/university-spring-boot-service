package application.jpa.entities;

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

    private String workshopAddress;
    private Date workshopDate;

    @ManyToOne
    @JoinColumn(referencedColumnName = "teacherId")
    private Teacher fkTeacherId;

    @ManyToMany(mappedBy = "workshops")
    private List<Student> students;

    public Workshop() {
    }

    public Workshop(Integer workshopId, Subject fkWorkshopId, String workshopAddress,
                    Date workshopDate, Teacher fkTeacherId, List<Student> students) {
        this.workshopId = workshopId;
        this.fkWorkshopId = fkWorkshopId;
        this.workshopAddress = workshopAddress;
        this.workshopDate = workshopDate;
        this.fkTeacherId = fkTeacherId;
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

    public String getWorkshopAddress() {
        return workshopAddress;
    }

    public void setWorkshopAddress(String workshopAddress) {
        this.workshopAddress = workshopAddress;
    }

    public Date getWorkshopDate() {
        return workshopDate;
    }

    public void setWorkshopDate(Date workshopDate) {
        this.workshopDate = workshopDate;
    }

    public Teacher getFkTeacherId() {
        return fkTeacherId;
    }

    public void setFkTeacherId(Teacher fkTeacherId) {
        this.fkTeacherId = fkTeacherId;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
