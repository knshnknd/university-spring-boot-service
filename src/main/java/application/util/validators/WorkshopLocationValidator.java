package application.util.validators;

import application.jpa.entities.Subject;
import application.jpa.entities.WorkshopLocation;
import application.services.SubjectService;
import application.services.WorkshopLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class WorkshopLocationValidator implements Validator {
    private final WorkshopLocationService workshopLocationService;

    @Autowired
    public WorkshopLocationValidator(WorkshopLocationService workshopLocationService) {
        this.workshopLocationService = workshopLocationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return WorkshopLocation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WorkshopLocation workshopLocation = (WorkshopLocation) target;

        if (workshopLocation.getWorkshopLocationFullAddress() == null
                || workshopLocation.getWorkshopLocationFullAddress().equals("")) {
            errors.rejectValue("workshopLocationFullAddress", "",
                    "The full address of the workshop location must not be empty.");;
        }

        if(workshopLocationService.findByName(workshopLocation.getWorkshopLocationFullAddress()).isPresent()) {
            errors.rejectValue("workshopLocationFullAddress", "",
                    "A workshop location with that full address already exists.");
        }
    }
}
