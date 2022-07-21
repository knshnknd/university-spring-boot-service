package application.controllers;

import application.dto.StudentGroupDTO;
import application.jpa.entities.StudentGroup;
import application.services.StudentGroupService;
import application.util.error_responses.ErrorResponse;
import application.util.exceptions.EntityNotCreatedException;
import application.util.validators.StudentGroupValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static application.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/groups")
public class StudentGroupController {
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

    // GET ALL
    // GET ONE
    // EDIT
    // DELETE

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid StudentGroupDTO studentGroupDTO,
                                             BindingResult bindingResult) {
        StudentGroup studentGroupToAdd = convertToStudentGroup(studentGroupDTO);

        // Нельзя создать группу с одинаковым названием
        studentGroupValidator.validate(studentGroupToAdd, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        studentGroupService.save(studentGroupToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(EntityNotCreatedException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private StudentGroup convertToStudentGroup(StudentGroupDTO studentGroupDTO) {
        return modelMapper.map(studentGroupDTO, StudentGroup.class);
    }

}
