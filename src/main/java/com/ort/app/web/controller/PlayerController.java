package com.ort.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.LoginForm;
import com.ort.app.web.form.PlayerCreateForm;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exentity.Player;

@Controller
public class PlayerController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private PlayerBhv playerBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @GetMapping("/new-player")
    private String index(PlayerCreateForm form, Model model) {
        setIndexModel(form, model);
        return "new-player";
    }

    @GetMapping("/login")
    private String index(LoginForm form, Model model) {
        model.addAttribute("form", form);
        return "login";
    }

    // プレイヤー新規登録
    @PostMapping("/new-player")
    private String createPlayer(@Validated @ModelAttribute("form") PlayerCreateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            setIndexModel(form, model);
            return "new-player";
        }

        try {
            selectPlayer(form);
        } catch (WerewolfMansionBusinessException e) {
            setIndexModel(form, model);
            model.addAttribute("errorMessage", e.getMessage());
            return "new-player";
        }
        insertPlayer(form);
        return "redirect:/";
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private void selectPlayer(PlayerCreateForm form) throws WerewolfMansionBusinessException {
        int count = playerBhv.selectCount(cb -> cb.query().setPlayerName_Equal(form.getUserId()));
        if (count > 0) {
            throw new WerewolfMansionBusinessException("既に登録されているIDです。");
        }
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void insertPlayer(PlayerCreateForm form) {
        Player player = new Player();
        player.setPlayerName(form.getUserId());
        String hashedPassword = BCrypt.hashpw(form.getPassword(), BCrypt.gensalt());
        player.setPlayerPassword(hashedPassword);
        player.setAuthorityCode_プレイヤー();
        playerBhv.insert(player);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void setIndexModel(PlayerCreateForm form, Model model) {
        model.addAttribute("form", form);
    }

}
