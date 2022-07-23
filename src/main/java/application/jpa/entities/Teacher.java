package application.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(scope = Teacher.class,
        generator = ObjectIdGenerators.PropertyGenerator.class, property = "teacherId")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teacherId;
    private String teacherFullName;
    private String teacherScienceDegree;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private List<Workshop> workshops;

    public Teacher(Integer teacherId, String teacherFullName, String teacherScienceDegree) {
        this.teacherId = teacherId;
        this.teacherFullName = teacherFullName;
        this.teacherScienceDegree = teacherScienceDegree;
    }

    public Teacher(String teacherFullName, String teacherScienceDegree) {
        this.teacherFullName = teacherFullName;
        this.teacherScienceDegree = teacherScienceDegree;
    }
}
