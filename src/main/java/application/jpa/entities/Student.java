package application.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    @NotEmpty(message = "ФИО студента не может быть пустым.")
    private String studentFullName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "student_group_name", referencedColumnName = "studentGroupName")
    private StudentGroup studentGroupName;

    @ManyToMany
    @JoinTable(
            name = "student_workshops",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "workshopId")
    )
    private List<Workshop> workshops;

    public Student() {
    }

    public Student(Integer studentId, String studentFullName, StudentGroup studentGroupName, List<Workshop> workshops) {
        this.studentId = studentId;
        this.studentFullName = studentFullName;
        this.studentGroupName = studentGroupName;
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

    public StudentGroup getStudentGroupName() {
        return studentGroupName;
    }

    public void setStudentGroupName(StudentGroup studentGroupName) {
        this.studentGroupName = studentGroupName;
    }
}
