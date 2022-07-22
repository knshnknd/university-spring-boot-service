package application.controllers;

import application.dto.requests.WorkshopRequestDto;
import application.dto.responses.WorkshopResponseDto;
import application.jpa.entities.Workshop;
import application.services.WorkshopService;
import application.validators.WorkshopValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static application.exceptions.ErrorsUtil.returnErrorsToClient;

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
    public WorkshopResponseDto getAll() {
        // Оборачиваем список из всех объектов в один внешний объект для пересылки
        return new WorkshopResponseDto(workshopService.findAll()
                                                    .stream()
                                                    .map(this::convertToWorkshopDTO)
                                                    .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public Workshop getOne(@PathVariable("id") Long id) {
        return workshopService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid WorkshopRequestDto workshopRequestDTO,
                                             BindingResult bindingResult) {
        Workshop workshopToCreate = convertToWorkshop(workshopRequestDTO);

        workshopValidator.validate(workshopToCreate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        workshopService.save(workshopToCreate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid WorkshopRequestDto workshopRequestDTO,
                                             BindingResult bindingResult, @PathVariable("id") Long id) {
        Workshop workshopToUpdate = convertToWorkshop(workshopRequestDTO);

        workshopValidator.validate(workshopToUpdate, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        workshopService.update(id, workshopToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        workshopService.delete(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    private Workshop convertToWorkshop(WorkshopRequestDto workshopRequestDTO) {
        return modelMapper.map(workshopRequestDTO, Workshop.class);
    }
    private WorkshopRequestDto convertToWorkshopDTO(Workshop workshop) {
        return modelMapper.map(workshop, WorkshopRequestDto.class);
    }
}
