package application.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public Student(String studentFullName, StudentGroup studentGroup) {
        this.studentFullName = studentFullName;
        this.studentGroup = studentGroup;
    }

    public Student(Integer studentId, String studentFullName) {
        this.studentId = studentId;
        this.studentFullName = studentFullName;
    }

    public Student(Integer studentId, String studentFullName, StudentGroup studentGroup) {
        this.studentId = studentId;
        this.studentFullName = studentFullName;
        this.studentGroup = studentGroup;
    }
}
