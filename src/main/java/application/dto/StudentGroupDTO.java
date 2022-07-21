package application.dto;

import javax.validation.constraints.NotEmpty;

public class StudentGroupDTO {

    @NotEmpty(message = "Название группы не должно быть пустым")
    private String studentGroupName;

    public String getStudentGroupName() {
        return studentGroupName;
    }

    public void setStudentGroupName(String studentGroupName) {
        this.studentGroupName = studentGroupName;
    }
}
