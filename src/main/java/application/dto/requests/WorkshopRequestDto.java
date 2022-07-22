package application.dto.requests;

import application.jpa.entities.Student;
import application.jpa.entities.Subject;
import application.jpa.entities.Teacher;
import application.jpa.entities.WorkshopLocation;


import java.util.Date;
import java.util.List;

public class WorkshopRequestDto {

    private Subject subject;
    private WorkshopLocation workshopLocation;
    private Teacher teacher;
    private Date workshopDate;

    private List<Student> students;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public WorkshopLocation getWorkshopLocation() {
        return workshopLocation;
    }

    public void setWorkshopLocation(WorkshopLocation workshopLocation) {
        this.workshopLocation = workshopLocation;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Date getWorkshopDate() {
        return workshopDate;
    }

    public void setWorkshopDate(Date workshopDate) {
        this.workshopDate = workshopDate;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
