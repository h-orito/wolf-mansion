package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import org.dbflute.optional.OptionalEntity;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.*;

/**
 * The entity of SKILL as TABLE. <br>
 * 役職
 * <pre>
 * [primary-key]
 *     SKILL_CODE
 *
 * [column]
 *     SKILL_CODE, SKILL_NAME, SKILL_SHORT_NAME, CAMP_CODE, DISP_ORDER
 *
 * [sequence]
 *     
 *
 * [identity]
 *     
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     CAMP
 *
 * [referrer table]
 *     NORMAL_SAY_RESTRICTION, SKILL_ALLOCATION, VILLAGE_PLAYER, VILLAGE_PLAYER_SKILL_HISTORY
 *
 * [foreign property]
 *     camp
 *
 * [referrer property]
 *     normalSayRestrictionList, skillAllocationList, villagePlayerByRequestSkillCodeList, villagePlayerBySecondRequestSkillCodeList, villagePlayerBySkillCodeList, villagePlayerSkillHistoryList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * String skillCode = entity.getSkillCode();
 * String skillName = entity.getSkillName();
 * String skillShortName = entity.getSkillShortName();
 * String campCode = entity.getCampCode();
 * Integer dispOrder = entity.getDispOrder();
 * entity.setSkillCode(skillCode);
 * entity.setSkillName(skillName);
 * entity.setSkillShortName(skillShortName);
 * entity.setCampCode(campCode);
 * entity.setDispOrder(dispOrder);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsSkill extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** SKILL_CODE: {PK, NotNull, VARCHAR(20), classification=Skill} */
    protected String _skillCode;

    /** SKILL_NAME: {NotNull, VARCHAR(20)} */
    protected String _skillName;

    /** SKILL_SHORT_NAME: {NotNull, CHAR(1)} */
    protected String _skillShortName;

    /** CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp, classification=Camp} */
    protected String _campCode;

    /** DISP_ORDER: {NotNull, INT UNSIGNED(10)} */
    protected Integer _dispOrder;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "skill";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_skillCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of skillCode as the classification of Skill. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20), classification=Skill} <br>
     * 役職
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Skill getSkillCodeAsSkill() {
        return CDef.Skill.codeOf(getSkillCode());
    }

    /**
     * Set the value of skillCode as the classification of Skill. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20), classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setSkillCodeAsSkill(CDef.Skill cdef) {
        setSkillCode(cdef != null ? cdef.code() : null);
    }

    /**
     * Get the value of campCode as the classification of Camp. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp, classification=Camp} <br>
     * 陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Camp getCampCodeAsCamp() {
        return CDef.Camp.codeOf(getCampCode());
    }

    /**
     * Set the value of campCode as the classification of Camp. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp, classification=Camp} <br>
     * 陣営
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setCampCodeAsCamp(CDef.Camp cdef) {
        setCampCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of skillCode as 教唆者 (ABETTER). <br>
     * 教唆者
     */
    public void setSkillCode_教唆者() {
        setSkillCodeAsSkill(CDef.Skill.教唆者);
    }

    /**
     * Set the value of skillCode as 絶対人狼 (ABSOLUTEWOLF). <br>
     * 絶対人狼
     */
    public void setSkillCode_絶対人狼() {
        setSkillCodeAsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * Set the value of skillCode as 餡麺麭者 (ANPANMAN). <br>
     * 餡麺麭者
     */
    public void setSkillCode_餡麺麭者() {
        setSkillCodeAsSkill(CDef.Skill.餡麺麭者);
    }

    /**
     * Set the value of skillCode as 占星術師 (ASTROLOGER). <br>
     * 占星術師
     */
    public void setSkillCode_占星術師() {
        setSkillCodeAsSkill(CDef.Skill.占星術師);
    }

    /**
     * Set the value of skillCode as ババ (BABA). <br>
     * ババ
     */
    public void setSkillCode_ババ() {
        setSkillCodeAsSkill(CDef.Skill.ババ);
    }

    /**
     * Set the value of skillCode as 美人局 (BADGERGAME). <br>
     * 美人局
     */
    public void setSkillCode_美人局() {
        setSkillCodeAsSkill(CDef.Skill.美人局);
    }

    /**
     * Set the value of skillCode as パン屋 (BAKERY). <br>
     * パン屋
     */
    public void setSkillCode_パン屋() {
        setSkillCodeAsSkill(CDef.Skill.パン屋);
    }

    /**
     * Set the value of skillCode as バールのようなもの (BAR). <br>
     * バールのようなもの
     */
    public void setSkillCode_バールのようなもの() {
        setSkillCodeAsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * Set the value of skillCode as 黒箱者 (BLACKBOX). <br>
     * 黒箱者
     */
    public void setSkillCode_黒箱者() {
        setSkillCodeAsSkill(CDef.Skill.黒箱者);
    }

    /**
     * Set the value of skillCode as 爆弾魔 (BOMBER). <br>
     * 爆弾魔
     */
    public void setSkillCode_爆弾魔() {
        setSkillCodeAsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * Set the value of skillCode as 組長 (BOSS). <br>
     * 組長
     */
    public void setSkillCode_組長() {
        setSkillCodeAsSkill(CDef.Skill.組長);
    }

    /**
     * Set the value of skillCode as 誑狐 (CHEATERFOX). <br>
     * 誑狐
     */
    public void setSkillCode_誑狐() {
        setSkillCodeAsSkill(CDef.Skill.誑狐);
    }

    /**
     * Set the value of skillCode as 曇天者 (CLOUDY). <br>
     * 曇天者
     */
    public void setSkillCode_曇天者() {
        setSkillCodeAsSkill(CDef.Skill.曇天者);
    }

    /**
     * Set the value of skillCode as 道化師 (CLOWN). <br>
     * 道化師
     */
    public void setSkillCode_道化師() {
        setSkillCodeAsSkill(CDef.Skill.道化師);
    }

    /**
     * Set the value of skillCode as C国狂人 (CMADMAN). <br>
     * C国狂人
     */
    public void setSkillCode_C国狂人() {
        setSkillCodeAsSkill(CDef.Skill.C国狂人);
    }

    /**
     * Set the value of skillCode as 同棲者 (COHABITER). <br>
     * 同棲者
     */
    public void setSkillCode_同棲者() {
        setSkillCodeAsSkill(CDef.Skill.同棲者);
    }

    /**
     * Set the value of skillCode as 指揮官 (COMMANDER). <br>
     * 指揮官
     */
    public void setSkillCode_指揮官() {
        setSkillCodeAsSkill(CDef.Skill.指揮官);
    }

    /**
     * Set the value of skillCode as 検死官 (CORONER). <br>
     * 検死官
     */
    public void setSkillCode_検死官() {
        setSkillCodeAsSkill(CDef.Skill.検死官);
    }

    /**
     * Set the value of skillCode as 求愛者 (COURTSHIP). <br>
     * 求愛者
     */
    public void setSkillCode_求愛者() {
        setSkillCodeAsSkill(CDef.Skill.求愛者);
    }

    /**
     * Set the value of skillCode as おまかせ愉快犯陣営 (CRIMINALS). <br>
     * おまかせ（愉快犯陣営）
     */
    public void setSkillCode_おまかせ愉快犯陣営() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * Set the value of skillCode as 反呪者 (CURSECOUNTER). <br>
     * 反呪者
     */
    public void setSkillCode_反呪者() {
        setSkillCodeAsSkill(CDef.Skill.反呪者);
    }

    /**
     * Set the value of skillCode as 呪縛者 (CURSER). <br>
     * 呪縛者
     */
    public void setSkillCode_呪縛者() {
        setSkillCodeAsSkill(CDef.Skill.呪縛者);
    }

    /**
     * Set the value of skillCode as 呪狼 (CURSEWOLF). <br>
     * 呪狼
     */
    public void setSkillCode_呪狼() {
        setSkillCodeAsSkill(CDef.Skill.呪狼);
    }

    /**
     * Set the value of skillCode as 探偵 (DETECTIVE). <br>
     * 探偵
     */
    public void setSkillCode_探偵() {
        setSkillCodeAsSkill(CDef.Skill.探偵);
    }

    /**
     * Set the value of skillCode as 興信者 (DETECTSEER). <br>
     * 興信者
     */
    public void setSkillCode_興信者() {
        setSkillCodeAsSkill(CDef.Skill.興信者);
    }

    /**
     * Set the value of skillCode as 箪笥 (DRAWERS). <br>
     * 箪笥
     */
    public void setSkillCode_箪笥() {
        setSkillCodeAsSkill(CDef.Skill.箪笥);
    }

    /**
     * Set the value of skillCode as 不止者 (DYINGPOINTER). <br>
     * 不止者
     */
    public void setSkillCode_不止者() {
        setSkillCodeAsSkill(CDef.Skill.不止者);
    }

    /**
     * Set the value of skillCode as 闇パン屋 (EVILBAKERY). <br>
     * 闇パン屋
     */
    public void setSkillCode_闇パン屋() {
        setSkillCodeAsSkill(CDef.Skill.闇パン屋);
    }

    /**
     * Set the value of skillCode as 闇探偵 (EVILDETECTIVE). <br>
     * 闇探偵
     */
    public void setSkillCode_闇探偵() {
        setSkillCodeAsSkill(CDef.Skill.闇探偵);
    }

    /**
     * Set the value of skillCode as 魔神官 (EVILMEDIUM). <br>
     * 魔神官
     */
    public void setSkillCode_魔神官() {
        setSkillCodeAsSkill(CDef.Skill.魔神官);
    }

    /**
     * Set the value of skillCode as 執行人 (EXECUTIONER). <br>
     * 執行人
     */
    public void setSkillCode_執行人() {
        setSkillCodeAsSkill(CDef.Skill.執行人);
    }

    /**
     * Set the value of skillCode as 冤罪者 (FALSECHARGES). <br>
     * 冤罪者
     */
    public void setSkillCode_冤罪者() {
        setSkillCodeAsSkill(CDef.Skill.冤罪者);
    }

    /**
     * Set the value of skillCode as 狂信者 (FANATIC). <br>
     * 狂信者
     */
    public void setSkillCode_狂信者() {
        setSkillCodeAsSkill(CDef.Skill.狂信者);
    }

    /**
     * Set the value of skillCode as 妄想癖 (FANTASIST). <br>
     * 妄想癖
     */
    public void setSkillCode_妄想癖() {
        setSkillCodeAsSkill(CDef.Skill.妄想癖);
    }

    /**
     * Set the value of skillCode as 花占い師 (FLOWERSEER). <br>
     * 花占い師
     */
    public void setSkillCode_花占い師() {
        setSkillCodeAsSkill(CDef.Skill.花占い師);
    }

    /**
     * Set the value of skillCode as おまかせ足音職 (FOOTSTEPS). <br>
     * おまかせ（足音職）
     */
    public void setSkillCode_おまかせ足音職() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * Set the value of skillCode as 妖狐 (FOX). <br>
     * 妖狐
     */
    public void setSkillCode_妖狐() {
        setSkillCodeAsSkill(CDef.Skill.妖狐);
    }

    /**
     * Set the value of skillCode as おまかせ妖狐陣営 (FOXS). <br>
     * おまかせ（妖狐陣営）
     */
    public void setSkillCode_おまかせ妖狐陣営() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * Set the value of skillCode as 冷凍者 (FREEZER). <br>
     * 冷凍者
     */
    public void setSkillCode_冷凍者() {
        setSkillCodeAsSkill(CDef.Skill.冷凍者);
    }

    /**
     * Set the value of skillCode as おまかせ役職窓あり (FRIENDS). <br>
     * おまかせ（役職窓あり）
     */
    public void setSkillCode_おまかせ役職窓あり() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * Set the value of skillCode as 果実籠 (FRUITSBASKET). <br>
     * 果実籠
     */
    public void setSkillCode_果実籠() {
        setSkillCodeAsSkill(CDef.Skill.果実籠);
    }

    /**
     * Set the value of skillCode as 歩狼 (FUWOLF). <br>
     * 歩狼
     */
    public void setSkillCode_歩狼() {
        setSkillCodeAsSkill(CDef.Skill.歩狼);
    }

    /**
     * Set the value of skillCode as 銀狼 (GINWOLF). <br>
     * 銀狼
     */
    public void setSkillCode_銀狼() {
        setSkillCodeAsSkill(CDef.Skill.銀狼);
    }

    /**
     * Set the value of skillCode as ごん (GONFOX). <br>
     * ごん
     */
    public void setSkillCode_ごん() {
        setSkillCodeAsSkill(CDef.Skill.ごん);
    }

    /**
     * Set the value of skillCode as 濡衣者 (GUILTER). <br>
     * 濡衣者
     */
    public void setSkillCode_濡衣者() {
        setSkillCodeAsSkill(CDef.Skill.濡衣者);
    }

    /**
     * Set the value of skillCode as 導師 (GURU). <br>
     * 導師
     */
    public void setSkillCode_導師() {
        setSkillCodeAsSkill(CDef.Skill.導師);
    }

    /**
     * Set the value of skillCode as 堅狼 (HARDWOLF). <br>
     * 堅狼
     */
    public void setSkillCode_堅狼() {
        setSkillCodeAsSkill(CDef.Skill.堅狼);
    }

    /**
     * Set the value of skillCode as 申し子 (HEAVENCHILD). <br>
     * 申し子
     */
    public void setSkillCode_申し子() {
        setSkillCodeAsSkill(CDef.Skill.申し子);
    }

    /**
     * Set the value of skillCode as 仙狐 (HERMITFOX). <br>
     * 仙狐
     */
    public void setSkillCode_仙狐() {
        setSkillCodeAsSkill(CDef.Skill.仙狐);
    }

    /**
     * Set the value of skillCode as 勇者 (HERO). <br>
     * 勇者
     */
    public void setSkillCode_勇者() {
        setSkillCodeAsSkill(CDef.Skill.勇者);
    }

    /**
     * Set the value of skillCode as 飛狼 (HISHAWOLF). <br>
     * 飛狼
     */
    public void setSkillCode_飛狼() {
        setSkillCodeAsSkill(CDef.Skill.飛狼);
    }

    /**
     * Set the value of skillCode as 冷やし中華 (HIYASICHUKA). <br>
     * 冷やし中華
     */
    public void setSkillCode_冷やし中華() {
        setSkillCodeAsSkill(CDef.Skill.冷やし中華);
    }

    /**
     * Set the value of skillCode as 狩人 (HUNTER). <br>
     * 狩人
     */
    public void setSkillCode_狩人() {
        setSkillCodeAsSkill(CDef.Skill.狩人);
    }

    /**
     * Set the value of skillCode as 背徳者 (IMMORAL). <br>
     * 背徳者
     */
    public void setSkillCode_背徳者() {
        setSkillCodeAsSkill(CDef.Skill.背徳者);
    }

    /**
     * Set the value of skillCode as 稲荷 (INARI). <br>
     * 稲荷
     */
    public void setSkillCode_稲荷() {
        setSkillCodeAsSkill(CDef.Skill.稲荷);
    }

    /**
     * Set the value of skillCode as 煽動者 (INSTIGATOR). <br>
     * 煽動者
     */
    public void setSkillCode_煽動者() {
        setSkillCodeAsSkill(CDef.Skill.煽動者);
    }

    /**
     * Set the value of skillCode as 保険屋 (INSURANCER). <br>
     * 保険屋
     */
    public void setSkillCode_保険屋() {
        setSkillCodeAsSkill(CDef.Skill.保険屋);
    }

    /**
     * Set the value of skillCode as 絡新婦 (JOROGUMO). <br>
     * 絡新婦
     */
    public void setSkillCode_絡新婦() {
        setSkillCodeAsSkill(CDef.Skill.絡新婦);
    }

    /**
     * Set the value of skillCode as 角狼 (KAKUWOLF). <br>
     * 角狼
     */
    public void setSkillCode_角狼() {
        setSkillCodeAsSkill(CDef.Skill.角狼);
    }

    /**
     * Set the value of skillCode as 王狼 (KINGWOLF). <br>
     * 王狼
     */
    public void setSkillCode_王狼() {
        setSkillCodeAsSkill(CDef.Skill.王狼);
    }

    /**
     * Set the value of skillCode as 金狼 (KINWOLF). <br>
     * 金狼
     */
    public void setSkillCode_金狼() {
        setSkillCodeAsSkill(CDef.Skill.金狼);
    }

    /**
     * Set the value of skillCode as 管狐 (KUDAFOX). <br>
     * 管狐
     */
    public void setSkillCode_管狐() {
        setSkillCodeAsSkill(CDef.Skill.管狐);
    }

    /**
     * Set the value of skillCode as 弁護士 (LAWYER). <br>
     * 弁護士
     */
    public void setSkillCode_弁護士() {
        setSkillCodeAsSkill(CDef.Skill.弁護士);
    }

    /**
     * Set the value of skillCode as おまかせ (LEFTOVER). <br>
     * おまかせ
     */
    public void setSkillCode_おまかせ() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Set the value of skillCode as 伝説の殺し屋 (LEGENDASSASSIN). <br>
     * 伝説の殺し屋
     */
    public void setSkillCode_伝説の殺し屋() {
        setSkillCodeAsSkill(CDef.Skill.伝説の殺し屋);
    }

    /**
     * Set the value of skillCode as 聴狂人 (LISTENMADMAN). <br>
     * 聴狂人
     */
    public void setSkillCode_聴狂人() {
        setSkillCodeAsSkill(CDef.Skill.聴狂人);
    }

    /**
     * Set the value of skillCode as 共有者 (LISTENMASON). <br>
     * 共有者
     */
    public void setSkillCode_共有者() {
        setSkillCodeAsSkill(CDef.Skill.共有者);
    }

    /**
     * Set the value of skillCode as 黙狼 (LISTENWOLF). <br>
     * 黙狼
     */
    public void setSkillCode_黙狼() {
        setSkillCodeAsSkill(CDef.Skill.黙狼);
    }

    /**
     * Set the value of skillCode as 一匹狼 (LONEWOLF). <br>
     * 一匹狼
     */
    public void setSkillCode_一匹狼() {
        setSkillCodeAsSkill(CDef.Skill.一匹狼);
    }

    /**
     * Set the value of skillCode as 拡声者 (LOUDSPEAKER). <br>
     * 拡声者
     */
    public void setSkillCode_拡声者() {
        setSkillCodeAsSkill(CDef.Skill.拡声者);
    }

    /**
     * Set the value of skillCode as 恋人 (LOVER). <br>
     * 恋人
     */
    public void setSkillCode_恋人() {
        setSkillCodeAsSkill(CDef.Skill.恋人);
    }

    /**
     * Set the value of skillCode as おまかせ恋人陣営 (LOVERS). <br>
     * おまかせ（恋人陣営）
     */
    public void setSkillCode_おまかせ恋人陣営() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * Set the value of skillCode as 強運者 (LUCKYMAN). <br>
     * 強運者
     */
    public void setSkillCode_強運者() {
        setSkillCodeAsSkill(CDef.Skill.強運者);
    }

    /**
     * Set the value of skillCode as 狂人 (MADMAN). <br>
     * 狂人
     */
    public void setSkillCode_狂人() {
        setSkillCodeAsSkill(CDef.Skill.狂人);
    }

    /**
     * Set the value of skillCode as 共鳴者 (MASON). <br>
     * 共鳴者
     */
    public void setSkillCode_共鳴者() {
        setSkillCodeAsSkill(CDef.Skill.共鳴者);
    }

    /**
     * Set the value of skillCode as マタギ (MATAGI). <br>
     * マタギ
     */
    public void setSkillCode_マタギ() {
        setSkillCodeAsSkill(CDef.Skill.マタギ);
    }

    /**
     * Set the value of skillCode as 市長 (MAYOR). <br>
     * 市長
     */
    public void setSkillCode_市長() {
        setSkillCodeAsSkill(CDef.Skill.市長);
    }

    /**
     * Set the value of skillCode as 霊能者 (MEDIUM). <br>
     * 霊能者
     */
    public void setSkillCode_霊能者() {
        setSkillCodeAsSkill(CDef.Skill.霊能者);
    }

    /**
     * Set the value of skillCode as 魅惑の人魚 (MERMAID). <br>
     * 魅惑の人魚
     */
    public void setSkillCode_魅惑の人魚() {
        setSkillCodeAsSkill(CDef.Skill.魅惑の人魚);
    }

    /**
     * Set the value of skillCode as 耳年増 (MIMIDOSHIMA). <br>
     * 耳年増
     */
    public void setSkillCode_耳年増() {
        setSkillCodeAsSkill(CDef.Skill.耳年増);
    }

    /**
     * Set the value of skillCode as 死霊術師 (NECROMANCER). <br>
     * 死霊術師
     */
    public void setSkillCode_死霊術師() {
        setSkillCodeAsSkill(CDef.Skill.死霊術師);
    }

    /**
     * Set the value of skillCode as 夜狐 (NIGHTFOX). <br>
     * 夜狐
     */
    public void setSkillCode_夜狐() {
        setSkillCodeAsSkill(CDef.Skill.夜狐);
    }

    /**
     * Set the value of skillCode as おまかせ役職窓なし (NOFRIENDS). <br>
     * おまかせ（役職窓なし）
     */
    public void setSkillCode_おまかせ役職窓なし() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * Set the value of skillCode as リア充 (NORMIE). <br>
     * リア充
     */
    public void setSkillCode_リア充() {
        setSkillCodeAsSkill(CDef.Skill.リア充);
    }

    /**
     * Set the value of skillCode as おまかせ人外 (NOVILLAGERS). <br>
     * おまかせ（人外）
     */
    public void setSkillCode_おまかせ人外() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * Set the value of skillCode as 監視者 (OBSERVER). <br>
     * 監視者
     */
    public void setSkillCode_監視者() {
        setSkillCodeAsSkill(CDef.Skill.監視者);
    }

    /**
     * Set the value of skillCode as 全知者 (OMNISCIENCE). <br>
     * 全知者
     */
    public void setSkillCode_全知者() {
        setSkillCodeAsSkill(CDef.Skill.全知者);
    }

    /**
     * Set the value of skillCode as 陰陽師 (ONMYOJI). <br>
     * 陰陽師
     */
    public void setSkillCode_陰陽師() {
        setSkillCodeAsSkill(CDef.Skill.陰陽師);
    }

    /**
     * Set the value of skillCode as 梟 (OWL). <br>
     * 梟
     */
    public void setSkillCode_梟() {
        setSkillCodeAsSkill(CDef.Skill.梟);
    }

    /**
     * Set the value of skillCode as 牧師 (PASTOR). <br>
     * 牧師
     */
    public void setSkillCode_牧師() {
        setSkillCodeAsSkill(CDef.Skill.牧師);
    }

    /**
     * Set the value of skillCode as 画鋲 (PUSHPIN). <br>
     * 画鋲
     */
    public void setSkillCode_画鋲() {
        setSkillCodeAsSkill(CDef.Skill.画鋲);
    }

    /**
     * Set the value of skillCode as 虹職人 (RAINBOW). <br>
     * 虹職人
     */
    public void setSkillCode_虹職人() {
        setSkillCodeAsSkill(CDef.Skill.虹職人);
    }

    /**
     * Set the value of skillCode as 転生者 (REINCARNATION). <br>
     * 転生者
     */
    public void setSkillCode_転生者() {
        setSkillCodeAsSkill(CDef.Skill.転生者);
    }

    /**
     * Set the value of skillCode as 覚者 (REMEMBERSEER). <br>
     * 覚者
     */
    public void setSkillCode_覚者() {
        setSkillCodeAsSkill(CDef.Skill.覚者);
    }

    /**
     * Set the value of skillCode as 怨恨者 (RESENTER). <br>
     * 怨恨者
     */
    public void setSkillCode_怨恨者() {
        setSkillCodeAsSkill(CDef.Skill.怨恨者);
    }

    /**
     * Set the value of skillCode as 蘇生者 (RESUSCITATOR). <br>
     * 蘇生者
     */
    public void setSkillCode_蘇生者() {
        setSkillCodeAsSkill(CDef.Skill.蘇生者);
    }

    /**
     * Set the value of skillCode as 革命者 (REVOLUTIONARY). <br>
     * 革命者
     */
    public void setSkillCode_革命者() {
        setSkillCodeAsSkill(CDef.Skill.革命者);
    }

    /**
     * Set the value of skillCode as 王族 (ROYALTY). <br>
     * 王族
     */
    public void setSkillCode_王族() {
        setSkillCodeAsSkill(CDef.Skill.王族);
    }

    /**
     * Set the value of skillCode as 占い師 (SEER). <br>
     * 占い師
     */
    public void setSkillCode_占い師() {
        setSkillCodeAsSkill(CDef.Skill.占い師);
    }

    /**
     * Set the value of skillCode as 破局者 (SEPARATOR). <br>
     * 破局者
     */
    public void setSkillCode_破局者() {
        setSkillCodeAsSkill(CDef.Skill.破局者);
    }

    /**
     * Set the value of skillCode as 静狼 (SILENTWOLF). <br>
     * 静狼
     */
    public void setSkillCode_静狼() {
        setSkillCodeAsSkill(CDef.Skill.静狼);
    }

    /**
     * Set the value of skillCode as 感覚者 (SIXTHSENSOR). <br>
     * 感覚者
     */
    public void setSkillCode_感覚者() {
        setSkillCodeAsSkill(CDef.Skill.感覚者);
    }

    /**
     * Set the value of skillCode as 夢遊病者 (SLEEPWALKER). <br>
     * 夢遊病者
     */
    public void setSkillCode_夢遊病者() {
        setSkillCodeAsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * Set the value of skillCode as 臭狼 (SMELLWOLF). <br>
     * 臭狼
     */
    public void setSkillCode_臭狼() {
        setSkillCodeAsSkill(CDef.Skill.臭狼);
    }

    /**
     * Set the value of skillCode as 防音者 (SOUNDPROOFER). <br>
     * 防音者
     */
    public void setSkillCode_防音者() {
        setSkillCodeAsSkill(CDef.Skill.防音者);
    }

    /**
     * Set the value of skillCode as ストーカー (STALKER). <br>
     * ストーカー
     */
    public void setSkillCode_ストーカー() {
        setSkillCodeAsSkill(CDef.Skill.ストーカー);
    }

    /**
     * Set the value of skillCode as 濁点者 (TATSUYA). <br>
     * 濁点者
     */
    public void setSkillCode_濁点者() {
        setSkillCodeAsSkill(CDef.Skill.濁点者);
    }

    /**
     * Set the value of skillCode as 泥棒猫 (THIEFCAT). <br>
     * 泥棒猫
     */
    public void setSkillCode_泥棒猫() {
        setSkillCodeAsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * Set the value of skillCode as 翻訳者 (TRANSLATOR). <br>
     * 翻訳者
     */
    public void setSkillCode_翻訳者() {
        setSkillCodeAsSkill(CDef.Skill.翻訳者);
    }

    /**
     * Set the value of skillCode as 罠師 (TRAPPER). <br>
     * 罠師
     */
    public void setSkillCode_罠師() {
        setSkillCodeAsSkill(CDef.Skill.罠師);
    }

    /**
     * Set the value of skillCode as 騙狐 (TRICKFOX). <br>
     * 騙狐
     */
    public void setSkillCode_騙狐() {
        setSkillCodeAsSkill(CDef.Skill.騙狐);
    }

    /**
     * Set the value of skillCode as トラック (TRUCK). <br>
     * トラック
     */
    public void setSkillCode_トラック() {
        setSkillCodeAsSkill(CDef.Skill.トラック);
    }

    /**
     * Set the value of skillCode as 村人 (VILLAGER). <br>
     * 村人
     */
    public void setSkillCode_村人() {
        setSkillCodeAsSkill(CDef.Skill.村人);
    }

    /**
     * Set the value of skillCode as おまかせ村人陣営 (VILLAGERS). <br>
     * おまかせ（村人陣営）
     */
    public void setSkillCode_おまかせ村人陣営() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * Set the value of skillCode as 壁殴り代行 (WALLPUNCHER). <br>
     * 壁殴り代行
     */
    public void setSkillCode_壁殴り代行() {
        setSkillCodeAsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * Set the value of skillCode as 風来狩人 (WANDERER). <br>
     * 風来狩人
     */
    public void setSkillCode_風来狩人() {
        setSkillCodeAsSkill(CDef.Skill.風来狩人);
    }

    /**
     * Set the value of skillCode as 人狼 (WEREWOLF). <br>
     * 人狼
     */
    public void setSkillCode_人狼() {
        setSkillCodeAsSkill(CDef.Skill.人狼);
    }

    /**
     * Set the value of skillCode as おまかせ人狼陣営 (WEREWOLFS). <br>
     * おまかせ（人狼陣営）
     */
    public void setSkillCode_おまかせ人狼陣営() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * Set the value of skillCode as 当選者 (WINNER). <br>
     * 当選者
     */
    public void setSkillCode_当選者() {
        setSkillCodeAsSkill(CDef.Skill.当選者);
    }

    /**
     * Set the value of skillCode as 賢者 (WISE). <br>
     * 賢者
     */
    public void setSkillCode_賢者() {
        setSkillCodeAsSkill(CDef.Skill.賢者);
    }

    /**
     * Set the value of skillCode as 智狼 (WISEWOLF). <br>
     * 智狼
     */
    public void setSkillCode_智狼() {
        setSkillCodeAsSkill(CDef.Skill.智狼);
    }

    /**
     * Set the value of campCode as 愉快犯陣営 (CRIMINAL). <br>
     * 愉快犯陣営
     */
    public void setCampCode_愉快犯陣営() {
        setCampCodeAsCamp(CDef.Camp.愉快犯陣営);
    }

    /**
     * Set the value of campCode as 狐陣営 (FOX). <br>
     * 狐陣営
     */
    public void setCampCode_狐陣営() {
        setCampCodeAsCamp(CDef.Camp.狐陣営);
    }

    /**
     * Set the value of campCode as 恋人陣営 (LOVERS). <br>
     * 恋人陣営
     */
    public void setCampCode_恋人陣営() {
        setCampCodeAsCamp(CDef.Camp.恋人陣営);
    }

    /**
     * Set the value of campCode as 村人陣営 (VILLAGER). <br>
     * 村人陣営
     */
    public void setCampCode_村人陣営() {
        setCampCodeAsCamp(CDef.Camp.村人陣営);
    }

    /**
     * Set the value of campCode as 人狼陣営 (WEREWOLF). <br>
     * 人狼陣営
     */
    public void setCampCode_人狼陣営() {
        setCampCodeAsCamp(CDef.Camp.人狼陣営);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of skillCode 教唆者? <br>
     * 教唆者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode教唆者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.教唆者) : false;
    }

    /**
     * Is the value of skillCode 絶対人狼? <br>
     * 絶対人狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode絶対人狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.絶対人狼) : false;
    }

    /**
     * Is the value of skillCode 餡麺麭者? <br>
     * 餡麺麭者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode餡麺麭者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.餡麺麭者) : false;
    }

    /**
     * Is the value of skillCode 占星術師? <br>
     * 占星術師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode占星術師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.占星術師) : false;
    }

    /**
     * Is the value of skillCode ババ? <br>
     * ババ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeババ() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ババ) : false;
    }

    /**
     * Is the value of skillCode 美人局? <br>
     * 美人局
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode美人局() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.美人局) : false;
    }

    /**
     * Is the value of skillCode パン屋? <br>
     * パン屋
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeパン屋() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.パン屋) : false;
    }

    /**
     * Is the value of skillCode バールのようなもの? <br>
     * バールのようなもの
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeバールのようなもの() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.バールのようなもの) : false;
    }

    /**
     * Is the value of skillCode 黒箱者? <br>
     * 黒箱者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode黒箱者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.黒箱者) : false;
    }

    /**
     * Is the value of skillCode 爆弾魔? <br>
     * 爆弾魔
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode爆弾魔() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.爆弾魔) : false;
    }

    /**
     * Is the value of skillCode 組長? <br>
     * 組長
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode組長() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.組長) : false;
    }

    /**
     * Is the value of skillCode 誑狐? <br>
     * 誑狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode誑狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.誑狐) : false;
    }

    /**
     * Is the value of skillCode 曇天者? <br>
     * 曇天者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode曇天者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.曇天者) : false;
    }

    /**
     * Is the value of skillCode 道化師? <br>
     * 道化師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode道化師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.道化師) : false;
    }

    /**
     * Is the value of skillCode C国狂人? <br>
     * C国狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeC国狂人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.C国狂人) : false;
    }

    /**
     * Is the value of skillCode 同棲者? <br>
     * 同棲者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode同棲者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.同棲者) : false;
    }

    /**
     * Is the value of skillCode 指揮官? <br>
     * 指揮官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode指揮官() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.指揮官) : false;
    }

    /**
     * Is the value of skillCode 検死官? <br>
     * 検死官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode検死官() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.検死官) : false;
    }

    /**
     * Is the value of skillCode 求愛者? <br>
     * 求愛者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode求愛者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.求愛者) : false;
    }

    /**
     * Is the value of skillCode おまかせ愉快犯陣営? <br>
     * おまかせ（愉快犯陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ愉快犯陣営() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ愉快犯陣営) : false;
    }

    /**
     * Is the value of skillCode 反呪者? <br>
     * 反呪者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode反呪者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.反呪者) : false;
    }

    /**
     * Is the value of skillCode 呪縛者? <br>
     * 呪縛者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode呪縛者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.呪縛者) : false;
    }

    /**
     * Is the value of skillCode 呪狼? <br>
     * 呪狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode呪狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.呪狼) : false;
    }

    /**
     * Is the value of skillCode 探偵? <br>
     * 探偵
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode探偵() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.探偵) : false;
    }

    /**
     * Is the value of skillCode 興信者? <br>
     * 興信者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode興信者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.興信者) : false;
    }

    /**
     * Is the value of skillCode 箪笥? <br>
     * 箪笥
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode箪笥() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.箪笥) : false;
    }

    /**
     * Is the value of skillCode 不止者? <br>
     * 不止者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode不止者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.不止者) : false;
    }

    /**
     * Is the value of skillCode 闇パン屋? <br>
     * 闇パン屋
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode闇パン屋() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.闇パン屋) : false;
    }

    /**
     * Is the value of skillCode 闇探偵? <br>
     * 闇探偵
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode闇探偵() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.闇探偵) : false;
    }

    /**
     * Is the value of skillCode 魔神官? <br>
     * 魔神官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode魔神官() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.魔神官) : false;
    }

    /**
     * Is the value of skillCode 執行人? <br>
     * 執行人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode執行人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.執行人) : false;
    }

    /**
     * Is the value of skillCode 冤罪者? <br>
     * 冤罪者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode冤罪者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.冤罪者) : false;
    }

    /**
     * Is the value of skillCode 狂信者? <br>
     * 狂信者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode狂信者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狂信者) : false;
    }

    /**
     * Is the value of skillCode 妄想癖? <br>
     * 妄想癖
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode妄想癖() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.妄想癖) : false;
    }

    /**
     * Is the value of skillCode 花占い師? <br>
     * 花占い師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode花占い師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.花占い師) : false;
    }

    /**
     * Is the value of skillCode おまかせ足音職? <br>
     * おまかせ（足音職）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ足音職() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ足音職) : false;
    }

    /**
     * Is the value of skillCode 妖狐? <br>
     * 妖狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode妖狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.妖狐) : false;
    }

    /**
     * Is the value of skillCode おまかせ妖狐陣営? <br>
     * おまかせ（妖狐陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ妖狐陣営() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ妖狐陣営) : false;
    }

    /**
     * Is the value of skillCode 冷凍者? <br>
     * 冷凍者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode冷凍者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.冷凍者) : false;
    }

    /**
     * Is the value of skillCode おまかせ役職窓あり? <br>
     * おまかせ（役職窓あり）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ役職窓あり() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ役職窓あり) : false;
    }

    /**
     * Is the value of skillCode 果実籠? <br>
     * 果実籠
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode果実籠() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.果実籠) : false;
    }

    /**
     * Is the value of skillCode 歩狼? <br>
     * 歩狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode歩狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.歩狼) : false;
    }

    /**
     * Is the value of skillCode 銀狼? <br>
     * 銀狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode銀狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.銀狼) : false;
    }

    /**
     * Is the value of skillCode ごん? <br>
     * ごん
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeごん() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ごん) : false;
    }

    /**
     * Is the value of skillCode 濡衣者? <br>
     * 濡衣者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode濡衣者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.濡衣者) : false;
    }

    /**
     * Is the value of skillCode 導師? <br>
     * 導師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode導師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.導師) : false;
    }

    /**
     * Is the value of skillCode 堅狼? <br>
     * 堅狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode堅狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.堅狼) : false;
    }

    /**
     * Is the value of skillCode 申し子? <br>
     * 申し子
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode申し子() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.申し子) : false;
    }

    /**
     * Is the value of skillCode 仙狐? <br>
     * 仙狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode仙狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.仙狐) : false;
    }

    /**
     * Is the value of skillCode 勇者? <br>
     * 勇者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode勇者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.勇者) : false;
    }

    /**
     * Is the value of skillCode 飛狼? <br>
     * 飛狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode飛狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.飛狼) : false;
    }

    /**
     * Is the value of skillCode 冷やし中華? <br>
     * 冷やし中華
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode冷やし中華() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.冷やし中華) : false;
    }

    /**
     * Is the value of skillCode 狩人? <br>
     * 狩人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode狩人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狩人) : false;
    }

    /**
     * Is the value of skillCode 背徳者? <br>
     * 背徳者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode背徳者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.背徳者) : false;
    }

    /**
     * Is the value of skillCode 稲荷? <br>
     * 稲荷
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode稲荷() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.稲荷) : false;
    }

    /**
     * Is the value of skillCode 煽動者? <br>
     * 煽動者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode煽動者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.煽動者) : false;
    }

    /**
     * Is the value of skillCode 保険屋? <br>
     * 保険屋
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode保険屋() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.保険屋) : false;
    }

    /**
     * Is the value of skillCode 絡新婦? <br>
     * 絡新婦
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode絡新婦() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.絡新婦) : false;
    }

    /**
     * Is the value of skillCode 角狼? <br>
     * 角狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode角狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.角狼) : false;
    }

    /**
     * Is the value of skillCode 王狼? <br>
     * 王狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode王狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.王狼) : false;
    }

    /**
     * Is the value of skillCode 金狼? <br>
     * 金狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode金狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.金狼) : false;
    }

    /**
     * Is the value of skillCode 管狐? <br>
     * 管狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode管狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.管狐) : false;
    }

    /**
     * Is the value of skillCode 弁護士? <br>
     * 弁護士
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode弁護士() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.弁護士) : false;
    }

    /**
     * Is the value of skillCode おまかせ? <br>
     * おまかせ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ) : false;
    }

    /**
     * Is the value of skillCode 伝説の殺し屋? <br>
     * 伝説の殺し屋
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode伝説の殺し屋() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.伝説の殺し屋) : false;
    }

    /**
     * Is the value of skillCode 聴狂人? <br>
     * 聴狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode聴狂人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.聴狂人) : false;
    }

    /**
     * Is the value of skillCode 共有者? <br>
     * 共有者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode共有者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.共有者) : false;
    }

    /**
     * Is the value of skillCode 黙狼? <br>
     * 黙狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode黙狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.黙狼) : false;
    }

    /**
     * Is the value of skillCode 一匹狼? <br>
     * 一匹狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode一匹狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.一匹狼) : false;
    }

    /**
     * Is the value of skillCode 拡声者? <br>
     * 拡声者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode拡声者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.拡声者) : false;
    }

    /**
     * Is the value of skillCode 恋人? <br>
     * 恋人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode恋人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.恋人) : false;
    }

    /**
     * Is the value of skillCode おまかせ恋人陣営? <br>
     * おまかせ（恋人陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ恋人陣営() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ恋人陣営) : false;
    }

    /**
     * Is the value of skillCode 強運者? <br>
     * 強運者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode強運者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.強運者) : false;
    }

    /**
     * Is the value of skillCode 狂人? <br>
     * 狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode狂人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狂人) : false;
    }

    /**
     * Is the value of skillCode 共鳴者? <br>
     * 共鳴者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode共鳴者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.共鳴者) : false;
    }

    /**
     * Is the value of skillCode マタギ? <br>
     * マタギ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeマタギ() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.マタギ) : false;
    }

    /**
     * Is the value of skillCode 市長? <br>
     * 市長
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode市長() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.市長) : false;
    }

    /**
     * Is the value of skillCode 霊能者? <br>
     * 霊能者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode霊能者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.霊能者) : false;
    }

    /**
     * Is the value of skillCode 魅惑の人魚? <br>
     * 魅惑の人魚
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode魅惑の人魚() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.魅惑の人魚) : false;
    }

    /**
     * Is the value of skillCode 耳年増? <br>
     * 耳年増
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode耳年増() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.耳年増) : false;
    }

    /**
     * Is the value of skillCode 死霊術師? <br>
     * 死霊術師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode死霊術師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.死霊術師) : false;
    }

    /**
     * Is the value of skillCode 夜狐? <br>
     * 夜狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode夜狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.夜狐) : false;
    }

    /**
     * Is the value of skillCode おまかせ役職窓なし? <br>
     * おまかせ（役職窓なし）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ役職窓なし() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ役職窓なし) : false;
    }

    /**
     * Is the value of skillCode リア充? <br>
     * リア充
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeリア充() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.リア充) : false;
    }

    /**
     * Is the value of skillCode おまかせ人外? <br>
     * おまかせ（人外）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ人外() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ人外) : false;
    }

    /**
     * Is the value of skillCode 監視者? <br>
     * 監視者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode監視者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.監視者) : false;
    }

    /**
     * Is the value of skillCode 全知者? <br>
     * 全知者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode全知者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.全知者) : false;
    }

    /**
     * Is the value of skillCode 陰陽師? <br>
     * 陰陽師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode陰陽師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.陰陽師) : false;
    }

    /**
     * Is the value of skillCode 梟? <br>
     * 梟
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode梟() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.梟) : false;
    }

    /**
     * Is the value of skillCode 牧師? <br>
     * 牧師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode牧師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.牧師) : false;
    }

    /**
     * Is the value of skillCode 画鋲? <br>
     * 画鋲
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode画鋲() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.画鋲) : false;
    }

    /**
     * Is the value of skillCode 虹職人? <br>
     * 虹職人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode虹職人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.虹職人) : false;
    }

    /**
     * Is the value of skillCode 転生者? <br>
     * 転生者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode転生者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.転生者) : false;
    }

    /**
     * Is the value of skillCode 覚者? <br>
     * 覚者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode覚者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.覚者) : false;
    }

    /**
     * Is the value of skillCode 怨恨者? <br>
     * 怨恨者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode怨恨者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.怨恨者) : false;
    }

    /**
     * Is the value of skillCode 蘇生者? <br>
     * 蘇生者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode蘇生者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.蘇生者) : false;
    }

    /**
     * Is the value of skillCode 革命者? <br>
     * 革命者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode革命者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.革命者) : false;
    }

    /**
     * Is the value of skillCode 王族? <br>
     * 王族
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode王族() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.王族) : false;
    }

    /**
     * Is the value of skillCode 占い師? <br>
     * 占い師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode占い師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.占い師) : false;
    }

    /**
     * Is the value of skillCode 破局者? <br>
     * 破局者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode破局者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.破局者) : false;
    }

    /**
     * Is the value of skillCode 静狼? <br>
     * 静狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode静狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.静狼) : false;
    }

    /**
     * Is the value of skillCode 感覚者? <br>
     * 感覚者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode感覚者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.感覚者) : false;
    }

    /**
     * Is the value of skillCode 夢遊病者? <br>
     * 夢遊病者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode夢遊病者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.夢遊病者) : false;
    }

    /**
     * Is the value of skillCode 臭狼? <br>
     * 臭狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode臭狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.臭狼) : false;
    }

    /**
     * Is the value of skillCode 防音者? <br>
     * 防音者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode防音者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.防音者) : false;
    }

    /**
     * Is the value of skillCode ストーカー? <br>
     * ストーカー
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeストーカー() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ストーカー) : false;
    }

    /**
     * Is the value of skillCode 濁点者? <br>
     * 濁点者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode濁点者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.濁点者) : false;
    }

    /**
     * Is the value of skillCode 泥棒猫? <br>
     * 泥棒猫
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode泥棒猫() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.泥棒猫) : false;
    }

    /**
     * Is the value of skillCode 翻訳者? <br>
     * 翻訳者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode翻訳者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.翻訳者) : false;
    }

    /**
     * Is the value of skillCode 罠師? <br>
     * 罠師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode罠師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.罠師) : false;
    }

    /**
     * Is the value of skillCode 騙狐? <br>
     * 騙狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode騙狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.騙狐) : false;
    }

    /**
     * Is the value of skillCode トラック? <br>
     * トラック
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeトラック() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.トラック) : false;
    }

    /**
     * Is the value of skillCode 村人? <br>
     * 村人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode村人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.村人) : false;
    }

    /**
     * Is the value of skillCode おまかせ村人陣営? <br>
     * おまかせ（村人陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ村人陣営() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ村人陣営) : false;
    }

    /**
     * Is the value of skillCode 壁殴り代行? <br>
     * 壁殴り代行
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode壁殴り代行() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.壁殴り代行) : false;
    }

    /**
     * Is the value of skillCode 風来狩人? <br>
     * 風来狩人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode風来狩人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.風来狩人) : false;
    }

    /**
     * Is the value of skillCode 人狼? <br>
     * 人狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode人狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.人狼) : false;
    }

    /**
     * Is the value of skillCode おまかせ人狼陣営? <br>
     * おまかせ（人狼陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ人狼陣営() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ人狼陣営) : false;
    }

    /**
     * Is the value of skillCode 当選者? <br>
     * 当選者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode当選者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.当選者) : false;
    }

    /**
     * Is the value of skillCode 賢者? <br>
     * 賢者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode賢者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.賢者) : false;
    }

    /**
     * Is the value of skillCode 智狼? <br>
     * 智狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode智狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.智狼) : false;
    }

    /**
     * 囁き可能 <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 臭狼, C国狂人]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_AvailableWerewolfSay() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isAvailableWerewolfSay();
    }

    /**
     * 囁きを見られる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, C国狂人, 聴狂人]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_ViewableWerewolfSay() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isViewableWerewolfSay();
    }

    /**
     * 占い能力を持つ <br>
     * The group elements:[占い師, 賢者, 占星術師, 花占い師, 感覚者, 興信者, 管狐]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasDivineAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasDivineAbility();
    }

    /**
     * 役職霊能能力を持つ <br>
     * The group elements:[導師, 魔神官, 稲荷]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasSkillPsychicAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasSkillPsychicAbility();
    }

    /**
     * 襲撃能力を持つ <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasAttackAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasAttackAbility();
    }

    /**
     * 徘徊能力を持つ <br>
     * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 聴狂人, 妖狐, 仙狐, 夜狐, 背徳者]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasDisturbAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasDisturbAbility();
    }

    /**
     * 襲撃されても死なない <br>
     * The group elements:[壁殴り代行, 堅狼, 妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 爆弾魔]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_NoDeadByAttack() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isNoDeadByAttack();
    }

    /**
     * 勝敗判定時、人狼にカウントされる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_WolfCount() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isWolfCount();
    }

    /**
     * 勝敗判定時、人間にも人狼にもカウントされない <br>
     * The group elements:[妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 梟]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_NoCount() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isNoCount();
    }

    /**
     * 人狼が誰かを知ることができる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, C国狂人, 狂信者, 煽動者]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_ViewableWolfCharaName() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isViewableWolfCharaName();
    }

    /**
     * 占い結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 一匹狼]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_DivineResultWolf() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isDivineResultWolf();
    }

    /**
     * 霊能結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 一匹狼]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_PsychicResultWolf() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isPsychicResultWolf();
    }

    /**
     * おまかせ系 <br>
     * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_SomeoneSkill() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isSomeoneSkill();
    }

    /**
     * Is the value of campCode 愉快犯陣営? <br>
     * 愉快犯陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isCampCode愉快犯陣営() {
        CDef.Camp cdef = getCampCodeAsCamp();
        return cdef != null ? cdef.equals(CDef.Camp.愉快犯陣営) : false;
    }

    /**
     * Is the value of campCode 狐陣営? <br>
     * 狐陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isCampCode狐陣営() {
        CDef.Camp cdef = getCampCodeAsCamp();
        return cdef != null ? cdef.equals(CDef.Camp.狐陣営) : false;
    }

    /**
     * Is the value of campCode 恋人陣営? <br>
     * 恋人陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isCampCode恋人陣営() {
        CDef.Camp cdef = getCampCodeAsCamp();
        return cdef != null ? cdef.equals(CDef.Camp.恋人陣営) : false;
    }

    /**
     * Is the value of campCode 村人陣営? <br>
     * 村人陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isCampCode村人陣営() {
        CDef.Camp cdef = getCampCodeAsCamp();
        return cdef != null ? cdef.equals(CDef.Camp.村人陣営) : false;
    }

    /**
     * Is the value of campCode 人狼陣営? <br>
     * 人狼陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isCampCode人狼陣営() {
        CDef.Camp cdef = getCampCodeAsCamp();
        return cdef != null ? cdef.equals(CDef.Camp.人狼陣営) : false;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** CAMP by my CAMP_CODE, named 'camp'. */
    protected OptionalEntity<Camp> _camp;

    /**
     * [get] CAMP by my CAMP_CODE, named 'camp'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'camp'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Camp> getCamp() {
        if (_camp == null) { _camp = OptionalEntity.relationEmpty(this, "camp"); }
        return _camp;
    }

    /**
     * [set] CAMP by my CAMP_CODE, named 'camp'.
     * @param camp The entity of foreign property 'camp'. (NullAllowed)
     */
    public void setCamp(OptionalEntity<Camp> camp) {
        _camp = camp;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** NORMAL_SAY_RESTRICTION by SKILL_CODE, named 'normalSayRestrictionList'. */
    protected List<NormalSayRestriction> _normalSayRestrictionList;

    /**
     * [get] NORMAL_SAY_RESTRICTION by SKILL_CODE, named 'normalSayRestrictionList'.
     * @return The entity list of referrer property 'normalSayRestrictionList'. (NotNull: even if no loading, returns empty list)
     */
    public List<NormalSayRestriction> getNormalSayRestrictionList() {
        if (_normalSayRestrictionList == null) { _normalSayRestrictionList = newReferrerList(); }
        return _normalSayRestrictionList;
    }

    /**
     * [set] NORMAL_SAY_RESTRICTION by SKILL_CODE, named 'normalSayRestrictionList'.
     * @param normalSayRestrictionList The entity list of referrer property 'normalSayRestrictionList'. (NullAllowed)
     */
    public void setNormalSayRestrictionList(List<NormalSayRestriction> normalSayRestrictionList) {
        _normalSayRestrictionList = normalSayRestrictionList;
    }

    /** SKILL_ALLOCATION by SKILL_CODE, named 'skillAllocationList'. */
    protected List<SkillAllocation> _skillAllocationList;

    /**
     * [get] SKILL_ALLOCATION by SKILL_CODE, named 'skillAllocationList'.
     * @return The entity list of referrer property 'skillAllocationList'. (NotNull: even if no loading, returns empty list)
     */
    public List<SkillAllocation> getSkillAllocationList() {
        if (_skillAllocationList == null) { _skillAllocationList = newReferrerList(); }
        return _skillAllocationList;
    }

    /**
     * [set] SKILL_ALLOCATION by SKILL_CODE, named 'skillAllocationList'.
     * @param skillAllocationList The entity list of referrer property 'skillAllocationList'. (NullAllowed)
     */
    public void setSkillAllocationList(List<SkillAllocation> skillAllocationList) {
        _skillAllocationList = skillAllocationList;
    }

    /** VILLAGE_PLAYER by REQUEST_SKILL_CODE, named 'villagePlayerByRequestSkillCodeList'. */
    protected List<VillagePlayer> _villagePlayerByRequestSkillCodeList;

    /**
     * [get] VILLAGE_PLAYER by REQUEST_SKILL_CODE, named 'villagePlayerByRequestSkillCodeList'.
     * @return The entity list of referrer property 'villagePlayerByRequestSkillCodeList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayer> getVillagePlayerByRequestSkillCodeList() {
        if (_villagePlayerByRequestSkillCodeList == null) { _villagePlayerByRequestSkillCodeList = newReferrerList(); }
        return _villagePlayerByRequestSkillCodeList;
    }

    /**
     * [set] VILLAGE_PLAYER by REQUEST_SKILL_CODE, named 'villagePlayerByRequestSkillCodeList'.
     * @param villagePlayerByRequestSkillCodeList The entity list of referrer property 'villagePlayerByRequestSkillCodeList'. (NullAllowed)
     */
    public void setVillagePlayerByRequestSkillCodeList(List<VillagePlayer> villagePlayerByRequestSkillCodeList) {
        _villagePlayerByRequestSkillCodeList = villagePlayerByRequestSkillCodeList;
    }

    /** VILLAGE_PLAYER by SECOND_REQUEST_SKILL_CODE, named 'villagePlayerBySecondRequestSkillCodeList'. */
    protected List<VillagePlayer> _villagePlayerBySecondRequestSkillCodeList;

    /**
     * [get] VILLAGE_PLAYER by SECOND_REQUEST_SKILL_CODE, named 'villagePlayerBySecondRequestSkillCodeList'.
     * @return The entity list of referrer property 'villagePlayerBySecondRequestSkillCodeList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayer> getVillagePlayerBySecondRequestSkillCodeList() {
        if (_villagePlayerBySecondRequestSkillCodeList == null) { _villagePlayerBySecondRequestSkillCodeList = newReferrerList(); }
        return _villagePlayerBySecondRequestSkillCodeList;
    }

    /**
     * [set] VILLAGE_PLAYER by SECOND_REQUEST_SKILL_CODE, named 'villagePlayerBySecondRequestSkillCodeList'.
     * @param villagePlayerBySecondRequestSkillCodeList The entity list of referrer property 'villagePlayerBySecondRequestSkillCodeList'. (NullAllowed)
     */
    public void setVillagePlayerBySecondRequestSkillCodeList(List<VillagePlayer> villagePlayerBySecondRequestSkillCodeList) {
        _villagePlayerBySecondRequestSkillCodeList = villagePlayerBySecondRequestSkillCodeList;
    }

    /** VILLAGE_PLAYER by SKILL_CODE, named 'villagePlayerBySkillCodeList'. */
    protected List<VillagePlayer> _villagePlayerBySkillCodeList;

    /**
     * [get] VILLAGE_PLAYER by SKILL_CODE, named 'villagePlayerBySkillCodeList'.
     * @return The entity list of referrer property 'villagePlayerBySkillCodeList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayer> getVillagePlayerBySkillCodeList() {
        if (_villagePlayerBySkillCodeList == null) { _villagePlayerBySkillCodeList = newReferrerList(); }
        return _villagePlayerBySkillCodeList;
    }

    /**
     * [set] VILLAGE_PLAYER by SKILL_CODE, named 'villagePlayerBySkillCodeList'.
     * @param villagePlayerBySkillCodeList The entity list of referrer property 'villagePlayerBySkillCodeList'. (NullAllowed)
     */
    public void setVillagePlayerBySkillCodeList(List<VillagePlayer> villagePlayerBySkillCodeList) {
        _villagePlayerBySkillCodeList = villagePlayerBySkillCodeList;
    }

    /** VILLAGE_PLAYER_SKILL_HISTORY by SKILL_CODE, named 'villagePlayerSkillHistoryList'. */
    protected List<VillagePlayerSkillHistory> _villagePlayerSkillHistoryList;

    /**
     * [get] VILLAGE_PLAYER_SKILL_HISTORY by SKILL_CODE, named 'villagePlayerSkillHistoryList'.
     * @return The entity list of referrer property 'villagePlayerSkillHistoryList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayerSkillHistory> getVillagePlayerSkillHistoryList() {
        if (_villagePlayerSkillHistoryList == null) { _villagePlayerSkillHistoryList = newReferrerList(); }
        return _villagePlayerSkillHistoryList;
    }

    /**
     * [set] VILLAGE_PLAYER_SKILL_HISTORY by SKILL_CODE, named 'villagePlayerSkillHistoryList'.
     * @param villagePlayerSkillHistoryList The entity list of referrer property 'villagePlayerSkillHistoryList'. (NullAllowed)
     */
    public void setVillagePlayerSkillHistoryList(List<VillagePlayerSkillHistory> villagePlayerSkillHistoryList) {
        _villagePlayerSkillHistoryList = villagePlayerSkillHistoryList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsSkill) {
            BsSkill other = (BsSkill)obj;
            if (!xSV(_skillCode, other._skillCode)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _skillCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_camp != null && _camp.isPresent())
        { sb.append(li).append(xbRDS(_camp, "camp")); }
        if (_normalSayRestrictionList != null) { for (NormalSayRestriction et : _normalSayRestrictionList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "normalSayRestrictionList")); } } }
        if (_skillAllocationList != null) { for (SkillAllocation et : _skillAllocationList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "skillAllocationList")); } } }
        if (_villagePlayerByRequestSkillCodeList != null) { for (VillagePlayer et : _villagePlayerByRequestSkillCodeList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerByRequestSkillCodeList")); } } }
        if (_villagePlayerBySecondRequestSkillCodeList != null) { for (VillagePlayer et : _villagePlayerBySecondRequestSkillCodeList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerBySecondRequestSkillCodeList")); } } }
        if (_villagePlayerBySkillCodeList != null) { for (VillagePlayer et : _villagePlayerBySkillCodeList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerBySkillCodeList")); } } }
        if (_villagePlayerSkillHistoryList != null) { for (VillagePlayerSkillHistory et : _villagePlayerSkillHistoryList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerSkillHistoryList")); } } }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_skillCode));
        sb.append(dm).append(xfND(_skillName));
        sb.append(dm).append(xfND(_skillShortName));
        sb.append(dm).append(xfND(_campCode));
        sb.append(dm).append(xfND(_dispOrder));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_camp != null && _camp.isPresent())
        { sb.append(dm).append("camp"); }
        if (_normalSayRestrictionList != null && !_normalSayRestrictionList.isEmpty())
        { sb.append(dm).append("normalSayRestrictionList"); }
        if (_skillAllocationList != null && !_skillAllocationList.isEmpty())
        { sb.append(dm).append("skillAllocationList"); }
        if (_villagePlayerByRequestSkillCodeList != null && !_villagePlayerByRequestSkillCodeList.isEmpty())
        { sb.append(dm).append("villagePlayerByRequestSkillCodeList"); }
        if (_villagePlayerBySecondRequestSkillCodeList != null && !_villagePlayerBySecondRequestSkillCodeList.isEmpty())
        { sb.append(dm).append("villagePlayerBySecondRequestSkillCodeList"); }
        if (_villagePlayerBySkillCodeList != null && !_villagePlayerBySkillCodeList.isEmpty())
        { sb.append(dm).append("villagePlayerBySkillCodeList"); }
        if (_villagePlayerSkillHistoryList != null && !_villagePlayerSkillHistoryList.isEmpty())
        { sb.append(dm).append("villagePlayerSkillHistoryList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public Skill clone() {
        return (Skill)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] SKILL_CODE: {PK, NotNull, VARCHAR(20), classification=Skill} <br>
     * 役職コード
     * @return The value of the column 'SKILL_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getSkillCode() {
        checkSpecifiedProperty("skillCode");
        return convertEmptyToNull(_skillCode);
    }

    /**
     * [set] SKILL_CODE: {PK, NotNull, VARCHAR(20), classification=Skill} <br>
     * 役職コード
     * @param skillCode The value of the column 'SKILL_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setSkillCode(String skillCode) {
        checkClassificationCode("SKILL_CODE", CDef.DefMeta.Skill, skillCode);
        registerModifiedProperty("skillCode");
        _skillCode = skillCode;
    }

    /**
     * [get] SKILL_NAME: {NotNull, VARCHAR(20)} <br>
     * 役職名
     * @return The value of the column 'SKILL_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getSkillName() {
        checkSpecifiedProperty("skillName");
        return convertEmptyToNull(_skillName);
    }

    /**
     * [set] SKILL_NAME: {NotNull, VARCHAR(20)} <br>
     * 役職名
     * @param skillName The value of the column 'SKILL_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setSkillName(String skillName) {
        registerModifiedProperty("skillName");
        _skillName = skillName;
    }

    /**
     * [get] SKILL_SHORT_NAME: {NotNull, CHAR(1)} <br>
     * 役職名略称
     * @return The value of the column 'SKILL_SHORT_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getSkillShortName() {
        checkSpecifiedProperty("skillShortName");
        return convertEmptyToNull(_skillShortName);
    }

    /**
     * [set] SKILL_SHORT_NAME: {NotNull, CHAR(1)} <br>
     * 役職名略称
     * @param skillShortName The value of the column 'SKILL_SHORT_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setSkillShortName(String skillShortName) {
        registerModifiedProperty("skillShortName");
        _skillShortName = skillShortName;
    }

    /**
     * [get] CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp, classification=Camp} <br>
     * 陣営コード
     * @return The value of the column 'CAMP_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getCampCode() {
        checkSpecifiedProperty("campCode");
        return convertEmptyToNull(_campCode);
    }

    /**
     * [set] CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp, classification=Camp} <br>
     * 陣営コード
     * @param campCode The value of the column 'CAMP_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setCampCode(String campCode) {
        checkClassificationCode("CAMP_CODE", CDef.DefMeta.Camp, campCode);
        registerModifiedProperty("campCode");
        _campCode = campCode;
    }

    /**
     * [get] DISP_ORDER: {NotNull, INT UNSIGNED(10)} <br>
     * 並び順
     * @return The value of the column 'DISP_ORDER'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDispOrder() {
        checkSpecifiedProperty("dispOrder");
        return _dispOrder;
    }

    /**
     * [set] DISP_ORDER: {NotNull, INT UNSIGNED(10)} <br>
     * 並び順
     * @param dispOrder The value of the column 'DISP_ORDER'. (basically NotNull if update: for the constraint)
     */
    public void setDispOrder(Integer dispOrder) {
        registerModifiedProperty("dispOrder");
        _dispOrder = dispOrder;
    }

    /**
     * For framework so basically DON'T use this method.
     * @param skillCode The value of the column 'SKILL_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingSkillCode(String skillCode) {
        setSkillCode(skillCode);
    }

    /**
     * For framework so basically DON'T use this method.
     * @param campCode The value of the column 'CAMP_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingCampCode(String campCode) {
        setCampCode(campCode);
    }
}
