package ru.yandex.practicum.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.error.exception.ConflictOnRequestException;
import ru.yandex.practicum.error.exception.IncorrectRequestException;
import ru.yandex.practicum.error.exception.NotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public String handleNotFoundException(final NotFoundException e, Model model) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getName(), e.getMessage());
        model.addAttribute("errorResponse", errorResponse);
        return "error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public String handleIncorrectException(final IncorrectRequestException e, Model model) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getName(), e.getMessage());
        model.addAttribute("errorResponse", errorResponse);
        return "error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public String handleConflictException(final ConflictOnRequestException e, Model model) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getName(), e.getMessage());
        model.addAttribute("errorResponse", errorResponse);
        return "error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public String handleAnyOtherException(final Exception e, Model model) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getName(), e.getMessage());
        model.addAttribute("errorResponse", errorResponse);
        return "error";
    }
}
