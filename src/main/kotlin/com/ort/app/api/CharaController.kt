package com.ort.app.api

import com.ort.app.web.model.CharaGroupListResultContent
import com.ort.app.web.model.CharaGroupResultContent
import com.ort.app.web.model.GroupCharaDto
import com.ort.app.web.model.inner.CharaGroupCharaDto
import com.ort.app.web.model.inner.CharaGroupListCharaGroupDto
import com.ort.dbflute.allcommon.CDef
import com.ort.dbflute.bsbhv.loader.LoaderOfChara
import com.ort.dbflute.bsbhv.loader.LoaderOfCharaGroup
import com.ort.dbflute.cbean.CharaCB
import com.ort.dbflute.cbean.CharaGroupCB
import com.ort.dbflute.cbean.CharaImageCB
import com.ort.dbflute.cbean.VillagePlayerCB
import com.ort.dbflute.exbhv.CharaBhv
import com.ort.dbflute.exbhv.CharaGroupBhv
import com.ort.dbflute.exbhv.CharaImageBhv
import com.ort.dbflute.exbhv.VillagePlayerBhv
import com.ort.dbflute.exentity.Chara
import com.ort.dbflute.exentity.CharaGroup
import com.ort.dbflute.exentity.CharaImage
import com.ort.dbflute.exentity.VillagePlayer
import com.ort.fw.security.UserInfo
import com.ort.fw.util.WolfMansionUserInfoUtil
import org.dbflute.cbean.result.ListResultBean
import org.dbflute.optional.OptionalEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import java.util.stream.Collectors

