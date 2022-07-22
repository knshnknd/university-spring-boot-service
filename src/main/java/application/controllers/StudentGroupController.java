package application.controllers;

import application.dto.requests.StudentGroupRequestDto;
import application.dto.responses.StudentGroupResponseDto;
import application.jpa.entities.StudentGroup;
import application.services.StudentGroupService;
import application.validators.StudentGroupValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static application.exceptions.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/student_groups")
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

    @GetMapping
    public StudentGroupResponseDto getAll() {
        // Оборачиваем список из всех объектов в один внешний объект для пересылки
        return new StudentGroupResponseDto(studentGroupService.findAll());
    }

    @GetMapping("/{id}")
    public StudentGroup getOne(@PathVariable("id") Integer id) {
        return studentGroupService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid StudentGroupRequestDto studentGroupRequestDTO,
                                             BindingResult bindingResult) {
        StudentGroup studentGroupToCreate = convertToStudentGroup(studentGroupRequestDTO);

        studentGroupValidator.validate(studentGroupToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        studentGroupService.save(studentGroupToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid StudentGroupRequestDto studentGroupRequestDTO,
                                                       BindingResult bindingResult, @PathVariable("id") int id) {
        StudentGroup studentGroupToUpdate = convertToStudentGroup(studentGroupRequestDTO);

        studentGroupValidator.validate(studentGroupToUpdate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        studentGroupService.update(id, studentGroupToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        studentGroupService.delete(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    private StudentGroup convertToStudentGroup(StudentGroupRequestDto studentGroupRequestDTO) {
        return modelMapper.map(studentGroupRequestDTO, StudentGroup.class);
    }

}
