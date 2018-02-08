package com.ort.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @GetMapping("/about")
    private String about(Model model) {
        return "about";
    }

    @GetMapping("/announce")
    private String announce(Model model) {
        return "announce";
    }

    @GetMapping("/rule")
    private String rule(Model model) {
        return "rule";
    }
}
