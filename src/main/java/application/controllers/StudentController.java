package application.controllers;

import application.dto.StudentDTO;
import application.dto.StudentResponse;
import application.jpa.entities.Student;
import application.services.StudentService;
import application.util.error_responses.ErrorResponse;
import application.util.exceptions.EntityNotCreatedException;
import application.util.validators.StudentValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.stream.Collectors;

import static application.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentValidator studentValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentController(StudentService studentService,
                             StudentValidator studentValidator, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.studentValidator = studentValidator;
        this.modelMapper = modelMapper;
    }

    // FIND ALL
    @GetMapping
    public StudentResponse getStudents() {
        // Оборачиваем список из всех студентов в один внешний объект для пересылки
        return new StudentResponse(studentService.findAll()
                .stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList()));
    }
    // FIND ONE
    // UPDATE

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid StudentDTO studentDTO,
                                          BindingResult bindingResult) {

        Student studentToAdd = convertToStudent(studentDTO);

        studentValidator.validate(studentToAdd, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        studentService.save(studentToAdd);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(EntityNotCreatedException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Student convertToStudent(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, Student.class);
    }
    private StudentDTO convertToStudentDTO(Student student) {
        return modelMapper.map(student, StudentDTO.class);
    }
}
