package application.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "studentId")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    private String studentFullName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(referencedColumnName = "studentGroupId")
    private StudentGroup studentGroup;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "student_workshops",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "workshopId")
    )
    private List<Workshop> workshops = new ArrayList<>();

    public Student() {
    }

    public Student(String studentFullName, StudentGroup studentGroup) {
        this.studentFullName = studentFullName;
        this.studentGroup = studentGroup;
    }

    public Student(Integer studentId, String studentFullName, StudentGroup studentGroup, List<Workshop> workshops) {
        this.studentId = studentId;
        this.studentFullName = studentFullName;
        this.studentGroup = studentGroup;
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

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }
}
