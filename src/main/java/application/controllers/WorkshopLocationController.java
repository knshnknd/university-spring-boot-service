package application.controllers;

import application.dto.requests.WorkshopLocationRequestDto;
import application.dto.responses.WorkshopLocationResponse;
import application.jpa.entities.WorkshopLocation;
import application.services.WorkshopLocationService;
import application.validators.WorkshopLocationValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static application.exceptions.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/workshop-locations")
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
        return new WorkshopLocationResponse(workshopLocationService.findAll());
    }

    @GetMapping("/{id}")
    public WorkshopLocation getOne(@PathVariable("id") Integer id) {
        return workshopLocationService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid WorkshopLocationRequestDto workshopLocationRequestDto,
                                             BindingResult bindingResult) {
        WorkshopLocation workshopLocationToCreate = convertToWorkshopLocation(workshopLocationRequestDto);

        workshopLocationValidator.validate(workshopLocationToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        workshopLocationService.save(workshopLocationToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid WorkshopLocationRequestDto workshopLocationRequestDto,
                                                       BindingResult bindingResult, @PathVariable("id") int id) {
        WorkshopLocation workshopLocationToUpdate = convertToWorkshopLocation(workshopLocationRequestDto);

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

    private WorkshopLocation convertToWorkshopLocation(WorkshopLocationRequestDto workshopLocationRequestDto) {
        return modelMapper.map(workshopLocationRequestDto, WorkshopLocation.class);
    }
}
