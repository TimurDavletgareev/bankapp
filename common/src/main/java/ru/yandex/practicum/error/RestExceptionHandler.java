package ru.yandex.practicum.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.error.exception.ConflictOnRequestException;
import ru.yandex.practicum.error.exception.IncorrectRequestException;
import ru.yandex.practicum.error.exception.NotFoundException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorResponse handleNotFoundException(final NotFoundException e) {

        log.error(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorResponse handleIncorrectException(final IncorrectRequestException e) {

        log.error(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public ErrorResponse handleConflictException(final ConflictOnRequestException e) {

        log.error(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT) // 500
    public ErrorResponse handleAnyOtherException(final Exception e) {

        log.error(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }
}
