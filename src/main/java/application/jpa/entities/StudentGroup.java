package application.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "studentGroupId")
public class StudentGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentGroupId;

    private String studentGroupName;

    @OneToMany(mappedBy = "studentGroup")
    private List<Student> students;

    public StudentGroup(String studentGroupName) {
        this.studentGroupName = studentGroupName;
    }

    public StudentGroup(Integer studentGroupId, String studentGroupName) {
        this.studentGroupId = studentGroupId;
        this.studentGroupName = studentGroupName;
    }
}
