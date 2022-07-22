package application.controllers;

import application.dto.requests.StudentRequestDto;
import application.dto.responses.StudentResponseDto;
import application.jpa.entities.Student;
import application.jpa.entities.Workshop;
import application.services.StudentService;
import application.validators.StudentValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Date;
import java.util.List;

import static application.exceptions.ErrorsUtil.returnErrorsToClient;

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
    public StudentResponseDto getAll() {
        return new StudentResponseDto(studentService.findAll());
    }

    @GetMapping("/{id}")
    public Student getOne(@PathVariable("id") Integer id) {
        return studentService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid StudentRequestDto studentRequestDTO, BindingResult bindingResult) {
        Student studentToCreate = convertToStudent(studentRequestDTO);

        studentValidator.validate(studentToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        studentService.save(studentToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid StudentRequestDto studentRequestDTO,
                                             BindingResult bindingResult, @PathVariable("id") int id) {
        Student studentToUpdate = convertToStudent(studentRequestDTO);

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

    // Расписание на конкретный день. В параметры передать дату, например: students/{id}/schedule?date=2000-11-22
   @GetMapping("/{id}/schedule")
   public List<Workshop> getSchedule(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                     @PathVariable("id") Integer id) {

        return studentService.getWorkshopsByDate(id, date);
   }
    private Student convertToStudent(StudentRequestDto studentRequestDTO) {
        return modelMapper.map(studentRequestDTO, Student.class);
    }
}
