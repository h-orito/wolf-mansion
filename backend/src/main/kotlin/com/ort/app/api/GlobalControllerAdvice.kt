package com.ort.app.api

import com.ort.app.fw.exception.WolfMansionBadRequestException
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.InitBinder


@ControllerAdvice
@Order(10000)
class GlobalControllerAdvice {

    @InitBinder
    fun setAllowedFields(dataBinder: WebDataBinder) {
        dataBinder.setDisallowedFields("class.*", "Class.*", "*.class.*", "*.Class.*")
    }

    @ExceptionHandler(WolfMansionBusinessException::class, WolfMansionBadRequestException::class)
    fun handleWolfMansionException(ex: WolfMansionBusinessException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }
}