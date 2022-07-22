package application.services;

import application.jpa.entities.*;
import application.jpa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DataPreloadService {
    private StudentRepository studentRepository;
    private StudentGroupRepository studentGroupRepository;
    private SubjectRepository subjectRepository;
    private TeacherRepository teacherRepository;
    private WorkshopRepository workshopRepository;
    private WorkshopLocationRepository workshopLocationRepository;
    private StudentService studentService;

    @Autowired
    public DataPreloadService(StudentRepository studentRepository, StudentGroupRepository studentGroupRepository,
                              SubjectRepository subjectRepository, TeacherRepository teacherRepository,
                              WorkshopRepository workshopRepository, WorkshopLocationRepository workshopLocationRepository,
                              StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.workshopRepository = workshopRepository;
        this.workshopLocationRepository = workshopLocationRepository;
        this.studentService = studentService;
    }

    public void preloadData() {
        preloadSubjects();
        preloadTeachers();
        preloadWorkshopLocations();
        preloadStudentGroups();
        preloadStudents();
        preloadWorkshops();
    }

    private void preloadTeachers() {
        List<Teacher> teacherList = List.of(
                new Teacher("John Smith", "PhD in history"),
                new Teacher("Bob Smith", "MBA"),
                new Teacher("Jake Johnson", "PhD in medicine")
        );

        teacherRepository.saveAll(teacherList);
    }

    private void preloadWorkshopLocations() {
        List<WorkshopLocation> workshopLocationList = List.of(
                new WorkshopLocation("Main building, auditorium 205"),
                new WorkshopLocation("Main building, gym"),
                new WorkshopLocation("Library, auditorium 12")
        );

        workshopLocationRepository.saveAll(workshopLocationList);
    }

    private void preloadSubjects() {
        List<Subject> subjectList = List.of(
                new Subject("Economics"),
                new Subject("Algorithmization and programming"),
                new Subject("History of the Ancient World")
        );

        subjectRepository.saveAll(subjectList);
    }

    private void preloadStudentGroups() {
        List<StudentGroup> studentGroups = List.of(
                new StudentGroup("Group 1"),
                new StudentGroup("Group 2")
        );

        studentGroupRepository.saveAll(studentGroups);
    }

    private void preloadStudents() {
        List<Student> students = List.of(
                new Student("William Blake", studentGroupRepository.findById(1).get()),
                new Student("John Milton", studentGroupRepository.findById(1).get()),
                new Student("William Shakespeare", studentGroupRepository.findById(1).get()),
                new Student("Rudyard Kipling", studentGroupRepository.findById(2).get()),
                new Student("Matthew Arnold", studentGroupRepository.findById(2).get())
        );

        studentRepository.saveAll(students);
    }
    private void preloadWorkshops() {
        List<Workshop> workshops = new ArrayList<>();
        List<Student> foundStudents = studentRepository.findAll();

        try {
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = simpleDateFormat.parse("2022-07-22");
            Date end = simpleDateFormat.parse("2022-08-22");

            calendar.setTime(start);

            do {
                Date d = calendar.getTime();

                for (int i = 1; i <= 3; i++) {
                    Workshop workshop = new Workshop(subjectRepository.findById(i).get(),
                            workshopLocationRepository.findById(i).get(),
                            teacherRepository.findById(i).get(),
                            d, foundStudents);

                    workshops.add(workshop);

                    for (int y = 1; y <= 5; y++){
                        Student newStudent = studentService.findOptionalById(y).get();
                        newStudent.getWorkshops().add(workshop);
                    }
                }

                calendar.add(Calendar.DAY_OF_MONTH, 1);
            } while (calendar.getTime().before(end));
        } catch (ParseException ignore) {}

        workshopRepository.saveAll(workshops);
    }
}
