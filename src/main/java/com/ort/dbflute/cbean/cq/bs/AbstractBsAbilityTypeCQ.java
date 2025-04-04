package com.ort.dbflute.cbean.cq.bs;

import java.util.*;

import org.dbflute.cbean.*;
import org.dbflute.cbean.chelper.*;
import org.dbflute.cbean.ckey.*;
import org.dbflute.cbean.coption.*;
import org.dbflute.cbean.cvalue.ConditionValue;
import org.dbflute.cbean.ordering.*;
import org.dbflute.cbean.scoping.*;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.dbmeta.DBMetaProvider;
import com.ort.dbflute.allcommon.*;
import com.ort.dbflute.cbean.*;
import com.ort.dbflute.cbean.cq.*;

/**
 * The abstract condition-query of ability_type.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsAbilityTypeCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsAbilityTypeCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    @Override
    protected DBMetaProvider xgetDBMetaProvider() {
        return DBMetaInstanceHandler.getProvider();
    }

    public String asTableDbName() {
        return "ability_type";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType}
     * @param abilityTypeCode The value of abilityTypeCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setAbilityTypeCode_Equal(String abilityTypeCode) {
        doSetAbilityTypeCode_Equal(fRES(abilityTypeCode));
    }

    /**
     * Equal(=). As AbilityType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType} <br>
     * 能力種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType cdef) {
        doSetAbilityTypeCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 襲撃 (ATTACK). And OnlyOnceRegistered. <br>
     * 襲撃
     */
    public void setAbilityTypeCode_Equal_襲撃() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.襲撃);
    }

    /**
     * Equal(=). As 襲撃希望 (ATTACK_REQUEST). And OnlyOnceRegistered. <br>
     * 襲撃希望
     */
    public void setAbilityTypeCode_Equal_襲撃希望() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.襲撃希望);
    }

    /**
     * Equal(=). As ババを渡す (BABAGIVE). And OnlyOnceRegistered. <br>
     * ババを渡す
     */
    public void setAbilityTypeCode_Equal_ババを渡す() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.ババを渡す);
    }

    /**
     * Equal(=). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setAbilityTypeCode_Equal_美人局() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.美人局);
    }

    /**
     * Equal(=). As 殴打 (BEAT). And OnlyOnceRegistered. <br>
     * 殴打
     */
    public void setAbilityTypeCode_Equal_殴打() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.殴打);
    }

    /**
     * Equal(=). As 爆弾設置 (BOMB). And OnlyOnceRegistered. <br>
     * 爆弾設置
     */
    public void setAbilityTypeCode_Equal_爆弾設置() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.爆弾設置);
    }

    /**
     * Equal(=). As 破局 (BREAKUP). And OnlyOnceRegistered. <br>
     * 破局
     */
    public void setAbilityTypeCode_Equal_破局() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.破局);
    }

    /**
     * Equal(=). As 誑かす (CHEAT). And OnlyOnceRegistered. <br>
     * 誑かす
     */
    public void setAbilityTypeCode_Equal_誑かす() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.誑かす);
    }

    /**
     * Equal(=). As 浮気 (CHEATLOVE). And OnlyOnceRegistered. <br>
     * 浮気
     */
    public void setAbilityTypeCode_Equal_浮気() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.浮気);
    }

    /**
     * Equal(=). As 誰だ今の (CHIKUWA). And OnlyOnceRegistered. <br>
     * 誰だ今の
     */
    public void setAbilityTypeCode_Equal_誰だ今の() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.誰だ今の);
    }

    /**
     * Equal(=). As 曇天 (CLOUD). And OnlyOnceRegistered. <br>
     * 曇天
     */
    public void setAbilityTypeCode_Equal_曇天() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.曇天);
    }

    /**
     * Equal(=). As 同棲 (COHABIT). And OnlyOnceRegistered. <br>
     * 同棲
     */
    public void setAbilityTypeCode_Equal_同棲() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.同棲);
    }

    /**
     * Equal(=). As 指揮 (COMMAND). And OnlyOnceRegistered. <br>
     * 指揮
     */
    public void setAbilityTypeCode_Equal_指揮() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.指揮);
    }

    /**
     * Equal(=). As 反呪 (COUNTERCURSE). And OnlyOnceRegistered. <br>
     * 反呪
     */
    public void setAbilityTypeCode_Equal_反呪() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.反呪);
    }

    /**
     * Equal(=). As 求愛 (COURT). And OnlyOnceRegistered. <br>
     * 求愛
     */
    public void setAbilityTypeCode_Equal_求愛() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.求愛);
    }

    /**
     * Equal(=). As 呪縛 (CURSE). And OnlyOnceRegistered. <br>
     * 呪縛
     */
    public void setAbilityTypeCode_Equal_呪縛() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.呪縛);
    }

    /**
     * Equal(=). As 死者占い (DEADDIVINE). And OnlyOnceRegistered. <br>
     * 死者占い
     */
    public void setAbilityTypeCode_Equal_死者占い() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.死者占い);
    }

    /**
     * Equal(=). As 占い (DIVINE). And OnlyOnceRegistered. <br>
     * 占い
     */
    public void setAbilityTypeCode_Equal_占い() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.占い);
    }

    /**
     * Equal(=). As 道化 (DOUKE). And OnlyOnceRegistered. <br>
     * 道化
     */
    public void setAbilityTypeCode_Equal_道化() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.道化);
    }

    /**
     * Equal(=). As 情緒 (EMOTION). And OnlyOnceRegistered. <br>
     * 情緒
     */
    public void setAbilityTypeCode_Equal_情緒() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.情緒);
    }

    /**
     * Equal(=). As 強制転生 (FORCE_REINCARNATION). And OnlyOnceRegistered. <br>
     * 強制転生
     */
    public void setAbilityTypeCode_Equal_強制転生() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.強制転生);
    }

    /**
     * Equal(=). As フルーツバスケット (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * フルーツバスケット
     */
    public void setAbilityTypeCode_Equal_フルーツバスケット() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.フルーツバスケット);
    }

    /**
     * Equal(=). As 念力付与 (GIVETELEKINESIS). And OnlyOnceRegistered. <br>
     * 念力付与
     */
    public void setAbilityTypeCode_Equal_念力付与() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.念力付与);
    }

    /**
     * Equal(=). As 護衛 (GUARD). And OnlyOnceRegistered. <br>
     * 護衛
     */
    public void setAbilityTypeCode_Equal_護衛() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.護衛);
    }

    /**
     * Equal(=). As 濡衣 (GUILTY). And OnlyOnceRegistered. <br>
     * 濡衣
     */
    public void setAbilityTypeCode_Equal_濡衣() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.濡衣);
    }

    /**
     * Equal(=). As 隠蔽 (HIDE). And OnlyOnceRegistered. <br>
     * 隠蔽
     */
    public void setAbilityTypeCode_Equal_隠蔽() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.隠蔽);
    }

    /**
     * Equal(=). As 冷やし中華 (HIYASICHUKA). And OnlyOnceRegistered. <br>
     * 冷やし中華
     */
    public void setAbilityTypeCode_Equal_冷やし中華() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.冷やし中華);
    }

    /**
     * Equal(=). As 狩猟 (HUNTING). And OnlyOnceRegistered. <br>
     * 狩猟
     */
    public void setAbilityTypeCode_Equal_狩猟() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.狩猟);
    }

    /**
     * Equal(=). As 教唆 (INCITE). And OnlyOnceRegistered. <br>
     * 教唆
     */
    public void setAbilityTypeCode_Equal_教唆() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.教唆);
    }

    /**
     * Equal(=). As 煽動 (INSTIGATE). And OnlyOnceRegistered. <br>
     * 煽動
     */
    public void setAbilityTypeCode_Equal_煽動() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.煽動);
    }

    /**
     * Equal(=). As 保険 (INSURANCE). And OnlyOnceRegistered. <br>
     * 保険
     */
    public void setAbilityTypeCode_Equal_保険() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.保険);
    }

    /**
     * Equal(=). As 捜査 (INVESTIGATE). And OnlyOnceRegistered. <br>
     * 捜査
     */
    public void setAbilityTypeCode_Equal_捜査() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.捜査);
    }

    /**
     * Equal(=). As 単独襲撃 (LONEATTACK). And OnlyOnceRegistered. <br>
     * 単独襲撃
     */
    public void setAbilityTypeCode_Equal_単独襲撃() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.単独襲撃);
    }

    /**
     * Equal(=). As 拡声 (LOUDSPEAK). And OnlyOnceRegistered. <br>
     * 拡声
     */
    public void setAbilityTypeCode_Equal_拡声() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.拡声);
    }

    /**
     * Equal(=). As 恋泥棒 (LOVESTEAL). And OnlyOnceRegistered. <br>
     * 恋泥棒
     */
    public void setAbilityTypeCode_Equal_恋泥棒() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.恋泥棒);
    }

    /**
     * Equal(=). As ナマ足 (NAMAASHI). And OnlyOnceRegistered. <br>
     * ナマ足
     */
    public void setAbilityTypeCode_Equal_ナマ足() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.ナマ足);
    }

    /**
     * Equal(=). As 死霊蘇生 (NECROMANCE). And OnlyOnceRegistered. <br>
     * 死霊蘇生
     */
    public void setAbilityTypeCode_Equal_死霊蘇生() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.死霊蘇生);
    }

    /**
     * Equal(=). As 全知 (OMNISCIENCE). And OnlyOnceRegistered. <br>
     * 全知
     */
    public void setAbilityTypeCode_Equal_全知() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.全知);
    }

    /**
     * Equal(=). As 降霊 (ONMYO_NECROMANCE). And OnlyOnceRegistered. <br>
     * 降霊
     */
    public void setAbilityTypeCode_Equal_降霊() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.降霊);
    }

    /**
     * Equal(=). As 説得 (PERSUADE). And OnlyOnceRegistered. <br>
     * 説得
     */
    public void setAbilityTypeCode_Equal_説得() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.説得);
    }

    /**
     * Equal(=). As 戦闘力発揮 (POWER). And OnlyOnceRegistered. <br>
     * 戦闘力発揮
     */
    public void setAbilityTypeCode_Equal_戦闘力発揮() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.戦闘力発揮);
    }

    /**
     * Equal(=). As 殺し屋化 (PRO). And OnlyOnceRegistered. <br>
     * 殺し屋化
     */
    public void setAbilityTypeCode_Equal_殺し屋化() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.殺し屋化);
    }

    /**
     * Equal(=). As 虹塗り (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹塗り
     */
    public void setAbilityTypeCode_Equal_虹塗り() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.虹塗り);
    }

    /**
     * Equal(=). As 蘇生 (RESUSCITATE). And OnlyOnceRegistered. <br>
     * 蘇生
     */
    public void setAbilityTypeCode_Equal_蘇生() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.蘇生);
    }

    /**
     * Equal(=). As 革命 (REVOLUTION). And OnlyOnceRegistered. <br>
     * 革命
     */
    public void setAbilityTypeCode_Equal_革命() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.革命);
    }

    /**
     * Equal(=). As 暴走転生 (RUNAWAY). And OnlyOnceRegistered. <br>
     * 暴走転生
     */
    public void setAbilityTypeCode_Equal_暴走転生() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.暴走転生);
    }

    /**
     * Equal(=). As 世界を救う (SAVETHEWORLD). And OnlyOnceRegistered. <br>
     * 世界を救う
     */
    public void setAbilityTypeCode_Equal_世界を救う() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.世界を救う);
    }

    /**
     * Equal(=). As 誘惑 (SEDUCE). And OnlyOnceRegistered. <br>
     * 誘惑
     */
    public void setAbilityTypeCode_Equal_誘惑() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.誘惑);
    }

    /**
     * Equal(=). As 叫び (SHOUT). And OnlyOnceRegistered. <br>
     * 叫び
     */
    public void setAbilityTypeCode_Equal_叫び() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.叫び);
    }

    /**
     * Equal(=). As ストーキング (STALKING). And OnlyOnceRegistered. <br>
     * ストーキング
     */
    public void setAbilityTypeCode_Equal_ストーキング() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.ストーキング);
    }

    /**
     * Equal(=). As 翻訳 (TRANSLATE). And OnlyOnceRegistered. <br>
     * 翻訳
     */
    public void setAbilityTypeCode_Equal_翻訳() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.翻訳);
    }

    /**
     * Equal(=). As 人魚化 (TRANSMERMAID). And OnlyOnceRegistered. <br>
     * 人魚化
     */
    public void setAbilityTypeCode_Equal_人魚化() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.人魚化);
    }

    /**
     * Equal(=). As 罠設置 (TRAP). And OnlyOnceRegistered. <br>
     * 罠設置
     */
    public void setAbilityTypeCode_Equal_罠設置() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.罠設置);
    }

    /**
     * Equal(=). As 壁殴り (WALLPUNCH). And OnlyOnceRegistered. <br>
     * 壁殴り
     */
    public void setAbilityTypeCode_Equal_壁殴り() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.壁殴り);
    }

    /**
     * Equal(=). As 風来護衛 (WANDERERGUARD). And OnlyOnceRegistered. <br>
     * 風来護衛
     */
    public void setAbilityTypeCode_Equal_風来護衛() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.風来護衛);
    }

    /**
     * Equal(=). As 当選 (WIN). And OnlyOnceRegistered. <br>
     * 当選
     */
    public void setAbilityTypeCode_Equal_当選() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.当選);
    }

    /**
     * Equal(=). As 指差死 (YUBISASHI). And OnlyOnceRegistered. <br>
     * 指差死
     */
    public void setAbilityTypeCode_Equal_指差死() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.指差死);
    }

    protected void doSetAbilityTypeCode_Equal(String abilityTypeCode) {
        regAbilityTypeCode(CK_EQ, abilityTypeCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType}
     * @param abilityTypeCode The value of abilityTypeCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setAbilityTypeCode_NotEqual(String abilityTypeCode) {
        doSetAbilityTypeCode_NotEqual(fRES(abilityTypeCode));
    }

    /**
     * NotEqual(&lt;&gt;). As AbilityType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType} <br>
     * 能力種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType cdef) {
        doSetAbilityTypeCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 襲撃 (ATTACK). And OnlyOnceRegistered. <br>
     * 襲撃
     */
    public void setAbilityTypeCode_NotEqual_襲撃() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.襲撃);
    }

    /**
     * NotEqual(&lt;&gt;). As 襲撃希望 (ATTACK_REQUEST). And OnlyOnceRegistered. <br>
     * 襲撃希望
     */
    public void setAbilityTypeCode_NotEqual_襲撃希望() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.襲撃希望);
    }

    /**
     * NotEqual(&lt;&gt;). As ババを渡す (BABAGIVE). And OnlyOnceRegistered. <br>
     * ババを渡す
     */
    public void setAbilityTypeCode_NotEqual_ババを渡す() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.ババを渡す);
    }

    /**
     * NotEqual(&lt;&gt;). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setAbilityTypeCode_NotEqual_美人局() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.美人局);
    }

    /**
     * NotEqual(&lt;&gt;). As 殴打 (BEAT). And OnlyOnceRegistered. <br>
     * 殴打
     */
    public void setAbilityTypeCode_NotEqual_殴打() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.殴打);
    }

    /**
     * NotEqual(&lt;&gt;). As 爆弾設置 (BOMB). And OnlyOnceRegistered. <br>
     * 爆弾設置
     */
    public void setAbilityTypeCode_NotEqual_爆弾設置() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.爆弾設置);
    }

    /**
     * NotEqual(&lt;&gt;). As 破局 (BREAKUP). And OnlyOnceRegistered. <br>
     * 破局
     */
    public void setAbilityTypeCode_NotEqual_破局() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.破局);
    }

    /**
     * NotEqual(&lt;&gt;). As 誑かす (CHEAT). And OnlyOnceRegistered. <br>
     * 誑かす
     */
    public void setAbilityTypeCode_NotEqual_誑かす() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.誑かす);
    }

    /**
     * NotEqual(&lt;&gt;). As 浮気 (CHEATLOVE). And OnlyOnceRegistered. <br>
     * 浮気
     */
    public void setAbilityTypeCode_NotEqual_浮気() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.浮気);
    }

    /**
     * NotEqual(&lt;&gt;). As 誰だ今の (CHIKUWA). And OnlyOnceRegistered. <br>
     * 誰だ今の
     */
    public void setAbilityTypeCode_NotEqual_誰だ今の() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.誰だ今の);
    }

    /**
     * NotEqual(&lt;&gt;). As 曇天 (CLOUD). And OnlyOnceRegistered. <br>
     * 曇天
     */
    public void setAbilityTypeCode_NotEqual_曇天() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.曇天);
    }

    /**
     * NotEqual(&lt;&gt;). As 同棲 (COHABIT). And OnlyOnceRegistered. <br>
     * 同棲
     */
    public void setAbilityTypeCode_NotEqual_同棲() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.同棲);
    }

    /**
     * NotEqual(&lt;&gt;). As 指揮 (COMMAND). And OnlyOnceRegistered. <br>
     * 指揮
     */
    public void setAbilityTypeCode_NotEqual_指揮() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.指揮);
    }

    /**
     * NotEqual(&lt;&gt;). As 反呪 (COUNTERCURSE). And OnlyOnceRegistered. <br>
     * 反呪
     */
    public void setAbilityTypeCode_NotEqual_反呪() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.反呪);
    }

    /**
     * NotEqual(&lt;&gt;). As 求愛 (COURT). And OnlyOnceRegistered. <br>
     * 求愛
     */
    public void setAbilityTypeCode_NotEqual_求愛() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.求愛);
    }

    /**
     * NotEqual(&lt;&gt;). As 呪縛 (CURSE). And OnlyOnceRegistered. <br>
     * 呪縛
     */
    public void setAbilityTypeCode_NotEqual_呪縛() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.呪縛);
    }

    /**
     * NotEqual(&lt;&gt;). As 死者占い (DEADDIVINE). And OnlyOnceRegistered. <br>
     * 死者占い
     */
    public void setAbilityTypeCode_NotEqual_死者占い() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.死者占い);
    }

    /**
     * NotEqual(&lt;&gt;). As 占い (DIVINE). And OnlyOnceRegistered. <br>
     * 占い
     */
    public void setAbilityTypeCode_NotEqual_占い() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.占い);
    }

    /**
     * NotEqual(&lt;&gt;). As 道化 (DOUKE). And OnlyOnceRegistered. <br>
     * 道化
     */
    public void setAbilityTypeCode_NotEqual_道化() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.道化);
    }

    /**
     * NotEqual(&lt;&gt;). As 情緒 (EMOTION). And OnlyOnceRegistered. <br>
     * 情緒
     */
    public void setAbilityTypeCode_NotEqual_情緒() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.情緒);
    }

    /**
     * NotEqual(&lt;&gt;). As 強制転生 (FORCE_REINCARNATION). And OnlyOnceRegistered. <br>
     * 強制転生
     */
    public void setAbilityTypeCode_NotEqual_強制転生() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.強制転生);
    }

    /**
     * NotEqual(&lt;&gt;). As フルーツバスケット (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * フルーツバスケット
     */
    public void setAbilityTypeCode_NotEqual_フルーツバスケット() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.フルーツバスケット);
    }

    /**
     * NotEqual(&lt;&gt;). As 念力付与 (GIVETELEKINESIS). And OnlyOnceRegistered. <br>
     * 念力付与
     */
    public void setAbilityTypeCode_NotEqual_念力付与() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.念力付与);
    }

    /**
     * NotEqual(&lt;&gt;). As 護衛 (GUARD). And OnlyOnceRegistered. <br>
     * 護衛
     */
    public void setAbilityTypeCode_NotEqual_護衛() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.護衛);
    }

    /**
     * NotEqual(&lt;&gt;). As 濡衣 (GUILTY). And OnlyOnceRegistered. <br>
     * 濡衣
     */
    public void setAbilityTypeCode_NotEqual_濡衣() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.濡衣);
    }

    /**
     * NotEqual(&lt;&gt;). As 隠蔽 (HIDE). And OnlyOnceRegistered. <br>
     * 隠蔽
     */
    public void setAbilityTypeCode_NotEqual_隠蔽() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.隠蔽);
    }

    /**
     * NotEqual(&lt;&gt;). As 冷やし中華 (HIYASICHUKA). And OnlyOnceRegistered. <br>
     * 冷やし中華
     */
    public void setAbilityTypeCode_NotEqual_冷やし中華() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.冷やし中華);
    }

    /**
     * NotEqual(&lt;&gt;). As 狩猟 (HUNTING). And OnlyOnceRegistered. <br>
     * 狩猟
     */
    public void setAbilityTypeCode_NotEqual_狩猟() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.狩猟);
    }

    /**
     * NotEqual(&lt;&gt;). As 教唆 (INCITE). And OnlyOnceRegistered. <br>
     * 教唆
     */
    public void setAbilityTypeCode_NotEqual_教唆() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.教唆);
    }

    /**
     * NotEqual(&lt;&gt;). As 煽動 (INSTIGATE). And OnlyOnceRegistered. <br>
     * 煽動
     */
    public void setAbilityTypeCode_NotEqual_煽動() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.煽動);
    }

    /**
     * NotEqual(&lt;&gt;). As 保険 (INSURANCE). And OnlyOnceRegistered. <br>
     * 保険
     */
    public void setAbilityTypeCode_NotEqual_保険() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.保険);
    }

    /**
     * NotEqual(&lt;&gt;). As 捜査 (INVESTIGATE). And OnlyOnceRegistered. <br>
     * 捜査
     */
    public void setAbilityTypeCode_NotEqual_捜査() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.捜査);
    }

    /**
     * NotEqual(&lt;&gt;). As 単独襲撃 (LONEATTACK). And OnlyOnceRegistered. <br>
     * 単独襲撃
     */
    public void setAbilityTypeCode_NotEqual_単独襲撃() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.単独襲撃);
    }

    /**
     * NotEqual(&lt;&gt;). As 拡声 (LOUDSPEAK). And OnlyOnceRegistered. <br>
     * 拡声
     */
    public void setAbilityTypeCode_NotEqual_拡声() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.拡声);
    }

    /**
     * NotEqual(&lt;&gt;). As 恋泥棒 (LOVESTEAL). And OnlyOnceRegistered. <br>
     * 恋泥棒
     */
    public void setAbilityTypeCode_NotEqual_恋泥棒() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.恋泥棒);
    }

    /**
     * NotEqual(&lt;&gt;). As ナマ足 (NAMAASHI). And OnlyOnceRegistered. <br>
     * ナマ足
     */
    public void setAbilityTypeCode_NotEqual_ナマ足() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.ナマ足);
    }

    /**
     * NotEqual(&lt;&gt;). As 死霊蘇生 (NECROMANCE). And OnlyOnceRegistered. <br>
     * 死霊蘇生
     */
    public void setAbilityTypeCode_NotEqual_死霊蘇生() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.死霊蘇生);
    }

    /**
     * NotEqual(&lt;&gt;). As 全知 (OMNISCIENCE). And OnlyOnceRegistered. <br>
     * 全知
     */
    public void setAbilityTypeCode_NotEqual_全知() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.全知);
    }

    /**
     * NotEqual(&lt;&gt;). As 降霊 (ONMYO_NECROMANCE). And OnlyOnceRegistered. <br>
     * 降霊
     */
    public void setAbilityTypeCode_NotEqual_降霊() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.降霊);
    }

    /**
     * NotEqual(&lt;&gt;). As 説得 (PERSUADE). And OnlyOnceRegistered. <br>
     * 説得
     */
    public void setAbilityTypeCode_NotEqual_説得() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.説得);
    }

    /**
     * NotEqual(&lt;&gt;). As 戦闘力発揮 (POWER). And OnlyOnceRegistered. <br>
     * 戦闘力発揮
     */
    public void setAbilityTypeCode_NotEqual_戦闘力発揮() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.戦闘力発揮);
    }

    /**
     * NotEqual(&lt;&gt;). As 殺し屋化 (PRO). And OnlyOnceRegistered. <br>
     * 殺し屋化
     */
    public void setAbilityTypeCode_NotEqual_殺し屋化() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.殺し屋化);
    }

    /**
     * NotEqual(&lt;&gt;). As 虹塗り (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹塗り
     */
    public void setAbilityTypeCode_NotEqual_虹塗り() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.虹塗り);
    }

    /**
     * NotEqual(&lt;&gt;). As 蘇生 (RESUSCITATE). And OnlyOnceRegistered. <br>
     * 蘇生
     */
    public void setAbilityTypeCode_NotEqual_蘇生() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.蘇生);
    }

    /**
     * NotEqual(&lt;&gt;). As 革命 (REVOLUTION). And OnlyOnceRegistered. <br>
     * 革命
     */
    public void setAbilityTypeCode_NotEqual_革命() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.革命);
    }

    /**
     * NotEqual(&lt;&gt;). As 暴走転生 (RUNAWAY). And OnlyOnceRegistered. <br>
     * 暴走転生
     */
    public void setAbilityTypeCode_NotEqual_暴走転生() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.暴走転生);
    }

    /**
     * NotEqual(&lt;&gt;). As 世界を救う (SAVETHEWORLD). And OnlyOnceRegistered. <br>
     * 世界を救う
     */
    public void setAbilityTypeCode_NotEqual_世界を救う() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.世界を救う);
    }

    /**
     * NotEqual(&lt;&gt;). As 誘惑 (SEDUCE). And OnlyOnceRegistered. <br>
     * 誘惑
     */
    public void setAbilityTypeCode_NotEqual_誘惑() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.誘惑);
    }

    /**
     * NotEqual(&lt;&gt;). As 叫び (SHOUT). And OnlyOnceRegistered. <br>
     * 叫び
     */
    public void setAbilityTypeCode_NotEqual_叫び() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.叫び);
    }

    /**
     * NotEqual(&lt;&gt;). As ストーキング (STALKING). And OnlyOnceRegistered. <br>
     * ストーキング
     */
    public void setAbilityTypeCode_NotEqual_ストーキング() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.ストーキング);
    }

    /**
     * NotEqual(&lt;&gt;). As 翻訳 (TRANSLATE). And OnlyOnceRegistered. <br>
     * 翻訳
     */
    public void setAbilityTypeCode_NotEqual_翻訳() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.翻訳);
    }

    /**
     * NotEqual(&lt;&gt;). As 人魚化 (TRANSMERMAID). And OnlyOnceRegistered. <br>
     * 人魚化
     */
    public void setAbilityTypeCode_NotEqual_人魚化() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.人魚化);
    }

    /**
     * NotEqual(&lt;&gt;). As 罠設置 (TRAP). And OnlyOnceRegistered. <br>
     * 罠設置
     */
    public void setAbilityTypeCode_NotEqual_罠設置() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.罠設置);
    }

    /**
     * NotEqual(&lt;&gt;). As 壁殴り (WALLPUNCH). And OnlyOnceRegistered. <br>
     * 壁殴り
     */
    public void setAbilityTypeCode_NotEqual_壁殴り() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.壁殴り);
    }

    /**
     * NotEqual(&lt;&gt;). As 風来護衛 (WANDERERGUARD). And OnlyOnceRegistered. <br>
     * 風来護衛
     */
    public void setAbilityTypeCode_NotEqual_風来護衛() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.風来護衛);
    }

    /**
     * NotEqual(&lt;&gt;). As 当選 (WIN). And OnlyOnceRegistered. <br>
     * 当選
     */
    public void setAbilityTypeCode_NotEqual_当選() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.当選);
    }

    /**
     * NotEqual(&lt;&gt;). As 指差死 (YUBISASHI). And OnlyOnceRegistered. <br>
     * 指差死
     */
    public void setAbilityTypeCode_NotEqual_指差死() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.指差死);
    }

    protected void doSetAbilityTypeCode_NotEqual(String abilityTypeCode) {
        regAbilityTypeCode(CK_NES, abilityTypeCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType}
     * @param abilityTypeCodeList The collection of abilityTypeCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setAbilityTypeCode_InScope(Collection<String> abilityTypeCodeList) {
        doSetAbilityTypeCode_InScope(abilityTypeCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As AbilityType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType} <br>
     * 能力種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeCode_InScope_AsAbilityType(Collection<CDef.AbilityType> cdefList) {
        doSetAbilityTypeCode_InScope(cTStrL(cdefList));
    }

    protected void doSetAbilityTypeCode_InScope(Collection<String> abilityTypeCodeList) {
        regINS(CK_INS, cTL(abilityTypeCodeList), xgetCValueAbilityTypeCode(), "ABILITY_TYPE_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType}
     * @param abilityTypeCodeList The collection of abilityTypeCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setAbilityTypeCode_NotInScope(Collection<String> abilityTypeCodeList) {
        doSetAbilityTypeCode_NotInScope(abilityTypeCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As AbilityType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType} <br>
     * 能力種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeCode_NotInScope_AsAbilityType(Collection<CDef.AbilityType> cdefList) {
        doSetAbilityTypeCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetAbilityTypeCode_NotInScope(Collection<String> abilityTypeCodeList) {
        regINS(CK_NINS, cTL(abilityTypeCodeList), xgetCValueAbilityTypeCode(), "ABILITY_TYPE_CODE");
    }

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select ABILITY_TYPE_CODE from ability where ...)} <br>
     * ability by ABILITY_TYPE_CODE, named 'abilityAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsAbility</span>(abilityCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     abilityCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of AbilityList for 'exists'. (NotNull)
     */
    public void existsAbility(SubQuery<AbilityCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        AbilityCB cb = new AbilityCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepAbilityTypeCode_ExistsReferrer_AbilityList(cb.query());
        registerExistsReferrer(cb.query(), "ABILITY_TYPE_CODE", "ABILITY_TYPE_CODE", pp, "abilityList");
    }
    public abstract String keepAbilityTypeCode_ExistsReferrer_AbilityList(AbilityCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select ABILITY_TYPE_CODE from ability where ...)} <br>
     * ability by ABILITY_TYPE_CODE, named 'abilityAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsAbility</span>(abilityCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     abilityCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of AbilityTypeCode_NotExistsReferrer_AbilityList for 'not exists'. (NotNull)
     */
    public void notExistsAbility(SubQuery<AbilityCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        AbilityCB cb = new AbilityCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepAbilityTypeCode_NotExistsReferrer_AbilityList(cb.query());
        registerNotExistsReferrer(cb.query(), "ABILITY_TYPE_CODE", "ABILITY_TYPE_CODE", pp, "abilityList");
    }
    public abstract String keepAbilityTypeCode_NotExistsReferrer_AbilityList(AbilityCQ sq);

    public void xsderiveAbilityList(String fn, SubQuery<AbilityCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        AbilityCB cb = new AbilityCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepAbilityTypeCode_SpecifyDerivedReferrer_AbilityList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "ABILITY_TYPE_CODE", "ABILITY_TYPE_CODE", pp, "abilityList", al, op);
    }
    public abstract String keepAbilityTypeCode_SpecifyDerivedReferrer_AbilityList(AbilityCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from ability where ...)} <br>
     * ability by ABILITY_TYPE_CODE, named 'abilityAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedAbility()</span>.<span style="color: #CC4747">max</span>(abilityCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     abilityCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     abilityCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<AbilityCB> derivedAbility() {
        return xcreateQDRFunctionAbilityList();
    }
    protected HpQDRFunction<AbilityCB> xcreateQDRFunctionAbilityList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveAbilityList(fn, sq, rd, vl, op));
    }
    public void xqderiveAbilityList(String fn, SubQuery<AbilityCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        AbilityCB cb = new AbilityCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepAbilityTypeCode_QueryDerivedReferrer_AbilityList(cb.query()); String prpp = keepAbilityTypeCode_QueryDerivedReferrer_AbilityListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "ABILITY_TYPE_CODE", "ABILITY_TYPE_CODE", sqpp, "abilityList", rd, vl, prpp, op);
    }
    public abstract String keepAbilityTypeCode_QueryDerivedReferrer_AbilityList(AbilityCQ sq);
    public abstract String keepAbilityTypeCode_QueryDerivedReferrer_AbilityListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType}
     */
    public void setAbilityTypeCode_IsNull() { regAbilityTypeCode(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType}
     */
    public void setAbilityTypeCode_IsNotNull() { regAbilityTypeCode(CK_ISNN, DOBJ); }

    protected void regAbilityTypeCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueAbilityTypeCode(), "ABILITY_TYPE_CODE"); }
    protected abstract ConditionValue xgetCValueAbilityTypeCode();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param abilityTypeName The value of abilityTypeName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeName_Equal(String abilityTypeName) {
        doSetAbilityTypeName_Equal(fRES(abilityTypeName));
    }

    protected void doSetAbilityTypeName_Equal(String abilityTypeName) {
        regAbilityTypeName(CK_EQ, abilityTypeName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param abilityTypeName The value of abilityTypeName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeName_NotEqual(String abilityTypeName) {
        doSetAbilityTypeName_NotEqual(fRES(abilityTypeName));
    }

    protected void doSetAbilityTypeName_NotEqual(String abilityTypeName) {
        regAbilityTypeName(CK_NES, abilityTypeName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param abilityTypeName The value of abilityTypeName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeName_GreaterThan(String abilityTypeName) {
        regAbilityTypeName(CK_GT, fRES(abilityTypeName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param abilityTypeName The value of abilityTypeName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeName_LessThan(String abilityTypeName) {
        regAbilityTypeName(CK_LT, fRES(abilityTypeName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param abilityTypeName The value of abilityTypeName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeName_GreaterEqual(String abilityTypeName) {
        regAbilityTypeName(CK_GE, fRES(abilityTypeName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param abilityTypeName The value of abilityTypeName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeName_LessEqual(String abilityTypeName) {
        regAbilityTypeName(CK_LE, fRES(abilityTypeName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param abilityTypeNameList The collection of abilityTypeName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeName_InScope(Collection<String> abilityTypeNameList) {
        doSetAbilityTypeName_InScope(abilityTypeNameList);
    }

    protected void doSetAbilityTypeName_InScope(Collection<String> abilityTypeNameList) {
        regINS(CK_INS, cTL(abilityTypeNameList), xgetCValueAbilityTypeName(), "ABILITY_TYPE_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param abilityTypeNameList The collection of abilityTypeName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeName_NotInScope(Collection<String> abilityTypeNameList) {
        doSetAbilityTypeName_NotInScope(abilityTypeNameList);
    }

    protected void doSetAbilityTypeName_NotInScope(Collection<String> abilityTypeNameList) {
        regINS(CK_NINS, cTL(abilityTypeNameList), xgetCValueAbilityTypeName(), "ABILITY_TYPE_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setAbilityTypeName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param abilityTypeName The value of abilityTypeName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setAbilityTypeName_LikeSearch(String abilityTypeName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setAbilityTypeName_LikeSearch(abilityTypeName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setAbilityTypeName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param abilityTypeName The value of abilityTypeName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setAbilityTypeName_LikeSearch(String abilityTypeName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(abilityTypeName), xgetCValueAbilityTypeName(), "ABILITY_TYPE_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param abilityTypeName The value of abilityTypeName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setAbilityTypeName_NotLikeSearch(String abilityTypeName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setAbilityTypeName_NotLikeSearch(abilityTypeName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param abilityTypeName The value of abilityTypeName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setAbilityTypeName_NotLikeSearch(String abilityTypeName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(abilityTypeName), xgetCValueAbilityTypeName(), "ABILITY_TYPE_NAME", likeSearchOption);
    }

    protected void regAbilityTypeName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueAbilityTypeName(), "ABILITY_TYPE_NAME"); }
    protected abstract ConditionValue xgetCValueAbilityTypeName();

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO = (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<AbilityTypeCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, AbilityTypeCB.class);
    }

    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO &lt;&gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<AbilityTypeCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, AbilityTypeCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterThan. <br>
     * {where FOO &gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<AbilityTypeCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, AbilityTypeCB.class);
    }

    /**
     * Prepare ScalarCondition as lessThan. <br>
     * {where FOO &lt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<AbilityTypeCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, AbilityTypeCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterEqual. <br>
     * {where FOO &gt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<AbilityTypeCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, AbilityTypeCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;AbilityTypeCB&gt;() {
     *     public void query(AbilityTypeCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<AbilityTypeCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, AbilityTypeCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        AbilityTypeCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(AbilityTypeCQ sq);

    protected AbilityTypeCB xcreateScalarConditionCB() {
        AbilityTypeCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected AbilityTypeCB xcreateScalarConditionPartitionByCB() {
        AbilityTypeCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<AbilityTypeCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        AbilityTypeCB cb = new AbilityTypeCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "ABILITY_TYPE_CODE";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(AbilityTypeCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<AbilityTypeCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(AbilityTypeCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        AbilityTypeCB cb = new AbilityTypeCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "ABILITY_TYPE_CODE";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(AbilityTypeCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<AbilityTypeCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        AbilityTypeCB cb = new AbilityTypeCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(AbilityTypeCQ sq);

    // ===================================================================================
    //                                                                        Manual Order
    //                                                                        ============
    /**
     * Order along manual ordering information.
     * <pre>
     * cb.query().addOrderBy_Birthdate_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_GreaterEqual</span>(priorityDate); <span style="color: #3F7E5E">// e.g. 2000/01/01</span>
     * });
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when BIRTHDATE &gt;= '2000/01/01' then 0</span>
     * <span style="color: #3F7E5E">//     else 1</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     *
     * cb.query().addOrderBy_MemberStatusCode_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Withdrawal);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Formalized);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Provisional);
     * });
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'WDL' then 0</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'FML' then 1</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'PRV' then 2</span>
     * <span style="color: #3F7E5E">//     else 3</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     * </pre>
     * <p>This function with Union is unsupported!</p>
     * <p>The order values are bound (treated as bind parameter).</p>
     * @param opLambda The callback for option of manual-order containing order values. (NotNull)
     */
    public void withManualOrder(ManualOrderOptionCall opLambda) { // is user public!
        xdoWithManualOrder(cMOO(opLambda));
    }

    // ===================================================================================
    //                                                                    Small Adjustment
    //                                                                    ================
    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    protected AbilityTypeCB newMyCB() {
        return new AbilityTypeCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return AbilityTypeCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
