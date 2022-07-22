package application.controllers;

import application.dto.TeacherDTO;
import application.dto.TeacherResponse;
import application.jpa.entities.Teacher;
import application.services.TeacherService;
import application.util.error_responses.ErrorResponse;
import application.util.exceptions.EntityNotCreatedException;
import application.util.exceptions.EntityNotFoundException;
import application.util.validators.TeacherValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

import static application.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherValidator teacherValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public TeacherController(TeacherService teacherService, TeacherValidator teacherValidator, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.teacherValidator = teacherValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public TeacherResponse getAll() {
        // Оборачиваем список из всех объектов в один внешний объект для пересылки
        return new TeacherResponse(new ArrayList<>(teacherService.findAll()));
    }

    @GetMapping("/{id}")
    public Teacher getOne(@PathVariable("id") Integer id) {
        return teacherService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TeacherDTO teacherDTO, BindingResult bindingResult) {
        Teacher teacherToCreate = convertToTeacher(teacherDTO);

        teacherValidator.validate(teacherToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        teacherService.save(teacherToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid TeacherDTO teacherDTO,
                                             BindingResult bindingResult, @PathVariable("id") int id) {
        Teacher teacherToUpdate = convertToTeacher(teacherDTO);

        teacherValidator.validate(teacherToUpdate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        teacherService.update(id, teacherToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        teacherService.delete(id);
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
    private Teacher convertToTeacher(TeacherDTO teacherDTO) {
        return modelMapper.map(teacherDTO, Teacher.class);
    }

}
