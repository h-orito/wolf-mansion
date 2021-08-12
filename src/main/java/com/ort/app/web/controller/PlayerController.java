package com.ort.app.web.controller;

import com.ort.app.web.controller.assist.PlayerAssist;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.LoginForm;
import com.ort.app.web.form.PlayerChangePasswordForm;
import com.ort.app.web.form.PlayerCreateForm;
import com.ort.app.web.form.validator.PlayerChangePasswordFormValidator;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exentity.Player;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PlayerController {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final String COOKIE_NAME_ID_REGISTER = "id_register";
    private static final int MAX_AGE_ID_REGISTER = 60 * 30; // 30分
    private static final String PATH_ID_REGISTER = "/wolf-mansion/";

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private PlayerBhv playerBhv;

    @Autowired
    private PlayerAssist playerAssist;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PlayerChangePasswordFormValidator playerChangePasswordFormValidator;

    @InitBinder("changePasswordForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(playerChangePasswordFormValidator);
    }

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
        if (BooleanUtils.isTrue(form.getError())) {
            model.addAttribute("errorMessage", "ユーザIDまたはパスワードが違います");
        }
        if (form != null) {
            form.setPassword(null); // パスワードは復元しない
        }
        model.addAttribute("form", form);
        model.addAttribute("noAd", true);
        return "login";
    }

    // プレイヤー新規登録
    @PostMapping("/new-player")
    private String createPlayer(
            @Validated @ModelAttribute("form") PlayerCreateForm form, //
            BindingResult result, //
            @CookieValue(name = COOKIE_NAME_ID_REGISTER, required = false) Boolean isRecentRegistered, //
            HttpServletResponse response, //
            Model model
    ) {
        if (result.hasErrors()) {
            setIndexModel(form, model);
            return "new-player";
        }

        if (BooleanUtils.isTrue(isRecentRegistered)) {
            setIndexModel(form, model);
            model.addAttribute("errorMessage", "連続して複数のIDを取得することはできません。時間をおいてから再度取得してください。");
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
        registerCookie(response);
        return "redirect:/";
    }

    // パスワード変更
    @GetMapping("/change-password")
    private String changePasswordIndex(PlayerChangePasswordForm form, BindingResult result, Model model) {
        setChangePasswordIndexModel(form, model);
        return "change-password";
    }

    // パスワード変更
    @PostMapping("/change-password")
    private String changePassword(
            @Validated @ModelAttribute("changePasswordForm") PlayerChangePasswordForm form, //
            BindingResult result, //
            Model model
    ) {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            return "redirect:/";
        }
        if (result.hasErrors()) {
            setChangePasswordIndexModel(form, model);
            return "change-password";
        }
        updatePlayerPassword(form, userInfo);
        return "redirect:/";
    }

    // ユーザ情報
    @GetMapping("/user/{userName}")
    private String user(@PathVariable String userName, Model model) {
        playerAssist.setPlayerIndexModel(userName, model);
        return "user";
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
        player.setIsRestrictedParticipation_False();
        playerBhv.insert(player);
    }

    private void updatePassword(String password, UserInfo userInfo) {
        Player player = new Player();
        player.setPlayerPassword(password);
        playerBhv.queryUpdate(player, cb -> cb.query().setPlayerName_Equal(userInfo.getUsername()));
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void setIndexModel(PlayerCreateForm form, Model model) {
        if (form != null) {
            form.setPassword(null); // パスワードは復元しない
        }
        model.addAttribute("form", form);
        model.addAttribute("noAd", true); // 広告なし
    }

    private void setChangePasswordIndexModel(PlayerChangePasswordForm form, Model model) {
        model.addAttribute("changePasswordForm", new PlayerChangePasswordForm()); // パスワードは復元しない
        model.addAttribute("noAd", true);
    }

    private void updatePlayerPassword(PlayerChangePasswordForm form, UserInfo userInfo) {
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        updatePassword(encodedPassword, userInfo);
        userInfo.setPassword(encodedPassword);
    }

    private void registerCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME_ID_REGISTER, "true");
        cookie.setMaxAge(MAX_AGE_ID_REGISTER);
        cookie.setPath(PATH_ID_REGISTER);
        response.addCookie(cookie);
    }
}
