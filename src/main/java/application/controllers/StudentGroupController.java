package application.controllers;

import application.dto.StudentGroupDTO;
import application.dto.StudentGroupResponse;
import application.dto.StudentResponse;
import application.jpa.entities.Student;
import application.jpa.entities.StudentGroup;
import application.jpa.entities.Subject;
import application.services.StudentGroupService;
import application.util.error_responses.ErrorResponse;
import application.util.exceptions.EntityNotCreatedException;
import application.util.exceptions.EntityNotFoundException;
import application.util.validators.StudentGroupValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static application.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/groups")
public class StudentGroupController {
// public class StudentGroupController extends ResponseEntityExceptionHandler {
    private final StudentGroupService studentGroupService;
    private final StudentGroupValidator studentGroupValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentGroupController(StudentGroupService studentGroupService,
                                  StudentGroupValidator studentGroupValidator, ModelMapper modelMapper) {
        this.studentGroupService = studentGroupService;
        this.studentGroupValidator = studentGroupValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public StudentGroupResponse getStudentGroups() {
        // Оборачиваем список из всех студентов в один внешний объект для пересылки
        return new StudentGroupResponse(new ArrayList<>(studentGroupService.findAll()));
    }

    @GetMapping("/{id}")
    public StudentGroup setStudentGroup(@PathVariable("id") Integer id) {
        return studentGroupService.findOne(id);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid StudentGroupDTO studentGroupDTO,
                                             BindingResult bindingResult) {
        StudentGroup studentGroupToCreate = convertToStudentGroup(studentGroupDTO);

        studentGroupValidator.validate(studentGroupToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        studentGroupService.save(studentGroupToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateDepartment(@RequestBody @Valid StudentGroupDTO studentGroupDTO,
                                                       BindingResult bindingResult, @PathVariable("id") int id) {
        StudentGroup studentGroupToUpdate = convertToStudentGroup(studentGroupDTO);

        studentGroupValidator.validate(studentGroupToUpdate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        studentGroupService.update(id, studentGroupToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable("id") int id) {
        studentGroupService.delete(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
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

    private StudentGroup convertToStudentGroup(StudentGroupDTO studentGroupDTO) {
        return modelMapper.map(studentGroupDTO, StudentGroup.class);
    }

}
