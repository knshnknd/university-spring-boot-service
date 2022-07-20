package application.jpa.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Student {

    @Id
    private Integer studentId;
    private String studentFullName;

    @ManyToOne
    @JoinColumn(referencedColumnName = "studentGroupId")
    private StudentGroup fkStudentGroupId;

    @ManyToMany
    @JoinTable(
            name = "student_workshops",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "workshopId")
    )
    private List<Workshop> workshops;

    public Student() {
    }

    public Student(Integer studentId, String studentFullName, StudentGroup fkStudentGroupId, List<Workshop> workshops) {
        this.studentId = studentId;
        this.studentFullName = studentFullName;
        this.fkStudentGroupId = fkStudentGroupId;
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

    public StudentGroup getFkStudentGroupId() {
        return fkStudentGroupId;
    }

    public void setFkStudentGroupId(StudentGroup fkStudentGroupId) {
        this.fkStudentGroupId = fkStudentGroupId;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }
}
