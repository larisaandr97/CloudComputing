package com.javaproject.storeapp.exception.advice;


import com.javaproject.storeapp.dto.BankAccountRequest;
import com.javaproject.storeapp.entity.User;
import com.javaproject.storeapp.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ModelAndView handlerArgumentMismatchException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("error_default");
        return modelAndView;
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ModelAndView handlerMethodNotSupportedException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("error_default");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public ModelAndView handlerNotFoundException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("notfound");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserAlreadyExistException.class})
    public ModelAndView handlerUserExistsException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("userRequest", new User());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DuplicateCardNumberException.class})
    public ModelAndView handlerCardExistsException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView("addBankAccount");
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("bankAccountRequest", new BankAccountRequest());
        return modelAndView;
    }

    @ExceptionHandler({ProductNotInStockException.class})
    public ModelAndView handlerNotEnoughStockException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    @ExceptionHandler({NegativeQuantityException.class})
    public ModelAndView handlerNegativeQuantityException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView("notfound");
        modelAndView.addObject("exception", exception);
        // modelAndView.addObject("bankAccountRequest", new BankAccountRequest());
        return modelAndView;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body("Invalid value " + Objects.requireNonNull(e.getFieldError()).getRejectedValue()
                        + " for field " + e.getFieldError().getField()
                        + " with message: " + e.getFieldError().getDefaultMessage());

    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File too large!");
    }
}
