package com.ort.app.api

import com.ort.app.api.request.*
import com.ort.app.api.request.validator.PlayerChangePasswordFormValidator
import com.ort.app.api.view.PlayerListContent
import com.ort.app.api.view.PlayerRecordsContent
import com.ort.app.application.coordinator.PlayerCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Controller
class PlayerController(
    private val playerService: PlayerService,
    private val playerCoordinator: PlayerCoordinator,
    private val charaService: CharaService,
    private val passwordEncoder: PasswordEncoder,
    private val playerChangePasswordFormValidator: PlayerChangePasswordFormValidator
) {

    companion object {
        private const val COOKIE_NAME_ID_REGISTER = "id_register"
        private val MAX_AGE_ID_REGISTER = 60 * 30 // 30分
        private val PATH_ID_REGISTER = "/wolf-mansion/"
    }

    @InitBinder("changePasswordForm")
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(playerChangePasswordFormValidator)
    }

    @GetMapping("/new-player")
    private fun index(form: PlayerCreateForm, model: Model): String {
        setIndexModel(form, model)
        return "new-player"
    }

    @GetMapping("/login")
    private fun index(form: LoginForm, model: Model): String {
        if (form.error == true) {
            model.addAttribute("errorMessage", "ユーザIDまたはパスワードが違います")
        }
        model.addAttribute("form", form.copy(password = null)) // パスワードは復元しない
        model.addAttribute("noAd", true)
        return "login"
    }

    // プレイヤー新規登録
    @PostMapping("/new-player")
    private fun createPlayer(
        @Validated @ModelAttribute("form") form: PlayerCreateForm, //
        result: BindingResult, //
        @CookieValue(name = COOKIE_NAME_ID_REGISTER, required = false) isRecentRegistered: Boolean?, //
        response: HttpServletResponse, //
        model: Model
    ): String {
        if (result.hasErrors()) {
            setIndexModel(form, model)
            return "new-player"
        }
        if (isRecentRegistered == true) {
            setIndexModel(form, model)
            model.addAttribute("errorMessage", "連続して複数のIDを取得することはできません。時間をおいてから再度取得してください。")
            return "new-player"
        }
        try {
            playerService.registerPlayer(form.userId!!, form.password!!)
        } catch (e: WolfMansionBusinessException) {
            setIndexModel(form, model)
            model.addAttribute("errorMessage", e.message)
            return "new-player"
        }
        registerCookie(response)
        return "redirect:/"
    }

    // パスワード変更
    @GetMapping("/change-password")
    private fun changePasswordIndex(model: Model): String {
        setChangePasswordIndexModel(model)
        return "change-password"
    }

    // パスワード変更
    @PostMapping("/change-password")
    private fun changePassword(
        @Validated @ModelAttribute("changePasswordForm") form: PlayerChangePasswordForm,  //
        result: BindingResult,  //
        model: Model
    ): String {
        val userInfo = WolfMansionUserInfoUtil.getUserInfo() ?: return "redirect:/"
        if (result.hasErrors()) {
            setChangePasswordIndexModel(model)
            return "change-password"
        }
        playerService.updatePassword(userInfo.username, form.password!!)
        return "redirect:/"
    }

    // ユーザー一覧
    @GetMapping("/user-list")
    private fun index(form: UserListForm, model: Model): String {
        val players = playerService.findAllPlayers(pageSize = 30, pageNum = form.pageNum ?: 1)
        val content = PlayerListContent(players)
        model.addAttribute("content", content)
        return "player-list"
    }

    // ユーザ情報
    @GetMapping("/user/{userName}")
    private fun user(@PathVariable userName: String, model: Model): String {
        model.addAttribute("userName", userName)
        val player = playerService.findPlayer(userName) ?: return "user"
        val playerRecords = playerCoordinator.findPlayerRecords(player)
        val originalCharachipVillages = playerRecords.participateVillageList.filter { it.village.setting.chara.isOriginalCharachip }
        val originalCharaIdList = originalCharachipVillages.map { it.participant.charaId }
        val originalCharas = charaService.findCharasByCharachipId(originalCharaIdList, true)
        val charachipVillages = playerRecords.participateVillageList.filterNot { it.village.setting.chara.isOriginalCharachip }
        val charaIdList = charachipVillages.map { it.participant.charaId }
        val charas = charaService.findCharasByCharachipId(charaIdList, false)

        val content = PlayerRecordsContent(playerRecords, charas, originalCharas, player.twitterUserName, player.introduction)
        model.addAttribute("content", content)

        val myName = WolfMansionUserInfoUtil.getUserInfo()?.username
        if (myName == userName) {
            model.addAttribute("userDetailForm", UserDetailForm(player.twitterUserName, player.introduction))
        }

        return "user"
    }

    @PostMapping("/user-detail")
    private fun userDetail(
        @Validated @ModelAttribute("userDetailForm") form: UserDetailForm,
        result: BindingResult
    ): String {
        val userInfo = WolfMansionUserInfoUtil.getUserInfo() ?: return "redirect:/"
        val username = userInfo.username
        if (result.hasErrors()) {
            return "redirect:/user/$username"
        }
        playerService.updatePlayerDetail(username, form.twitterUserName, form.introduction)
        return "redirect:/user/${userInfo.username}"
    }

    private fun setIndexModel(form: PlayerCreateForm, model: Model) {
        model.addAttribute("form", form.copy(password = null)) // パスワードは復元しない
        model.addAttribute("noAd", true) // 広告なし
    }

    private fun setChangePasswordIndexModel(model: Model) {
        model.addAttribute("changePasswordForm", PlayerChangePasswordForm()) // パスワードは復元しない
        model.addAttribute("noAd", true)
    }

    private fun registerCookie(response: HttpServletResponse) {
        val cookie = Cookie(COOKIE_NAME_ID_REGISTER, "true")
        cookie.maxAge = MAX_AGE_ID_REGISTER
        cookie.path = PATH_ID_REGISTER
        response.addCookie(cookie)
    }
}