package application.util;

import application.util.exceptions.EntityNotCreatedException;
import application.util.exceptions.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EntityNotFoundException.class, EntityNotCreatedException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {

        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof EntityNotFoundException enfe) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            return handleEntityNotFoundException(enfe, headers, status);
        } else if (ex instanceof EntityNotCreatedException ente) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            return handleEntityNotCreatedException(ente, headers, status);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, headers, status, request);
        }

    }

    protected ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException exception,
                                                                     HttpHeaders headers, HttpStatus status) {
        ApiError response = new ApiError(exception.getMessage());
        return new ResponseEntity<>(response, headers, status);
    }

    protected ResponseEntity<ApiError> handleEntityNotCreatedException(EntityNotCreatedException exception,
                                                                       HttpHeaders headers, HttpStatus status) {
        ApiError response = new ApiError(exception.getMessage());
        return new ResponseEntity<>(response, headers, status);
    }

    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(null, headers, status);
    }
}
