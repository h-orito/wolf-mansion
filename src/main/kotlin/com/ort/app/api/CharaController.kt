package com.ort.app.api

import com.ort.app.api.view.CharaGroupContent
import com.ort.app.api.view.CharaGroupListContent
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageApplicationService
import com.ort.app.domain.model.chara.Chara
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
    private val villageService: VillageApplicationService
) {

    @GetMapping("/chara-group")
    private fun charaGroups(model: Model): String {
        val charachips = charaService.findCharachips()
        val content = CharaGroupListContent(charachips)
        model.addAttribute("content", content)
        return "chara-list"
    }

    @GetMapping("/chara-group/{charaGroupId}")
    private fun charaGroup(@PathVariable charaGroupId: Int, model: Model): String {
        val charachip = charaService.findCharachip(charaGroupId)
            ?: throw WolfMansionBusinessException("charachip not found. $charaGroupId")
        val content = CharaGroupContent(charachip)
        model.addAttribute("content", content)
        return "chara"
    }

    @GetMapping("/getCharacterList/{charaGroupId}")
    @ResponseBody
    private fun getCharacterList(@PathVariable charaGroupId: Int): List<Chara> {
        val charas = charaService.findCharas(charaGroupId)
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
        val chara = charaService.findChara(participant.charaId) ?: return null
        return chara.images.list.firstOrNull { it.faceType.code == faceTypeCode }?.url
    }
}