package application;

import application.jpa.entities.*;
import application.jpa.repositories.*;
import application.services.StudentService;
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
public class DataPreloadingObject {
    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final WorkshopRepository workshopRepository;
    private final WorkshopLocationRepository workshopLocationRepository;
    private final StudentService studentService;

    @Autowired
    public DataPreloadingObject(StudentRepository studentRepository, StudentGroupRepository studentGroupRepository,
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
        System.out.println("Subjects preloaded: " + preloadSubjects());
        System.out.println("Teachers preloaded: " + preloadTeachers());
        System.out.println("Workshop locations preloaded: " + preloadWorkshopLocations());
        System.out.println("Student groups preloaded: " + preloadStudentGroups());
        System.out.println("Students preloaded: " + preloadStudents());
        System.out.println("Workshops preloaded: " + preloadWorkshops());
    }

    private boolean preloadTeachers() {
        List<Teacher> teacherList = List.of(
                new Teacher("John Smith", "PhD in history"),
                new Teacher("Bob Smith", "MBA"),
                new Teacher("Jake Johnson", "PhD in medicine")
        );

        teacherRepository.saveAll(teacherList);
        return true;
    }

    private boolean preloadWorkshopLocations() {
        List<WorkshopLocation> workshopLocationList = List.of(
                new WorkshopLocation("Main building, auditorium 205"),
                new WorkshopLocation("Main building, gym"),
                new WorkshopLocation("Library, auditorium 12")
        );

        workshopLocationRepository.saveAll(workshopLocationList);
        return true;
    }

    private boolean preloadSubjects() {
        List<Subject> subjectList = List.of(
                new Subject("Economics"),
                new Subject("Algorithmization and programming"),
                new Subject("History of the Ancient World")
        );

        subjectRepository.saveAll(subjectList);
        return true;
    }

    private boolean preloadStudentGroups() {
        List<StudentGroup> studentGroups = List.of(
                new StudentGroup("Group 1"),
                new StudentGroup("Group 2")
        );

        studentGroupRepository.saveAll(studentGroups);
        return true;
    }

    private boolean preloadStudents() {
        List<Student> students = List.of(
                new Student("William Blake", studentGroupRepository.findById(1).get()),
                new Student("John Milton", studentGroupRepository.findById(1).get()),
                new Student("William Shakespeare", studentGroupRepository.findById(1).get()),
                new Student("Rudyard Kipling", studentGroupRepository.findById(2).get()),
                new Student("Matthew Arnold", studentGroupRepository.findById(2).get())
        );

        studentRepository.saveAll(students);
        return true;
    }
    private boolean preloadWorkshops() {
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
        return true;
    }
}