@Controller
class CharaController(
    private val charaGroupBhv: CharaGroupBhv,
    private val charaBhv: CharaBhv,
    private val charaImageBhv: CharaImageBhv,
    private val villagePlayerBhv: VillagePlayerBhv
) {

    @GetMapping("/chara-group")
    private fun charaIndex(model: Model): String {
        val charaGroupList = selectCharaGroupList()
        val content = mappingToContent(charaGroupList)
        model.addAttribute("content", content)
        return "chara-list"
    }

    @GetMapping("/chara-group/{charaGroupId}")
    private fun charaDetailIndex(@PathVariable charaGroupId: Int, model: Model): String {
        val charaGroup = selectCharaGroup(charaGroupId)
        val content = mappingToDetailContent(charaGroup)
        model.addAttribute("content", content)
        return "chara"
    }

    @GetMapping("/getCharacterList/{charaGroupId}")
    @ResponseBody
    private fun getCharacterList(@PathVariable charaGroupId: Int): List<GroupCharaDto> {
        val charaList = charaBhv.selectList { cb: CharaCB ->
            cb.query().setCharaGroupId_Equal(charaGroupId)
            cb.query().addOrderBy_DefaultJoinMessage_Desc().withNullsLast()
            cb.query().addOrderBy_CharaId_Asc()
        }
        charaBhv.loadCharaImage(
            charaList
        ) { charaImgCB: CharaImageCB ->
            charaImgCB.query().setFaceTypeCode_Equal_通常()
        }
        return charaList.stream().map { chara: Chara ->
            val groupChara = GroupCharaDto()
            groupChara.charaId = chara.charaId
            groupChara.charaName = chara.charaName
            groupChara.charaImgHeight = chara.displayHeight
            groupChara.charaImgWidth = chara.displayWidth
            groupChara.joinMessage = chara.defaultJoinMessage
            groupChara.firstDayMessage = chara.defaultFirstdayMessage
            groupChara.charaImgUrl = chara.charaImageList[0].charaImgUrl
            groupChara
        }.collect(Collectors.toList())
    }

    @GetMapping("/getFaceImgUrl/{villageId}/{faceTypeCode}")
    @ResponseBody
    private fun getFaceImgUrl(@PathVariable villageId: Int, @PathVariable faceTypeCode: String): String? {
        val userInfo = WolfMansionUserInfoUtil.getUserInfo()
        val optVillagePlayer = selectVillagePlayer(villageId, userInfo)
        if (!optVillagePlayer.isPresent) {
            return null
        }
        val faceType = CDef.FaceType.codeOf(faceTypeCode) ?: return null
        val optImage = charaImageBhv.selectEntity { cb: CharaImageCB ->
            cb.query().setCharaId_Equal(optVillagePlayer.get().charaId)
            cb.query().setFaceTypeCode_Equal_AsFaceType(faceType)
        }
        return optImage.map { im: CharaImage -> im.charaImgUrl }.orElse(null)
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private fun mappingToContent(charaGroupList: ListResultBean<CharaGroup>): CharaGroupListResultContent {
        val content = CharaGroupListResultContent()
        content.charaGroupList =
            charaGroupList.stream().map { charaGroup: CharaGroup ->
                convertToCharaGroupDto(
                    charaGroup
                )
            }.collect(Collectors.toList())
        return content
    }

    private fun convertToCharaGroupDto(charaGroup: CharaGroup): CharaGroupListCharaGroupDto {
        val part = CharaGroupListCharaGroupDto()
        part.charaGroupId = charaGroup.charaGroupId
        part.charaGroupName = charaGroup.charaGroupName
        part.designerName = charaGroup.designer.get().designerName
        part.charaNum = charaGroup.charaList.size
        part.dummyImgUrl = charaGroup.charaList[0].charaImageList[0].charaImgUrl
        part.dummyImgWidth = charaGroup.charaList[0].displayWidth
        part.dummyImgHeight = charaGroup.charaList[0].displayHeight
        return part
    }

    private fun mappingToDetailContent(charaGroup: CharaGroup): CharaGroupResultContent {
        val content = CharaGroupResultContent()
        content.charaGroupId = charaGroup.charaGroupId
        content.charaGroupName = charaGroup.charaGroupName
        content.designerName = charaGroup.designer.get().designerName
        content.descriptionUrl = charaGroup.descriptionUrl
        content.charaList =
            charaGroup.charaList.stream().map { chara: Chara ->
                convertToCharaDto(
                    chara
                )
            }.collect(Collectors.toList())
        content.isAvailableChangeName = charaGroup.isIsAvailableChangeNameTrue
        return content
    }

    private fun convertToCharaDto(chara: Chara): CharaGroupCharaDto {
        val part = CharaGroupCharaDto()
        part.charaId = chara.charaId
        part.charaName = chara.charaName
        part.charaShortName = chara.charaShortName
        part.charaImgUrlList =
            chara.charaImageList.stream().map { charaImg: CharaImage -> charaImg.charaImgUrl }
                .collect(Collectors.toList())
        part.charaImgWidth = chara.displayWidth
        part.charaImgHeight = chara.displayHeight
        return part
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private fun selectCharaGroupList(): ListResultBean<CharaGroup> {
        val charaGroupList = charaGroupBhv.selectList { cb: CharaGroupCB ->
            cb.setupSelect_Designer()
            cb.query().addOrderBy_CharaGroupId_Asc()
        }
        charaGroupBhv.load(
            charaGroupList
        ) { loader: LoaderOfCharaGroup ->
            loader.loadChara { charaCB: CharaCB ->
                charaCB.query().addOrderBy_DefaultJoinMessage_Asc().withNullsLast()
            }.withNestedReferrer { charaLoader: LoaderOfChara ->
                charaLoader.loadCharaImage { charaImageCB: CharaImageCB ->
                    charaImageCB.query().setFaceTypeCode_Equal_通常()
                }
            }
        }
        return charaGroupList
    }

    private fun selectCharaGroup(charaGroupId: Int): CharaGroup {
        val charaGroup = charaGroupBhv.selectEntityWithDeletedCheck { cb: CharaGroupCB ->
            cb.setupSelect_Designer()
            cb.query().setCharaGroupId_Equal(charaGroupId)
        }
        charaGroupBhv.load(
            charaGroup
        ) { loader: LoaderOfCharaGroup ->
            loader.loadChara { charaCB: CharaCB ->
                charaCB.query().addOrderBy_DefaultJoinMessage_Desc()
                charaCB.query().addOrderBy_CharaId_Asc()
            }.withNestedReferrer { charaLoader: LoaderOfChara ->
                charaLoader.loadCharaImage { charaImageCB: CharaImageCB ->
                    charaImageCB.query().queryFaceType().addOrderBy_DispOrder_Asc()
                }
            }
        }
        return charaGroup
    }

    private fun selectVillagePlayer(villageId: Int, userInfo: UserInfo?): OptionalEntity<VillagePlayer> {
        return if (userInfo == null) {
            OptionalEntity.empty()
        } else villagePlayerBhv.selectEntity { cb: VillagePlayerCB ->
            cb.setupSelect_Player()
            cb.setupSelect_SkillBySkillCode()
            cb.query().setVillageId_Equal(villageId)
            cb.query().setIsGone_Equal_False()
            cb.query().queryPlayer().setPlayerName_Equal(userInfo.username)
        }
    }
}