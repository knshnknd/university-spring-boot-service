package application.controllers;

import application.dto.SubjectDTO;
import application.jpa.entities.Subject;
import application.services.SubjectService;
import application.util.error_responses.ErrorResponse;
import application.util.exceptions.EntityNotCreatedException;
import application.util.exceptions.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static application.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final ModelMapper modelMapper;

    @Autowired
    public SubjectController(SubjectService subjectService, ModelMapper modelMapper) {
        this.subjectService = subjectService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Subject> getSubjects() {
        return subjectService.findAll();
    }

    @GetMapping("/{id}")
    public Subject getSubject(@PathVariable("id") Integer id) {
        return subjectService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SubjectDTO subjectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        subjectService.save(convertToSubject(subjectDTO));

        return ResponseEntity.ok(HttpStatus.OK); // 200
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(EntityNotFoundException exception) {
         ErrorResponse response = new ErrorResponse("Дисциплина с таким ID не найдена.");

         return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(EntityNotCreatedException exception) {
         ErrorResponse response = new ErrorResponse(exception.getMessage());

         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //
    }

    private Subject convertToSubject(SubjectDTO subjectDTO) {
        return modelMapper.map(subjectDTO, Subject.class);
    }

    private SubjectDTO convertToSubjectDTO(Subject subject) {
        return modelMapper.map(subject, SubjectDTO.class);
    }
}
