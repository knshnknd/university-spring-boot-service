package application.controllers;

import application.dto.StudentDTO;
import application.dto.StudentResponse;
import application.jpa.entities.Student;
import application.jpa.entities.Workshop;
import application.services.StudentService;
import application.util.error_responses.ErrorResponse;
import application.util.exceptions.EntityNotCreatedException;
import application.util.exceptions.EntityNotFoundException;
import application.util.validators.StudentValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    public StudentResponse getAll() {
        // Оборачиваем список из всех объектов в один внешний объект для пересылки
        return new StudentResponse(new ArrayList<>(studentService.findAll()));
    }

    @GetMapping("/{id}")
    public Student getOne(@PathVariable("id") Integer id) {
        return studentService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid StudentDTO studentDTO, BindingResult bindingResult) {
        Student studentToCreate = convertToStudent(studentDTO);

        studentValidator.validate(studentToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        studentService.save(studentToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid StudentDTO studentDTO,
                                             BindingResult bindingResult, @PathVariable("id") int id) {
        Student studentToUpdate = convertToStudent(studentDTO);

        studentValidator.validate(studentToUpdate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        studentService.update(id, studentToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        studentService.delete(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{id}/schedule")
    public List<Workshop> getSchedule(@PathVariable("id") Integer id) {
        return studentService.getWorkshops(id);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(EntityNotCreatedException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(EntityNotFoundException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    private Student convertToStudent(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, Student.class);
    }
}
