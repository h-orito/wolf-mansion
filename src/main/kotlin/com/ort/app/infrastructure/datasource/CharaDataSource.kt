package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.chara.*
import com.ort.app.domain.model.chara.Chara
import com.ort.dbflute.bsbhv.loader.LoaderOfChara
import com.ort.dbflute.cbean.CharaImageCB
import com.ort.dbflute.exbhv.*
import com.ort.dbflute.exentity.CharaGroup
import com.ort.dbflute.exentity.OriginalChara
import com.ort.dbflute.exentity.OriginalCharaGroup
import com.ort.dbflute.exentity.OriginalCharaImage
import org.dbflute.cbean.result.ListResultBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import com.ort.dbflute.exentity.Chara as DbChara


@Repository
class CharaDataSource(
    private val charaGroupBhv: CharaGroupBhv,
    private val charaBhv: CharaBhv,
    private val originalCharaGroupBhv: OriginalCharaGroupBhv,
    private val originalCharaBhv: OriginalCharaBhv,
    private val originalCharaImageBhv: OriginalCharaImageBhv
) : CharaRepository {

    @Value("\${app.original-image.basedir}")
    private lateinit var basedir: String

    @Value("\${app.original-image.baseurl}")
    private lateinit var baseurl: String

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

    override fun findCharachips(ids: List<Int>, isOriginal: Boolean): Charachips {
        if (ids.isEmpty()) return Charachips(emptyList())
        return if (isOriginal) mapOriginalCharachips(selectOriginalCharaGroups(ids))
        else mapCharachips(selectCharaGroups(ids))
    }

    override fun findCharachip(id: Int, isOriginal: Boolean): Charachip? {
        return if (isOriginal) selectOriginalCharaGroup(id)?.let { mapOriginalCharachip(it) }
        else selectCharaGroup(id)?.let { mapCharachip(it) }
    }

    override fun findCharas(charachipId: Int, isOriginal: Boolean): Charas {
        return if (isOriginal) mapOriginalCharas(selectOriginalCharas(charachipId))
        else mapCharas(selectCharas(charachipId))
    }

    override fun findCharasByCharaIdList(idList: List<Int>, isOriginal: Boolean): Charas {
        if (idList.isEmpty()) return Charas(emptyList())
        return if (isOriginal) mapOriginalCharas(selectOriginalCharas(idList))
        else mapCharas(selectCharas(idList))
    }

    override fun findChara(id: Int, isOriginal: Boolean): Chara? {
        return if (isOriginal) selectOriginalChara(id)?.let { mapOriginalChara(it) }
        else selectChara(id)?.let { mapChara(it) }
    }

    override fun registerOriginalCharachip(name: String): Charachip {
        val id = insertOriginalCharaGroup(name)
        return findCharachip(id, true)!!
    }

    override fun registerOriginalChara(
        charachipId: Int,
        name: String,
        shortName: String,
        faceTypeName: String,
        charaImage: MultipartFile
    ): Chara {
        val charaId = insertOriginalChara(charachipId, name, shortName)
        val charaImageId = insertOriginalCharaImage(charaId, faceTypeName)
        val url = uploadCharaImage(charachipId, charaId, charaImageId, charaImage)
        updateOriginalCharaImageUrl(charaImageId, url)
        return findChara(charaId, true)!!
    }

    override fun registerOriginalCharaImage(
        charachipId: Int,
        charaId: Int,
        faceTypeName: String,
        charaImage: MultipartFile
    ) {
        val charaImageId = insertOriginalCharaImage(charaId, faceTypeName)
        val url = uploadCharaImage(charachipId, charaId, charaImageId, charaImage)
        updateOriginalCharaImageUrl(charaImageId, url)
    }

    private fun selectOriginalCharaGroups(ids: List<Int>): ListResultBean<OriginalCharaGroup> {
        val groupList = originalCharaGroupBhv.selectList {
            it.query().setOriginalCharaGroupId_InScope(ids)
            it.query().addOrderBy_OriginalCharaGroupId_Asc()
        }
        originalCharaGroupBhv.load(groupList) { loader ->
            loader.loadOriginalChara {
                it.query().addOrderBy_OriginalCharaId_Asc()
            }.withNestedReferrer {
                it.loadOriginalCharaImage { ociCB ->
                    ociCB.query().addOrderBy_OriginalCharaImageId_Asc()
                }
            }
        }
        return groupList
    }

    private fun selectCharaGroups(ids: List<Int>): ListResultBean<CharaGroup> {
        val groupList = charaGroupBhv.selectList {
            it.setupSelect_Designer()
            it.query().setCharaGroupId_InScope(ids)
            it.query().addOrderBy_CharaGroupId_Asc()
        }
        charaGroupBhv.load(groupList) { loader ->
            loader.loadChara {
                it.query().addOrderBy_DefaultJoinMessage_Asc().withNullsLast()
            }.withNestedReferrer { charaLoader ->
                charaLoader.loadCharaImage {}
            }
        }
        return groupList
    }

    private fun selectCharaGroup(id: Int): CharaGroup? {
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
        return group
    }

    private fun selectOriginalCharaGroup(id: Int): OriginalCharaGroup? {
        val optGroup = originalCharaGroupBhv.selectEntity {
            it.query().setOriginalCharaGroupId_Equal(id)
        }
        if (!optGroup.isPresent) return null
        val group = optGroup.get()
        originalCharaGroupBhv.load(group) { loader ->
            loader.loadOriginalChara {
                it.query().addOrderBy_OriginalCharaId_Asc()
            }.withNestedReferrer { charaLoader ->
                charaLoader.loadOriginalCharaImage {
                    it.query().addOrderBy_OriginalCharaImageId_Asc()
                }
            }
        }
        return group
    }

    private fun selectCharas(charachipId: Int): ListResultBean<com.ort.dbflute.exentity.Chara> {
        val charaList = charaBhv.selectList {
            it.query().setCharaGroupId_Equal(charachipId)
            it.query().addOrderBy_DefaultJoinMessage_Desc()
            it.query().addOrderBy_CharaId_Asc()
        }
        charaBhv.load(charaList) {
            it.loadCharaImage { charaImageCB ->
                charaImageCB.query().queryFaceType().addOrderBy_DispOrder_Asc()
            }
        }
        return charaList
    }

    private fun selectCharas(ids: List<Int>): ListResultBean<com.ort.dbflute.exentity.Chara> {
        val charaList = charaBhv.selectList {
            it.query().setCharaId_InScope(ids)
            it.query().addOrderBy_CharaId_Asc()
        }
        charaBhv.load(charaList) {
            it.loadCharaImage { charaImageCB ->
                charaImageCB.query().queryFaceType().addOrderBy_DispOrder_Asc()
            }
        }
        return charaList
    }

    private fun selectOriginalCharas(charachipId: Int): ListResultBean<OriginalChara> {
        val charaList = originalCharaBhv.selectList {
            it.query().setOriginalCharaGroupId_Equal(charachipId)
            it.query().addOrderBy_OriginalCharaId_Asc()
        }
        originalCharaBhv.load(charaList) {
            it.loadOriginalCharaImage { cb ->
                cb.query().addOrderBy_OriginalCharaImageId_Asc()
            }
        }
        return charaList
    }

    private fun selectOriginalCharas(ids: List<Int>): ListResultBean<OriginalChara> {
        val charaList = originalCharaBhv.selectList {
            it.query().setOriginalCharaId_InScope(ids)
            it.query().addOrderBy_OriginalCharaId_Asc()
        }
        originalCharaBhv.load(charaList) {
            it.loadOriginalCharaImage { cb ->
                cb.query().addOrderBy_OriginalCharaImageId_Asc()
            }
        }
        return charaList
    }

    private fun selectChara(id: Int): com.ort.dbflute.exentity.Chara? {
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
        return chara
    }

    private fun selectOriginalChara(id: Int): OriginalChara? {
        val optChara = originalCharaBhv.selectEntity {
            it.query().setOriginalCharaId_Equal(id)
        }
        if (!optChara.isPresent) return null
        val chara = optChara.get()
        originalCharaBhv.load(chara) {
            it.loadOriginalCharaImage { charaImageCB ->
                charaImageCB.query().addOrderBy_OriginalCharaImageId_Asc()
            }
        }
        return chara
    }

    private fun insertOriginalCharaGroup(name: String): Int {
        val c = OriginalCharaGroup()
        c.originalCharaGroupName = name
        originalCharaGroupBhv.insert(c)
        return c.originalCharaGroupId
    }

    private fun insertOriginalChara(charachipId: Int, name: String, shortName: String): Int {
        val c = OriginalChara()
        c.originalCharaName = name
        c.originalCharaShortName = shortName
        c.displayWidth = 60
        c.displayHeight = 60
        c.originalCharaGroupId = charachipId
        originalCharaBhv.insert(c)
        return c.originalCharaId
    }

    private fun insertOriginalCharaImage(charaId: Int, faceTypeName: String): Int {
        val ci = OriginalCharaImage()
        ci.originalCharaId = charaId
        ci.charaImgUrl = "https://placehold.jp/60x60.png"
        ci.faceTypeName = faceTypeName
        ci.isDisplay = true
        originalCharaImageBhv.insert(ci)
        return ci.originalCharaImageId
    }

    private fun updateOriginalCharaImageUrl(charaImageId: Int, url: String) {
        val ci = OriginalCharaImage()
        ci.originalCharaImageId = charaImageId
        ci.charaImgUrl = url
        originalCharaImageBhv.update(ci)
    }

    private fun uploadCharaImage(
        charachipId: Int,
        charaId: Int,
        charaImageId: Int,
        charaImage: MultipartFile
    ): String {
        // フォルダ作成
        val dir = File("$basedir/$charachipId")
        dir.mkdir()
        // 画像
        val ext = charaImage.originalFilename.let { it!!.substring(it.lastIndexOf('.')) }
        val filename = "${charaId}_${charaImageId}$ext"
        uploadFile(dir, charaImage, filename)
        return "$baseurl/$charachipId/$filename"
    }

    private fun uploadFile(dir: File, file: MultipartFile, name: String) {
        val uploadFile = File("${dir.path}/$name")
        FileOutputStream(uploadFile).use { out ->
            BufferedOutputStream(out).use { it.write(file.bytes) }
        }
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
                        url = charaImage.charaImgUrl,
                        isDisplay = true
                    )
                }
            )
        )
    }

    private fun mapOriginalCharachips(groupList: ListResultBean<OriginalCharaGroup>): Charachips {
        return Charachips(
            list = groupList.map { mapOriginalCharachip(it) }
        )
    }

    private fun mapOriginalCharachip(group: OriginalCharaGroup): Charachip {
        return Charachip(
            id = group.originalCharaGroupId,
            name = group.originalCharaGroupName,
            designer = null,
            descriptionUrl = null,
            isAvailableChangeName = true,
            charas = mapOriginalCharas(group.originalCharaList)
        )
    }

    private fun mapOriginalCharas(charaList: List<OriginalChara>): Charas {
        return Charas(
            list = charaList.map { chara -> mapOriginalChara(chara) }
        )
    }

    private fun mapOriginalChara(chara: OriginalChara): Chara {
        return Chara(
            id = chara.originalCharaId,
            name = chara.originalCharaName,
            shortName = chara.originalCharaShortName,
            defaultJoinMessage = null,
            defaultFirstdayMessage = null,
            size = CharaSize(
                width = chara.displayWidth,
                height = chara.displayHeight
            ),
            images = CharaImages(
                list = chara.originalCharaImageList.map { charaImage ->
                    CharaImage(
                        faceType = FaceType(
                            code = charaImage.originalCharaImageId.toString(),
                            name = charaImage.faceTypeName
                        ),
                        url = charaImage.charaImgUrl,
                        isDisplay = charaImage.isDisplay
                    )
                }
            )
        )
    }
}