package application.jpa.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Student {

    @Id
    private Integer studentId;
    private String studentFullName;

    @ManyToMany
    @JoinTable(
            name = "student_workshops",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "workshopId")
    )
    private List<Workshop> workshops;

    public Student() {
    }

    public Student(Integer studentId, String studentFullName, List<Workshop> workshops) {
        this.studentId = studentId;
        this.studentFullName = studentFullName;
        this.workshops = workshops;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }
}
