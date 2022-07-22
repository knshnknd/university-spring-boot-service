package application.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(scope = Workshop.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "workshopId")
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workshopId;

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

    public Workshop() {
    }

    public Workshop(Subject subject, WorkshopLocation workshopLocation, Teacher teacher,
                    Date workshopDate, List<Student> students) {
        this.subject = subject;
        this.workshopLocation = workshopLocation;
        this.teacher = teacher;
        this.workshopDate = workshopDate;
        this.students = students;
    }

    public Workshop(Integer workshopId, Subject subject, WorkshopLocation workshopLocation, Teacher teacher,
                    Date workshopDate, List<Student> students) {
        this.workshopId = workshopId;
        this.subject = subject;
        this.workshopLocation = workshopLocation;
        this.teacher = teacher;
        this.workshopDate = workshopDate;
        this.students = students;
    }

    public Integer getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public WorkshopLocation getWorkshopLocation() {
        return workshopLocation;
    }

    public void setWorkshopLocation(WorkshopLocation workshopLocation) {
        this.workshopLocation = workshopLocation;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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

    public void enrollStudent(Student student) {
        students.add(student);
    }
}
