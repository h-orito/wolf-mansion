package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.CharaImage
import com.ort.app.domain.model.chara.CharaImages
import com.ort.app.domain.model.chara.CharaRepository
import com.ort.app.domain.model.chara.CharaSize
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.chara.Designer
import com.ort.app.domain.model.chara.FaceType
import com.ort.dbflute.bsbhv.loader.LoaderOfChara
import com.ort.dbflute.cbean.CharaImageCB
import com.ort.dbflute.exbhv.CharaBhv
import com.ort.dbflute.exbhv.CharaGroupBhv
import com.ort.dbflute.exentity.CharaGroup
import org.dbflute.cbean.result.ListResultBean
import org.springframework.stereotype.Repository
import com.ort.dbflute.exentity.Chara as DbChara

@Repository
class CharaDataSource(
    private val charaGroupBhv: CharaGroupBhv,
    private val charaBhv: CharaBhv
) : CharaRepository {

    // 表情は通常のみ
    override fun findCharachips(): Charachips {
        val groupList = charaGroupBhv.selectList {
            it.setupSelect_Designer()
            it.query().addOrderBy_CharaGroupId_Asc()
        }
        charaGroupBhv.load(groupList) { loader ->
            loader.loadChara {
                it.query().addOrderBy_DefaultJoinMessage_Asc().withNullsLast()
            }.withNestedReferrer { charaLoader ->
                charaLoader.loadCharaImage {
                    it.query().setFaceTypeCode_Equal_通常()
                }
            }
        }
        return mapCharachips(groupList)
    }

    override fun findCharachip(id: Int): Charachip? {
        val optGroup = charaGroupBhv.selectEntity {
            it.setupSelect_Designer()
            it.query().setCharaGroupId_Equal(id)
        }
        if (!optGroup.isPresent) return null
        val group = optGroup.get()
        charaGroupBhv.load(group) { loader ->
            loader.loadChara {
                it.query().addOrderBy_DefaultJoinMessage_Desc()
                it.query().addOrderBy_CharaId_Asc()
            }.withNestedReferrer { charaLoader: LoaderOfChara ->
                charaLoader.loadCharaImage { charaImageCB: CharaImageCB ->
                    charaImageCB.query().queryFaceType().addOrderBy_DispOrder_Asc()
                }
            }
        }
        return mapCharachip(group)
    }

    override fun findCharas(id: Int): Charas {
        val charaList = charaBhv.selectList {
            it.query().setCharaGroupId_Equal(id)
            it.query().addOrderBy_CharaId_Asc()
        }
        charaBhv.load(charaList) {
            it.loadCharaImage { charaImageCB ->
                charaImageCB.query().queryFaceType().addOrderBy_DispOrder_Asc()
            }
        }
        return mapCharas(charaList)
    }

    override fun findChara(id: Int): Chara? {
        val optChara = charaBhv.selectEntity {
            it.query().setCharaId_Equal(id)
        }
        if (!optChara.isPresent) return null
        val chara = optChara.get()
        charaBhv.load(chara) {
            it.loadCharaImage { charaImageCB ->
                charaImageCB.query().queryFaceType().addOrderBy_DispOrder_Asc()
            }
        }
        return mapChara(chara)
    }

    private fun mapCharachips(groupList: ListResultBean<CharaGroup>): Charachips {
        return Charachips(
            list = groupList.map { mapCharachip(it) }
        )
    }

    private fun mapCharachip(group: CharaGroup): Charachip {
        return Charachip(
            id = group.charaGroupId,
            name = group.charaGroupName,
            designer = group.designer.get().let { designer ->
                Designer(
                    id = designer.designerId,
                    name = designer.designerName
                )
            },
            descriptionUrl = group.descriptionUrl,
            isAvailableChangeName = group.isAvailableChangeName,
            charas = mapCharas(group.charaList)
        )
    }

    private fun mapCharas(charaList: List<DbChara>): Charas {
        return Charas(
            list = charaList.map { chara -> mapChara(chara) }
        )
    }

    private fun mapChara(chara: DbChara): Chara {
        return Chara(
            id = chara.charaId,
            name = chara.charaName,
            shortName = chara.charaShortName,
            defaultJoinMessage = chara.defaultJoinMessage,
            defaultFirstdayMessage = chara.defaultFirstdayMessage,
            size = CharaSize(
                width = chara.displayWidth,
                height = chara.displayHeight
            ),
            images = CharaImages(
                list = chara.charaImageList.map { charaImage ->
                    CharaImage(
                        faceType = FaceType(charaImage.faceTypeCodeAsFaceType),
                        url = charaImage.charaImgUrl
                    )
                }
            )
        )
    }
}