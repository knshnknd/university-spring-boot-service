package application.dto.requests;

public class TeacherRequestDto {

    private String teacherFullName;
    private String teacherScienceDegree;

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
}
