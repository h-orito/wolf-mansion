package com.ort.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.ort.app.web.form.LoginForm;

@Controller
public class LoginController {

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @PostMapping("/login")
    private String login(LoginForm form) {
        return "index";
    }
}
