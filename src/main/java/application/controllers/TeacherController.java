package application.controllers;

import application.dto.requests.TeacherRequestDto;
import application.dto.responses.TeacherResponseDto;
import application.jpa.entities.Teacher;
import application.services.TeacherService;
import application.validators.TeacherValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static application.exceptions.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherValidator teacherValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public TeacherController(TeacherService teacherService, TeacherValidator teacherValidator,
                             ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.teacherValidator = teacherValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public TeacherResponseDto getAll() {
        return new TeacherResponseDto(teacherService.findAll());
    }

    @GetMapping("/{id}")
    public Teacher getOne(@PathVariable("id") Integer id) {
        return teacherService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TeacherRequestDto teacherRequestDTO,
                                             BindingResult bindingResult) {
        Teacher teacherToCreate = convertToTeacher(teacherRequestDTO);

        teacherValidator.validate(teacherToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        teacherService.save(teacherToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid TeacherRequestDto teacherRequestDTO,
                                             BindingResult bindingResult, @PathVariable("id") int id) {
        Teacher teacherToUpdate = convertToTeacher(teacherRequestDTO);

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

    private Teacher convertToTeacher(TeacherRequestDto teacherRequestDTO) {
        return modelMapper.map(teacherRequestDTO, Teacher.class);
    }

}
