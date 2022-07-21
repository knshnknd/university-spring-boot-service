package application.controllers;

import application.dto.StudentDTO;
import application.dto.WorkshopDTO;
import application.dto.WorkshopResponse;
import application.jpa.entities.Student;
import application.jpa.entities.Workshop;
import application.services.WorkshopService;
import application.util.error_responses.ErrorResponse;
import application.util.exceptions.EntityNotCreatedException;
import application.util.exceptions.EntityNotFoundException;
import application.util.validators.WorkshopValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static application.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/workshops")
public class WorkshopController {
    private final WorkshopService workshopService;
    private final WorkshopValidator workshopValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public WorkshopController(WorkshopService workshopService,
                              WorkshopValidator workshopValidator, ModelMapper modelMapper) {
        this.workshopService = workshopService;
        this.workshopValidator = workshopValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public WorkshopResponse getAll() {
        // Оборачиваем список из всех объектов в один внешний объект для пересылки
        return new WorkshopResponse(workshopService.findAll()
                                                    .stream()
                                                    .map(this::convertToWorkshopDTO)
                                                    .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public Workshop getOne(@PathVariable("id") Integer id) {
        return workshopService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid WorkshopDTO workshopDTO,
                                             BindingResult bindingResult) {
        Workshop workshopToCreate = convertToWorkshop(workshopDTO);

        workshopValidator.validate(workshopToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        workshopService.save(workshopToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid WorkshopDTO workshopDTO,
                                             BindingResult bindingResult, @PathVariable("id") int id) {
        Workshop workshopToUpdate = convertToWorkshop(workshopDTO);

        workshopValidator.validate(workshopToUpdate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        workshopService.update(id, workshopToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("{workshopId}/students/{studentId}")
    public ResponseEntity<HttpStatus> enrollStudentToWorkshop(@PathVariable int workshopId,
                                                              @PathVariable int studentId) {
        workshopService.enroll(workshopId, studentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        workshopService.delete(id);
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
    private Workshop convertToWorkshop(WorkshopDTO workshopDTO) {
        return modelMapper.map(workshopDTO, Workshop.class);
    }
    private WorkshopDTO convertToWorkshopDTO(Workshop workshop) {
        return modelMapper.map(workshop, WorkshopDTO.class);
    }
}
