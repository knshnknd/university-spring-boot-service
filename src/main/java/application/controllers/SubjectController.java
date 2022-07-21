package application.controllers;

import application.dto.*;
import application.jpa.entities.Subject;
import application.services.SubjectService;
import application.util.error_responses.ErrorResponse;
import application.util.exceptions.EntityNotCreatedException;
import application.util.exceptions.EntityNotFoundException;
import application.util.validators.SubjectValidator;
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
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectValidator subjectValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SubjectController(SubjectService subjectService, SubjectValidator subjectValidator,
                             ModelMapper modelMapper) {
        this.subjectService = subjectService;
        this.subjectValidator = subjectValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public SubjectResponse getAll() {
        // Оборачиваем список из всех объектов в один внешний объект для пересылки
        return new SubjectResponse(new ArrayList<>(subjectService.findAll()));
    }

    @GetMapping("/{id}")
    public Subject getOne(@PathVariable("id") Integer id) {
        return subjectService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SubjectDTO subjectDTO,
                                             BindingResult bindingResult) {
        Subject subjectToCreate = convertToSubject(subjectDTO);

        subjectValidator.validate(subjectToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        subjectService.save(subjectToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid SubjectDTO subjectDTO,
                                                       BindingResult bindingResult, @PathVariable("id") int id) {
        Subject subjectToUpdate = convertToSubject(subjectDTO);

        subjectValidator.validate(subjectToUpdate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        subjectService.update(id, subjectToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        subjectService.delete(id);
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

    private Subject convertToSubject(SubjectDTO subjectDTO) {
        return modelMapper.map(subjectDTO, Subject.class);
    }
}
