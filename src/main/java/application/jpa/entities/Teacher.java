package application.jpa.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teacherId;
    private String teacherFullName;
    private String teacherScienceDegree;

    @OneToMany(mappedBy = "teacherId")
    private List<Workshop> workshops;

    public Teacher() {
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
