package com.ort.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @GetMapping("/about")
    private String about() {
        return "about";
    }

    @GetMapping("/announce")
    private String announce() {
        return "announce";
    }

    @GetMapping("/rule")
    private String rule() {
        return "rule";
    }
}
