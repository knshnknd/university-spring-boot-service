package application.dto;

import application.jpa.entities.Subject;
import application.jpa.entities.Teacher;
import application.jpa.entities.WorkshopLocation;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class WorkshopDTO {

    private Subject subject;
    private WorkshopLocation workshopLocation;
    private Teacher teacher;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date workshopDate;

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
}
