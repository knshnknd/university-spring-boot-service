package application.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(scope = Teacher.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "teacherId")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teacherId;
    private String teacherFullName;
    private String teacherScienceDegree;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private List<Workshop> workshops;

    public Teacher() {
    }

    public Teacher(Integer teacherId, String teacherFullName, String teacherScienceDegree) {
        this.teacherId = teacherId;
        this.teacherFullName = teacherFullName;
        this.teacherScienceDegree = teacherScienceDegree;
    }

    public Teacher(String teacherFullName, String teacherScienceDegree) {
        this.teacherFullName = teacherFullName;
        this.teacherScienceDegree = teacherScienceDegree;
    }

    public Teacher(Integer teacherId, String teacherFullName,
                   String teacherScienceDegree, List<Workshop> workshops) {
        this.teacherId = teacherId;
        this.teacherFullName = teacherFullName;
        this.teacherScienceDegree = teacherScienceDegree;
        this.workshops = workshops;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public void setTeacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }

    public String getTeacherScienceDegree() {
        return teacherScienceDegree;
    }

    public void setTeacherScienceDegree(String teacherScienceDegree) {
        this.teacherScienceDegree = teacherScienceDegree;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }
}
