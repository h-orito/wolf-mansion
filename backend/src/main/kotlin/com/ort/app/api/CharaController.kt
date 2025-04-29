package com.ort.app.api

import com.ort.app.api.request.CharacterListForm
import com.ort.app.api.view.CharaGroupContent
import com.ort.app.api.view.CharaGroupListContent
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class CharaController(
    private val charaService: CharaService,
    private val villageService: VillageService
) {

    @GetMapping("/chara-group")
    private fun charaGroups(model: Model): String {
        val charachips = charaService.findCharachips()
        val content = CharaGroupListContent(charachips)
        model.addAttribute("content", content)
        return "chara-list"
    }

    @GetMapping("/api/chara-groups")
    @ResponseBody
    private fun apiCharaGroups(): Charachips {
        return charaService.findCharachips()
    }

    @GetMapping("/chara-group/{charaGroupId}")
    private fun charaGroup(@PathVariable charaGroupId: Int, model: Model): String {
        val charachip = charaService.findCharachip(charaGroupId, false)
            ?: throw WolfMansionBusinessException("charachip not found. $charaGroupId")
        val content = CharaGroupContent(charachip)
        model.addAttribute("content", content)
        return "chara"
    }

    @GetMapping("/api/chara-group/{charaGroupId}")
    @ResponseBody
    private fun apiCharaGroup(@PathVariable charaGroupId: Int): Charachip {
        return charaService.findCharachip(charaGroupId, false)
            ?: throw WolfMansionBusinessException("charachip not found. $charaGroupId")
    }

    @GetMapping("/getCharacterList")
    @ResponseBody
    private fun getCharacterList(form: CharacterListForm): List<Chara> {
        val charas = charaService.findCharachips(form.charaGroupId!!, false).charas()
        return charas.list.map {
            it.copy(
                images = it.images.copy(
                    list = it.images.list.filter { it.faceType.toCdef() == CDef.FaceType.通常 }
                )
            )
        }
    }

    @GetMapping("/getFaceImgUrl/{villageId}/{faceTypeCode}")
    @ResponseBody
    private fun getFaceImgUrl(@PathVariable villageId: Int, @PathVariable faceTypeCode: String): String? {
        val userInfo = WolfMansionUserInfoUtil.getUserInfo() ?: return null
        val participant = villageService.findVillageParticipant(villageId, userInfo.username) ?: return null
        val village = villageService.findVillage(villageId) ?: return null
        val chara =
            charaService.findChara(participant.charaId, village.setting.chara.isOriginalCharachip) ?: return null
        return chara.images.list.firstOrNull { it.faceType.code == faceTypeCode }?.url
    }
}