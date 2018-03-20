package com.ort.fw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ExceptionControllerAdvice {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        logger.error(e.getMessage(), e);
        return "error";
    }

    @ModelAttribute
    public void modelAttribute() {
    }
}
