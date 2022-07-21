package application.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class StudentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentGroupId;

    @NotEmpty(message = "Название группы не должно быть пустым")
    private String studentGroupName;

//    @OneToMany(mappedBy = "fkStudentGroupId")
//    private List<Student> students;

    public StudentGroup() {
    }

    public StudentGroup(String studentGroupName) {
        this.studentGroupName = studentGroupName;
    }

    public StudentGroup(Integer studentGroupId, String studentGroupName) {
        this.studentGroupId = studentGroupId;
        this.studentGroupName = studentGroupName;
    }

    public Integer getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(Integer studentGroupId) {
        this.studentGroupId = studentGroupId;
    }

    public String getStudentGroupName() {
        return studentGroupName;
    }

    public void setStudentGroupName(String studentGroupName) {
        this.studentGroupName = studentGroupName;
    }
//
//    public List<Student> getStudents() {
//        return students;
//    }
//
//    public void setStudents(List<Student> students) {
//        this.students = students;
//    }
}
