package application.controllers;

import application.dto.SubjectDTO;
import application.dto.SubjectResponse;
import application.dto.WorkshopLocationDTO;
import application.dto.WorkshopLocationResponse;
import application.jpa.entities.Subject;
import application.jpa.entities.WorkshopLocation;
import application.services.SubjectService;
import application.services.WorkshopLocationService;
import application.util.error_responses.ErrorResponse;
import application.util.exceptions.EntityNotCreatedException;
import application.util.exceptions.EntityNotFoundException;
import application.util.validators.SubjectValidator;
import application.util.validators.WorkshopLocationValidator;
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
@RequestMapping("/subject")
public class WorkshopLocationController {

    private final WorkshopLocationService workshopLocationService;
    private final WorkshopLocationValidator workshopLocationValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public WorkshopLocationController(WorkshopLocationService workshopLocationService,
                                      WorkshopLocationValidator workshopLocationValidator, ModelMapper modelMapper) {
        this.workshopLocationService = workshopLocationService;
        this.workshopLocationValidator = workshopLocationValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public WorkshopLocationResponse getAll() {
        // Оборачиваем список из всех объектов в один внешний объект для пересылки
        return new WorkshopLocationResponse(new ArrayList<>(workshopLocationService.findAll()));
    }

    @GetMapping("/{id}")
    public WorkshopLocation getOne(@PathVariable("id") Integer id) {
        return workshopLocationService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid WorkshopLocationDTO workshopLocationDTO,
                                             BindingResult bindingResult) {
        WorkshopLocation workshopLocationToCreate = convertToSubject(workshopLocationDTO);

        workshopLocationValidator.validate(workshopLocationToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        workshopLocationService.save(workshopLocationToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid WorkshopLocationDTO workshopLocationDTO,
                                                       BindingResult bindingResult, @PathVariable("id") int id) {
        WorkshopLocation workshopLocationToUpdate = convertToSubject(workshopLocationDTO);

        workshopLocationValidator.validate(workshopLocationToUpdate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        workshopLocationService.update(id, workshopLocationToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        workshopLocationService.delete(id);
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

    private WorkshopLocation convertToSubject(WorkshopLocationDTO workshopLocationDTO) {
        return modelMapper.map(workshopLocationDTO, WorkshopLocation.class);
    }
}
