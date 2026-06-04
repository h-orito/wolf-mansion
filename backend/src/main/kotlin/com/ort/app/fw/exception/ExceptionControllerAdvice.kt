package com.ort.app.fw.exception

import org.slf4j.LoggerFactory
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ModelAttribute

class ExceptionControllerAdvice {

    private val logger = LoggerFactory.getLogger(ExceptionControllerAdvice::class.java)

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
    }

    @ExceptionHandler(Exception::class)
    fun exception(e: Exception): String {
        logger.error(e.message, e)
        return "error"
    }

    @ModelAttribute
    fun modelAttribute() {
    }
}