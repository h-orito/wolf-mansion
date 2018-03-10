package com.ort.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @GetMapping("/error")
    private String error(Model model) {
        return "error";
    }
}
