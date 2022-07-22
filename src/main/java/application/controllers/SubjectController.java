package application.controllers;

import application.dto.requests.SubjectRequestDto;
import application.dto.responses.SubjectResponseDto;
import application.jpa.entities.Subject;
import application.services.SubjectService;
import application.validators.SubjectValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static application.exceptions.ErrorsUtil.returnErrorsToClient;

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
    public SubjectResponseDto getAll() {
        return new SubjectResponseDto(subjectService.findAll());
    }

    @GetMapping("/{id}")
    public Subject getOne(@PathVariable("id") Integer id) {
        return subjectService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SubjectRequestDto subjectRequestDTO,
                                             BindingResult bindingResult) {
        Subject subjectToCreate = convertToSubject(subjectRequestDTO);

        subjectValidator.validate(subjectToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        subjectService.save(subjectToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid SubjectRequestDto subjectRequestDTO,
                                                       BindingResult bindingResult, @PathVariable("id") int id) {
        Subject subjectToUpdate = convertToSubject(subjectRequestDTO);

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

    private Subject convertToSubject(SubjectRequestDto subjectRequestDTO) {
        return modelMapper.map(subjectRequestDTO, Subject.class);
    }
}
